package fr.flalal.clicker.api.game;

import fr.flalal.clicker.api.error.PreconditionFailedException;
import fr.flalal.clicker.api.representation.GameRepresentation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v0/games")
public class GameEndPoint {

    private final GameService service;

    public GameEndPoint(GameService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Mono<GameRepresentation> findGameById(@PathVariable UUID id) {
        return service.findGameById(id);
    }

    @PutMapping("/{id}")
    public Mono<GameRepresentation> updateGame(@PathVariable UUID id, @RequestBody GameRepresentation game) {
        if (!game.getId().equals(id)) {
            throw new PreconditionFailedException("Try to update an id game different of id game in body");
        }
        return service.updateGame(game);
    }

}
