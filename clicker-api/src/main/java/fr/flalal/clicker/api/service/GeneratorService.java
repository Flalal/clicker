package fr.flalal.clicker.api.service;

import fr.flalal.clicker.api.repository.GeneratorRepository;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class GeneratorService {

    private final GeneratorRepository repository;


    public List<GeneratorRecord> findGeneratorsByIds(Set<UUID> generatorIds) {
        return repository.findGeneratorsByIds(generatorIds);
    }
}
