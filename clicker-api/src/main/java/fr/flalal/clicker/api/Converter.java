package fr.flalal.clicker.api;

import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.game.GameModel;
import fr.flalal.clicker.api.game.GeneratorModel;
import fr.flalal.clicker.api.representation.GameRepresentation;
import fr.flalal.clicker.api.representation.GeneratorRepresentation;
import fr.flalal.clicker.api.representation.ManualClickRepresentation;
import fr.flalal.clicker.api.representation.PlayerRepresentation;
import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public interface Converter {

    PlayerRepresentation toPlayerRepresentation(PlayerRecord record);

    List<GeneratorRepresentation> toGeneratorsRepresentation(List<GeneratorModel> models);

    List<PlayerRepresentation> toPlayersRepresentation(List<PlayerRecord> playersRecord);

    GeneratorModel toGeneratorModel(GeneratorRecord generator, GameGeneratorRecord gameGenerator);

    default GameRepresentation toGameRepresentation(GameModel model) {
        GameRepresentation representation = new GameRepresentation();
        if (model.getGameRecord() == null) {
            throw new ResourceNotFoundException("No game here");
        }

        representation.setId(model.getGameRecord().getId());
        representation.setMoney(model.getGameRecord().getMoney());
        representation.setManualClick(new ManualClickRepresentation(model.getGameRecord().getManualClick()));
        representation.setPlayer(this.toPlayerRepresentation(model.getPlayerRecord()));
        representation.setGenerators(this.toGeneratorsRepresentation(model.getGeneratorModels()));
        representation.setCreatedAt(model.getGameRecord().getCreatedAt());
        representation.setUpdatedAt(model.getGameRecord().getUpdatedAt());

        return representation;
    }

}
