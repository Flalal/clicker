package fr.flalal.clicker.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class GeneratorModel {
    private UUID id;
    private int level;
    private String name;
    private String description;
    private BigDecimal generatedClick;
    private BigDecimal baseCost;
    private BigDecimal baseMultiplier;
    private BigDecimal baseClickPerSecond;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
