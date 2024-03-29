package fr.flalal.clicker.api.repository;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.model.GameModel;
import fr.flalal.clicker.api.model.GeneratorModel;
import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;
import fr.flalal.clicker.storage.tables.records.GameRecord;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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

    public GameModel findGameByPseudonym(String pseudo) {
        GameModel model = new GameModel();
        jooq.select(GAME.asterisk(), PLAYER.asterisk(), GENERATOR.asterisk(), GAME_GENERATOR.asterisk())
                .from(GAME)
                .join(PLAYER).on(GAME.PLAYER_ID.eq(PLAYER.ID))
                .leftJoin(GAME_GENERATOR).on(GAME_GENERATOR.ID_GAME.eq(GAME.ID))
                .leftJoin(GENERATOR).on(GENERATOR.ID.eq(GAME_GENERATOR.ID_GENERATOR))
                .where(PLAYER.PSEUDONYM.equalIgnoreCase(pseudo))
                .fetch()
                .forEach(record -> populateGameModel(model, record));
        return model;
    }

    private void populateGameModel(GameModel model, Record record) {
        model.setGameRecord(record.into(GameRecord.class));
        model.setPlayerRecord(record.into(PlayerRecord.class));
        GeneratorRecord generatorRecord = record.into(GeneratorRecord.class);
        GameGeneratorRecord gameGeneratorRecord = record.into(GameGeneratorRecord.class);
        GeneratorModel generatorModel = converter.toGeneratorModel(generatorRecord, gameGeneratorRecord);
        if (generatorModel != null && generatorModel.getId() != null) {
            model.getGeneratorModels().add(generatorModel);
        }
    }

    public int createGameByPlayerId(UUID playerId) {
        return jooq.insertInto(GAME)
                .set(GAME.ID, UUID.randomUUID())
                .set(GAME.PLAYER_ID, playerId)
                .set(GAME.CREATED_AT, OffsetDateTime.now())
                .set(GAME.UPDATED_AT, OffsetDateTime.now())
                .set(GAME.MONEY, new BigDecimal(0))
                .set(GAME.MANUAL_CLICK, new BigDecimal(0))
                .execute();
    }

    public int updateGameGenerator(UUID gameId, UUID generatorId, BigDecimal generatedClick, int level) {
        return jooq.update(GAME_GENERATOR)
                .set(GAME_GENERATOR.LEVEL, level)
                .set(GAME_GENERATOR.GENERATED_CLICK, generatedClick)
                .set(GAME_GENERATOR.UPDATED_AT, OffsetDateTime.now())
                .where(GAME_GENERATOR.ID_GAME.eq(gameId))
                .and(GAME_GENERATOR.ID_GENERATOR.eq(generatorId))
                .execute();
    }

    public void createGameGenerator(GameGeneratorRecord draftGenerator) {
        jooq.batchInsert(draftGenerator).execute();
    }

    public int updateMoney(UUID id, BigDecimal money) {
        return jooq.update(GAME)
                .set(GAME.MONEY, money)
                .where(GAME.ID.eq(id))
                .execute();
    }

    public int updateManualClick(UUID id, BigDecimal manualClickCount) {
        return jooq.update(GAME)
                .set(GAME.MANUAL_CLICK, manualClickCount)
                .where(GAME.ID.eq(id))
                .execute();
    }
}
