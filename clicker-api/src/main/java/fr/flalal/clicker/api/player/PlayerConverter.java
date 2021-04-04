package fr.flalal.clicker.api.player;

import fr.flalal.clicker.api.representation.PlayerRepresentation;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerConverter {

    public List<PlayerRepresentation> toRepresentation(List<PlayerRecord> playersRecord) {
        return playersRecord.stream()
                .map(this::toRepresentation)
                .collect(Collectors.toList());
    }

    public PlayerRepresentation toRepresentation(PlayerRecord record) {
        PlayerRepresentation representation = new PlayerRepresentation();
        representation.setId(record.getId());
        representation.setEmail(record.getEmail());
        representation.setFirstName(record.getFirstName());
        representation.setLastname(record.getLastName());
        representation.setPseudonym(record.getPseudonym());
        representation.setRole(record.getRole());
        return representation;
    }

}
