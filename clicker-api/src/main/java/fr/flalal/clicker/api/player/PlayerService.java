package fr.flalal.clicker.api.player;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.error.NoContentException;
import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.game.GameService;
import fr.flalal.clicker.api.representation.PlayerRepresentation;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final GameService gameService;
    private final Converter converter = Mappers.getMapper(Converter.class);

    public Flux<PlayerRepresentation> getAllPlayers() {
        List<PlayerRecord> playersRecord = repository.findAllPlayers();
        if (playersRecord.isEmpty()) {
            throw new NoContentException("No players in database");
        }
        return Flux.fromIterable(converter.toPlayersRepresentation(playersRecord));
    }

    @Transactional
    public Mono<PlayerRepresentation> createPlayer(@RequestBody PlayerDraft draft) throws Exception {
        PlayerRecord playerRecord = repository.createPlayer(draft);
        gameService.createGame(playerRecord.getId());
        return Mono.just(converter.toPlayerRepresentation(playerRecord));
    }

    public Mono<PlayerRepresentation> getPlayerByPseudonym(String pseudonym) {
        PlayerRecord player = repository.findPlayerByPseudonym(pseudonym);
        if (player == null) {
            throw new ResourceNotFoundException("Player not found");
        }
        return Mono.just(converter.toPlayerRepresentation(player));
    }
}
