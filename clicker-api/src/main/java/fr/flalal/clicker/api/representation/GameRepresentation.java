package fr.flalal.clicker.api.representation;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class GameRepresentation {
    private UUID id;
    private BigDecimal money;
    private PlayerRepresentation player;
    private ManualClickRepresentation manualClick;
    private List<GeneratorRepresentation> generators = new ArrayList<>();
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
