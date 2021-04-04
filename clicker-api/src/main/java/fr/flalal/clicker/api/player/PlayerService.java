package fr.flalal.clicker.api.player;

import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {

    private PlayerRepository repository;
    private PlayerConverter converter;

    public Flux<PlayerRepresentation> getAllPlayers() throws Exception {
        List<PlayerRecord> playersRecord = repository.findAllPlayers();
        // TODO : Create some Exception customer
        if (playersRecord.isEmpty()) {
            throw new Exception();
        }
        return Flux.fromIterable(converter.toRepresentation(playersRecord));
    }

    public Mono<PlayerRepresentation> createPlayer(@RequestBody PlayerDraft draft) throws Exception {
        return Mono.just(converter.toRepresentation(repository.createPlayer(draft)));
    }
}
