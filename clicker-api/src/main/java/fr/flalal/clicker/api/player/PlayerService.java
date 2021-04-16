package fr.flalal.clicker.api.player;

import fr.flalal.clicker.api.error.NoContentException;
import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.representation.PlayerRepresentation;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerConverter converter;

    public Flux<PlayerRepresentation> getAllPlayers() {
        List<PlayerRecord> playersRecord = repository.findAllPlayers();
        if (playersRecord.isEmpty()) {
            throw new NoContentException("No players in database");
        }
        return Flux.fromIterable(converter.toRepresentation(playersRecord));
    }

    public Mono<PlayerRepresentation> createPlayer(@RequestBody PlayerDraft draft) throws Exception {
        return Mono.just(converter.toRepresentation(repository.createPlayer(draft)));
    }
}
