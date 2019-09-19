package fr.flalal.clicker.api.player;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v0")
@AllArgsConstructor
public class PlayerEndPoint {

    private PlayerService service;

    @RequestMapping("/players")
    public Flux<PlayerRepresentation> getAllPlayers() {
        return service.getAllPlayers();
    }
}
