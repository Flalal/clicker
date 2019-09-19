package fr.flalal.clicker.api.player;

import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {

    private PlayerRepository repository;

    public Flux<PlayerRepresentation> getAllPlayers() {
        List<PlayerRecord> playersRecord = repository.findAllPlayers();
        return Flux.fromIterable(toRepresentation(playersRecord));
    }

    private List<PlayerRepresentation> toRepresentation(List<PlayerRecord> playersRecord) {
        return playersRecord.stream().map(record ->
                PlayerRepresentation.builder()
                        .id(record.getId())
                        .firstName(record.getFirstName())
                        .lastname(record.getLastName())
                        .email(record.getEmail())
                        .pseudonym(record.getPseudonym())
                        .role(record.getRole())
                        .build()
        ).collect(Collectors.toList());
    }
}
