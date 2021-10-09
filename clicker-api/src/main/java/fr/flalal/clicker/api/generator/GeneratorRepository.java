package fr.flalal.clicker.api.generator;

import fr.flalal.clicker.storage.tables.records.GeneratorRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static fr.flalal.clicker.storage.Tables.GENERATOR;

@Repository
@AllArgsConstructor
public class GeneratorRepository {

    private final DSLContext jooq;


    public List<GeneratorRecord> findGeneratorsByIds(Set<UUID> generatorIds) {
        return jooq.selectFrom(GENERATOR)
                .where(GENERATOR.ID.in(generatorIds))
                .fetch();
    }
}
