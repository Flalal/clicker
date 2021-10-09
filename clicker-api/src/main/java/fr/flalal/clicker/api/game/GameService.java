package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.configuration.ClickerProperties;
import fr.flalal.clicker.api.error.HackerException;
import fr.flalal.clicker.api.error.InternalDatabaseServeurException;
import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.generator.GeneratorService;
import fr.flalal.clicker.api.representation.GameRepresentation;
import fr.flalal.clicker.api.representation.GeneratorRepresentation;
import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class GameService {

    private final GameRepository repository;
    private final ClickerProperties clickerProperties;
    private final GeneratorService generatorService;
    private final Converter converter = Mappers.getMapper(Converter.class);

    public Mono<GameRepresentation> findGameById(UUID id) {
        return Mono.just(converter.toGameRepresentation(repository.findGameById(id)));
    }

    public Mono<GameRepresentation> findGameByPseudonym(String pseudo) {
        return Mono.just(converter.toGameRepresentation(repository.findGameByPseudonym(pseudo)));
    }

    public Mono<GameRepresentation> updateGame(GameRepresentation draft) {
        return findGameById(draft.getId())
                .map(gameStored -> compareGame(gameStored, draft));
    }

    @Transactional
    GameRepresentation compareGame(GameRepresentation stored, GameRepresentation draft) {
        long secondDiff = (OffsetDateTime.now().toEpochSecond() - stored.getUpdatedAt().toEpochSecond());

        checkManualClick(stored, draft, secondDiff);
        checkGeneratorClick(stored, draft, secondDiff);

        return converter.toGameRepresentation(repository.findGameById(stored.getId()));
    }

    private void checkGeneratorClick(GameRepresentation stored, GameRepresentation draft, long secondDiff) {
        List<GeneratorRepresentation> storedGenerators = stored.getGenerators();
        List<GeneratorRepresentation> draftGenerators = draft.getGenerators();
        List<GeneratorRecord> allGenerator = generatorService.findGeneratorsByIds(collectGeneratorIds(storedGenerators, draftGenerators));
        draftGenerators
                .forEach(draftGenerator -> verifyAndUpdateGameGenerator(draft, secondDiff, storedGenerators, allGenerator, draftGenerator));

    }

    private void verifyAndUpdateGameGenerator(GameRepresentation draft, long secondDiff, List<GeneratorRepresentation> storedGenerators, List<GeneratorRecord> allGenerator, GeneratorRepresentation draftGenerator) {
        Optional<GeneratorRepresentation> optionalStoredGenerator = storedGenerators.stream()
                .filter(generatorRepresentation -> draftGenerator.getId().equals(generatorRepresentation.getId()))
                .findFirst();
        Optional<GeneratorRecord> optionalGenerator = allGenerator.stream()
                .filter(generatorRecord -> draftGenerator.getId().equals(generatorRecord.getId()))
                .findFirst();

        if (optionalGenerator.isEmpty()) {
            throw new ResourceNotFoundException("Generator not found maybe a hacker");
        }

        if (optionalStoredGenerator.isEmpty()) {
            createNewGameGenerator(draft.getId(), draftGenerator, optionalGenerator.get());
        } else {
            GeneratorRecord generator = optionalGenerator.get();
            GeneratorRepresentation storedGenerator = optionalStoredGenerator.get();
            checkGeneratedClickAlreadyInGame(secondDiff, draftGenerator, generator, storedGenerator);
        }

        int nbRow = repository.updateGameGenerator(draft.getId(), optionalGenerator.get().getId(), draftGenerator.getGeneratedClick(), draftGenerator.getLevel());
        if (nbRow != 1) {
            throw new InternalDatabaseServeurException("Error on update");
        }
    }

    private void checkGeneratedClickAlreadyInGame(long secondDiff, GeneratorRepresentation draftGenerator, GeneratorRecord generator, GeneratorRepresentation storedGenerator) {
        int draftLevel = draftGenerator.getLevel();
        BigDecimal maxGeneratedClick = generator.getBaseMultiplier().multiply(new BigDecimal(draftLevel)).multiply(new BigDecimal(secondDiff));
        BigDecimal generatedClickSinceLastUpdate = draftGenerator.getGeneratedClick().subtract(storedGenerator.getGeneratedClick());
        if (maxGeneratedClick.compareTo(generatedClickSinceLastUpdate) <= 0) {
            throw new HackerException("Hacker detected");
        }
    }

    private void createNewGameGenerator(UUID gameId, GeneratorRepresentation draftGenerator, GeneratorRecord generator) {
        // Must be lvl 1
        if (draftGenerator.getLevel() != 1) {
            throw new HackerException("New Game generator not lvl 1");
        }
        GameGeneratorRecord gameGeneratorRecord = new GameGeneratorRecord();
        gameGeneratorRecord.setIdGenerator(generator.getId());
        gameGeneratorRecord.setIdGame(gameId);
        gameGeneratorRecord.setCreatedAt(OffsetDateTime.now());
        gameGeneratorRecord.setUpdatedAt(OffsetDateTime.now());
        gameGeneratorRecord.setLevel(1);
        gameGeneratorRecord.setGeneratedClick(BigDecimal.ZERO);
        repository.createGameGenerator(gameGeneratorRecord);
    }

    private Set<UUID> collectGeneratorIds(List<GeneratorRepresentation> storedGenerators, List<GeneratorRepresentation> draftGenerators) {
        return Stream.concat(storedGenerators.stream(), draftGenerators.stream())
                .map(GeneratorRepresentation::getId)
                .collect(Collectors.toSet());
    }

    private void checkManualClick(GameRepresentation stored, GameRepresentation draft, long secondDiff) {
        BigDecimal maxPossibleManualClick = new BigDecimal(clickerProperties.getAverageHumanClickPerSecond() * secondDiff);
        BigDecimal manualClickAdded = draft.getManualClickCount().subtract(stored.getManualClickCount());
        if (manualClickAdded.compareTo(maxPossibleManualClick) >= 0) {
            log.error("HACKED DETECTED PLAYER ID : " + stored.getPlayer().getId());
            throw new HackerException("Hacker detected");
        }
    }

    public void createGame(UUID playerId) {
        int nbRow = repository.createGameByPlayerId(playerId);
        if (nbRow != 1) {
            throw new InternalDatabaseServeurException("Can not create game during creation of player : " + playerId);
        }
    }
}
