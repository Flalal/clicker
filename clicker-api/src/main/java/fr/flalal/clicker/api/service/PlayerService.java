package fr.flalal.clicker.api.service;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.api.error.NoContentException;
import fr.flalal.clicker.api.error.ResourceNotFoundException;
import fr.flalal.clicker.api.draft.PlayerDraft;
import fr.flalal.clicker.api.repository.PlayerRepository;
import fr.flalal.clicker.api.representation.GameRepresentation;
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
        return Mono.fromCallable(repository::findAllPlayers)
                .flatMapIterable(playerRecords -> playerRecords)
                .map(converter::toPlayerRepresentation);
    }

    @Transactional
    public Mono<PlayerRepresentation> createPlayer(@RequestBody PlayerDraft draft) throws Exception {
        return Mono.fromCallable(() -> repository.createPlayer(draft))
                .flatMap(playerRecord -> gameService.createGame(playerRecord.getId()).map(nbRow -> playerRecord))
                .map(converter::toPlayerRepresentation);
    }

    public Mono<PlayerRepresentation> getPlayerByPseudonym(String pseudonym) {
        return Mono.fromCallable(() -> repository.findPlayerByPseudonym(pseudonym))
                .map(converter::toPlayerRepresentation)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Player not found")));
    }

    public Mono<GameRepresentation> getGameByPseudonym(String pseudonym) {
        return gameService.findGameByPseudonym(pseudonym);
    }
}
