package fr.flalal.clicker.api.player;

import fr.flalal.clicker.api.representation.PlayerRepresentation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0/players")
@Slf4j
@AllArgsConstructor
public class PlayerEndPoint {

    private PlayerService service;

    @GetMapping
    public Flux<PlayerRepresentation> getAllPlayers() throws Exception {
        try {
            return service.getAllPlayers();
        } catch (Exception e) {
            // TODO : log some good error
            log.error("Error");
            throw e;
        }
    }

    @PostMapping
    public Mono<PlayerRepresentation> createPlayer(@RequestBody PlayerDraft draft) throws Exception {
        // TODO : validator on draft
        try {
            return service.createPlayer(draft);
        } catch (Exception e) {
            // TODO : log some good error
            log.error("Error");
            throw e;
        }
    }
}
