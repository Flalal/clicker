package fr.flalal.clicker.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v0")
public class ClickerEndPoint {

    @RequestMapping("/test")
    public Mono<ResponseEntity<String>> getTest() {
        return Mono.just(ResponseEntity.ok("Test"));
    }
}
