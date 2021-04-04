package fr.flalal.clicker.api.player;

import fr.flalal.clicker.storage.enums.UserRoleType;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static fr.flalal.clicker.storage.Tables.PLAYER;

@Repository
@AllArgsConstructor
public class PlayerRepository {

    private final DSLContext jooq;

    public List<PlayerRecord> findAllPlayers() {
        return jooq.selectFrom(PLAYER).fetch();
    }

    public PlayerRecord createPlayer(PlayerDraft draft) throws Exception {
        PlayerRecord record = new PlayerRecord();
        record.setId(UUID.randomUUID());
        record.setFirstName(draft.getFirstName());
        record.setLastName(draft.getLastName());
        record.setEmail(draft.getEmail());
        record.setPseudonym(draft.getPseudonym());
        record.setRole(UserRoleType.USER);
        int row = jooq.executeInsert(record);
        if (row != 1) {
            throw new Exception();
        }
        return record;
    }
}
