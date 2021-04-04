package fr.flalal.clicker.api.game;

import fr.flalal.clicker.storage.tables.records.GameGeneratorRecord;
import fr.flalal.clicker.storage.tables.records.GameRecord;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static fr.flalal.clicker.storage.Tables.*;

@Repository
@AllArgsConstructor
public class GameRepository {

    private final DSLContext jooq;

    public GameModel findGameById(UUID gameId) {
        GameModel model = new GameModel();
        jooq.select(GAME.asterisk(), PLAYER.asterisk(), GENERATOR.asterisk(), GAME_GENERATOR.asterisk())
                .from(GAME)
                .join(PLAYER).on(GAME.PLAYER_ID.eq(PLAYER.ID))
                .join(GAME_GENERATOR).on(GAME_GENERATOR.ID_GAME.eq(GAME.ID))
                .join(GENERATOR).on(GENERATOR.ID.eq(GAME_GENERATOR.ID_GENERATOR))
                .where(GAME.ID.eq(gameId))
                .fetch()
                .forEach(record -> {
                    model.setGameRecord(record.into(GameRecord.class));
                    model.setPlayerRecord(record.into(PlayerRecord.class));
                    GeneratorModel generatorModel = toGeneratorModel(record.into(GeneratorRecord.class), record.into(GameGeneratorRecord.class));
                    model.getGeneratorModels().add(generatorModel);
                });
        return model;
    }

    private GeneratorModel toGeneratorModel(GeneratorRecord generator, GameGeneratorRecord gameGenerator) {
        GeneratorModel model = new GeneratorModel();
        model.setId(generator.getId());
        model.setLevel(gameGenerator.getLevel());
        model.setName(generator.getName());
        model.setDescription(generator.getDescription());

        model.setBaseCost(generator.getBaseCost());
        model.setBaseMultiplier(generator.getBaseMultiplier());
        model.setBaseClickPerSecond(generator.getBaseClickPerSecond());

        model.setCreatedAt(OffsetDateTime.of(gameGenerator.getCreatedAt(), ZoneOffset.UTC));
        model.setUpdatedAt(OffsetDateTime.of(gameGenerator.getUpdatedAt(), ZoneOffset.UTC));
        return model;
    }

}
