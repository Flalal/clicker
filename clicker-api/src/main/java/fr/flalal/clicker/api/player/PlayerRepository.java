package fr.flalal.clicker.api.player;

import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static fr.flalal.clicker.storage.Tables.PLAYER;

@Repository
@AllArgsConstructor
public class PlayerRepository {

    private final DSLContext jooq;

    public List<PlayerRecord> findAllPlayers() {
        return jooq.selectFrom(PLAYER).fetch();
    }

}
