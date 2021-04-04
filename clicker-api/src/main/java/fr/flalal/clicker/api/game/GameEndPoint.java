package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.player.PlayerDraft;
import fr.flalal.clicker.api.representation.PlayerRepresentation;
import fr.flalal.clicker.api.representation.GameRepresentation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v0/games")
@Slf4j
@AllArgsConstructor
public class GameEndPoint {

    private GameService service;

    @GetMapping("/{id}")
    public Mono<GameRepresentation> findGameById(@PathVariable UUID id) {
        return service.findGameById(id);
    }

}
