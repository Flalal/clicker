package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.error.InternalDatabaseServeurException;
import fr.flalal.clicker.api.representation.GameRepresentation;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final Converter converter = Mappers.getMapper(Converter.class);

    public Mono<GameRepresentation> findGameById(UUID id) {
        return Mono.just(converter.toGameRepresentation(repository.findGameById(id)));
    }

    public Mono<GameRepresentation> updateGame(GameRepresentation draft) {
        return findGameById(draft.getId())
                .map(gameStored -> compareGame(gameStored, draft));
    }

    private GameRepresentation compareGame(GameRepresentation stored, GameRepresentation draft) {
        long secondDiff = (OffsetDateTime.now().toEpochSecond() - stored.getUpdatedAt().toEpochSecond()) / 1000;

//        var computedClickByGenerator = draft.getGenerators().stream()
//                .map(generator -> {
                    // TODO : compare generator click
//                    BigDecimal actualPossibleMultiplier = generator.getBaseMultiplier().multiply(BigDecimal.valueOf(generator.getLevel()));
//                    BigDecimal numberOfPossibleGeneratedClick = actualMultiplier.multiply(generator.getBaseClickPerSecond()).multiply(BigDecimal.valueOf(secondDiff));

//                });

        return draft;
    }

    public void createGame(UUID playerId) {
        int nbRow = repository.createGameByPlayerId(playerId);
        if (nbRow != 1) {
            throw new InternalDatabaseServeurException("Can not create game during creation of player : " + playerId);
        }
    }
}
