package fr.flalal.clicker.api.player;

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
        return PlayerRepresentation.builder()
                .id(record.getId())
                .firstName(record.getFirstName())
                .lastname(record.getLastName())
                .email(record.getEmail())
                .pseudonym(record.getPseudonym())
                .role(record.getRole())
                .build();
    }

}
