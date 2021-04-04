package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.player.PlayerConverter;
import fr.flalal.clicker.api.representation.GameRepresentation;
import fr.flalal.clicker.api.representation.GeneratorRepresentation;
import fr.flalal.clicker.api.representation.ManualClickRepresentation;
import lombok.AllArgsConstructor;
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

    private GameRepository repository;
    private PlayerConverter playerConverter;

    public Mono<GameRepresentation> findGameById(UUID id) {
        GameModel gameModel = repository.findGameById(id);
        return Mono.just(toRepresentation(gameModel));
    }

    private GameRepresentation toRepresentation(GameModel model) {
        GameRepresentation representation = new GameRepresentation();

        representation.setId(model.getGameRecord().getId());
        representation.setMoney(model.getGameRecord().getMoney());

        representation.setManualClick(new ManualClickRepresentation(model.getGameRecord().getManualClick()));
        representation.setPlayer(playerConverter.toRepresentation(model.getPlayerRecord()));
        representation.setGenerators(toRepresentation(model.getGeneratorModels()));

        representation.setCreatedAt(OffsetDateTime.of(model.getGameRecord().getCreatedAt(), ZoneOffset.UTC));
        representation.setUpdatedAt(OffsetDateTime.of(model.getGameRecord().getUpdatedAt(), ZoneOffset.UTC));

        return representation;
    }

    private List<GeneratorRepresentation> toRepresentation(List<GeneratorModel> models) {
        return models.stream()
                .map(this::toRepresentation).collect(Collectors.toList());
    }

    private GeneratorRepresentation toRepresentation(GeneratorModel model) {
        GeneratorRepresentation representation = new GeneratorRepresentation();
        representation.setId(model.getId());
        representation.setLevel(model.getLevel());
        representation.setName(model.getName());
        representation.setDescription(model.getDescription());
        representation.setBaseMultiplier(model.getBaseMultiplier());
        representation.setBaseClickPerSecond(model.getBaseClickPerSecond());
        representation.setBaseCost(model.getBaseCost());
        representation.setCreatedAt(model.getCreatedAt());
        representation.setUpdatedAt(model.getUpdatedAt());
        return representation;
    }
}
