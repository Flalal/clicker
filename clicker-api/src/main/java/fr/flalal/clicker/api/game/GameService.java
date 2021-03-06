package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.representation.GameRepresentation;
import fr.flalal.clicker.api.representation.GeneratorRepresentation;
import fr.flalal.clicker.api.representation.ManualClickRepresentation;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
}
