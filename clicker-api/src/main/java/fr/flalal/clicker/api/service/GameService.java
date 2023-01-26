package fr.flalal.clicker.api.service;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.configuration.ClickerProperties;
import fr.flalal.clicker.api.error.HackerException;
import fr.flalal.clicker.api.error.InternalDatabaseServerException;
import fr.flalal.clicker.api.repository.GameRepository;
import fr.flalal.clicker.api.representation.GameRepresentation;
import fr.flalal.clicker.api.representation.GeneratorRepresentation;
import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final ClickerProperties clickerProperties;
    private final GeneratorService generatorService;
    private final Converter converter = Mappers.getMapper(Converter.class);

    public Mono<GameRepresentation> findGameById(UUID id) {
        return Mono.fromCallable(() -> repository.findGameById(id))
                .map(converter::toGameRepresentation);
    }

    public Mono<GameRepresentation> findGameByPseudonym(String pseudo) {
        return Mono.fromCallable(() -> repository.findGameByPseudonym(pseudo))
                .map(converter::toGameRepresentation);
    }

    public Mono<GameRepresentation> updateGame(GameRepresentation draft) {
        return findGameById(draft.getId())
                .flatMap(gameStored -> compareGame(gameStored, draft));
    }

    @Transactional
    Mono<GameRepresentation> compareGame(GameRepresentation stored, GameRepresentation draft) {
        long secondDiff = (OffsetDateTime.now().toEpochSecond() - stored.getUpdatedAt().toEpochSecond());

        return checkAndUpdateMoney(stored, draft, secondDiff)
                .flatMap(nbRow -> checkAndUpdateManualClick(stored, draft, secondDiff))
                .flatMap(nbRow -> checkAndUpdateGeneratorClick(stored, draft, secondDiff))
                .flatMap(nbRows -> Mono.fromCallable(() -> repository.findGameById(stored.getId())))
                .map(converter::toGameRepresentation);
    }

    private Mono<Integer> checkAndUpdateMoney(GameRepresentation stored, GameRepresentation draft, long secondDiff) {
        // TODO check here
        return Mono.fromCallable(() -> repository.updateMoney(stored.getId(), draft.getMoney()))
                .flatMap(nbRow -> {
                    if (nbRow != 1) {
                        log.error("Update of money failed");
                        return Mono.error(new InternalDatabaseServerException("Update of money failed"));
                    }
                    return Mono.just(nbRow);
                });
    }

    // FIXME : must be refactored to used fromCallable
    private Mono<List<Integer>> checkAndUpdateGeneratorClick(GameRepresentation stored, GameRepresentation draft, long secondDiff) {
        List<GeneratorRepresentation> storedGenerators = stored.getGenerators();
        List<GeneratorRepresentation> draftGenerators = draft.getGenerators();
        return generatorService.findGeneratorsByIds(collectGeneratorIds(storedGenerators, draftGenerators))
                .flatMapMany(allGenerators -> Flux.fromIterable(draftGenerators)
                        .flatMap(draftGenerator -> verifyAndUpdateGameGenerator(draft, secondDiff, storedGenerators, allGenerators, draftGenerator))
                ).collectList();

    }

    private Mono<Integer> verifyAndUpdateGameGenerator(GameRepresentation draft, long secondDiff, List<GeneratorRepresentation> storedGenerators, List<GeneratorRecord> allGenerator, GeneratorRepresentation draftGenerator) {
        Optional<GeneratorRepresentation> optionalStoredGenerator = storedGenerators.stream()
                .filter(generatorRepresentation -> draftGenerator.getId().equals(generatorRepresentation.getId()))
                .findFirst();
        Optional<GeneratorRecord> optionalGenerator = allGenerator.stream()
                .filter(generatorRecord -> draftGenerator.getId().equals(generatorRecord.getId()))
                .findFirst();

        if (optionalGenerator.isEmpty()) {
            log.error("Generator not found maybe for generator id : {}", draftGenerator.getId());
        }

        BigDecimal generatedClickByGenerator;
        if (optionalStoredGenerator.isEmpty()) {
            generatedClickByGenerator = BigDecimal.ZERO;
            createNewGameGenerator(draft.getId(), draftGenerator, optionalGenerator.get());
        } else {
            GeneratorRecord generator = optionalGenerator.get();
            GeneratorRepresentation storedGenerator = optionalStoredGenerator.get();
            checkGeneratedClickAlreadyInGame(secondDiff, draftGenerator, generator, storedGenerator);
            generatedClickByGenerator = draftGenerator.getGeneratedClick().subtract(storedGenerator.getGeneratedClick()).longValue() > 0 ? draftGenerator.getGeneratedClick() : storedGenerator.getGeneratedClick() ;
        }

        // With generated click and check value of actual lvl
        // TODO check draftGenerator.getLevel() from front

        return Mono.fromCallable(() -> repository.updateGameGenerator(draft.getId(), optionalGenerator.get().getId(), generatedClickByGenerator, draftGenerator.getLevel()))
                .flatMap(nbRow -> {
                    if (nbRow != 1) {
                        log.error("Update of game generator failed");
                        return Mono.error(new InternalDatabaseServerException("Update of game generator failed"));
                    }
                    return Mono.just(nbRow);
                });
    }

    private void checkGeneratedClickAlreadyInGame(long secondDiff, GeneratorRepresentation draftGenerator, GeneratorRecord generator, GeneratorRepresentation storedGenerator) {
        int draftLevel = draftGenerator.getLevel();
        BigDecimal maxGeneratedClick = generator.getBaseMultiplier().multiply(new BigDecimal(draftLevel)).multiply(new BigDecimal(secondDiff));
        BigDecimal generatedClickSinceLastUpdate = draftGenerator.getGeneratedClick().subtract(storedGenerator.getGeneratedClick());
        if (maxGeneratedClick.compareTo(generatedClickSinceLastUpdate) <= 0) {
            log.error("Hacker detected");
            throw new HackerException("Hacker detected");
        }
    }

    private void createNewGameGenerator(UUID gameId, GeneratorRepresentation draftGenerator, GeneratorRecord generator) {
        // Must be lvl 1
        if (draftGenerator.getLevel() != 1) {
            log.error("New Game generator not lvl 1");
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

    private Mono<Integer> checkAndUpdateManualClick(GameRepresentation stored, GameRepresentation draft, long secondDiff) {
        BigDecimal maxManualPossibleClick = new BigDecimal(clickerProperties.getAverageHumanClickPerSecond() * secondDiff);
        BigDecimal manualClickAdded = draft.getManualClickCount().subtract(stored.getManualClickCount());
        if (manualClickAdded.compareTo(maxManualPossibleClick) >= 0) {
            log.error("HACKER DETECTED PLAYER ID : " + stored.getPlayer().getId());
            throw new HackerException("Hacker detected");
        }

        return Mono.fromCallable(() -> repository.updateManualClick(stored.getId(), draft.getManualClickCount()))
                .map(nbRow -> {
                    if (nbRow != 1) {
                        log.error("Can update manual click for game : {}", stored.getId());
                        return nbRow;
                    }
                    return nbRow;
                });
    }

    public Mono<Integer> createGame(UUID playerId) {
        return Mono.fromCallable(() -> repository.createGameByPlayerId(playerId))
                .flatMap(nbRow -> {
                    if (nbRow != 1) {
                        log.error("Can not create game during creation of player : {}", playerId);
                        return Mono.error(new InternalDatabaseServerException("Can not create game during creation of player : " + playerId));
                    }
                    return Mono.just(nbRow);
                });
    }
}
