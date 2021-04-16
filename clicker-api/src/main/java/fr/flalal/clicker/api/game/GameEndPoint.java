package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.representation.GameRepresentation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v0/games")
@Slf4j
@AllArgsConstructor
public class GameEndPoint {

    private final GameService service;

    @GetMapping("/{id}")
    public Mono<GameRepresentation> findGameById(@PathVariable UUID id) {
        return service.findGameById(id);
    }

    @PutMapping("/{id}")
    public Mono<GameRepresentation> updateGame(@PathVariable UUID id, @RequestBody GameRepresentation game) {
        if (!game.getId().equals(id)) {
            // TODO : precondition failed
        }
        return service.updateGame(game);
    }

}
