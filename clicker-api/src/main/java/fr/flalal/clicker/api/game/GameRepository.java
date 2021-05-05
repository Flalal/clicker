package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;
import fr.flalal.clicker.storage.tables.records.GameRecord;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static fr.flalal.clicker.storage.Tables.*;

@Repository
@AllArgsConstructor
public class GameRepository {

    private final DSLContext jooq;
    private final Converter converter = Mappers.getMapper(Converter.class);

    public GameModel findGameById(UUID gameId) {
        GameModel model = new GameModel();
        jooq.select(GAME.asterisk(), PLAYER.asterisk(), GENERATOR.asterisk(), GAME_GENERATOR.asterisk())
                .from(GAME)
                .join(PLAYER).on(GAME.PLAYER_ID.eq(PLAYER.ID))
                .leftJoin(GAME_GENERATOR).on(GAME_GENERATOR.ID_GAME.eq(GAME.ID))
                .leftJoin(GENERATOR).on(GENERATOR.ID.eq(GAME_GENERATOR.ID_GENERATOR))
                .where(GAME.ID.eq(gameId))
                .fetch()
                .forEach(record -> populateGameModel(model, record));
        return model;
    }

    private void populateGameModel(GameModel model, org.jooq.Record record) {
        model.setGameRecord(record.into(GameRecord.class));
        model.setPlayerRecord(record.into(PlayerRecord.class));
        GeneratorRecord generatorRecord = record.into(GeneratorRecord.class);
        GameGeneratorRecord gameGeneratorRecord = record.into(GameGeneratorRecord.class);
        GeneratorModel generatorModel = converter.toGeneratorModel(generatorRecord, gameGeneratorRecord);
        if (generatorModel != null) {
            model.getGeneratorModels().add(generatorModel);
        }
    }

}
