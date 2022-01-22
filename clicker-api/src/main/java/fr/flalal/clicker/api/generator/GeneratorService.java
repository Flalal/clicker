package fr.flalal.clicker.api.generator;

import fr.flalal.clicker.api.Converter;
import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static fr.flalal.clicker.storage.Tables.GENERATOR;

@Service
@AllArgsConstructor
@Slf4j
public class GeneratorService {

    private final GeneratorRepository repository;


    public List<GeneratorRecord> findGeneratorsByIds(Set<UUID> generatorIds) {
        return repository.findGeneratorsByIds(generatorIds);
    }
}
