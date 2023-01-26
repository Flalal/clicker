package fr.flalal.clicker.api.endpoint;

import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.draft.PlayerDraft;
import fr.flalal.clicker.api.service.PlayerService;
import fr.flalal.clicker.api.representation.GameRepresentation;
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

    private final PlayerService service;

    @GetMapping
    public Flux<PlayerRepresentation> getAllPlayers() {
        return service.getAllPlayers();
    }

    // FIXME
    @CrossOrigin("*")
    @GetMapping("/{pseudonym}/games")
    public Mono<GameRepresentation> getGameByPseudonym(@PathVariable String pseudonym) {
        return service.getGameByPseudonym(pseudonym);
    }

    @GetMapping("/{pseudonym}")
    public Mono<PlayerRepresentation> getPlayerByPseudonym(@PathVariable String pseudonym) {
        return service.getPlayerByPseudonym(pseudonym);
    }

    @PostMapping
    public Mono<PlayerRepresentation> createPlayer(@RequestBody PlayerDraft draft) throws Exception {
        // TODO : validator on draft
        return service.createPlayer(draft);
    }
}
