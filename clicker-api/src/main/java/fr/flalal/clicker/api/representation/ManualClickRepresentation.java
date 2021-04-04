package fr.flalal.clicker.api.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ManualClickRepresentation {
    private BigDecimal manualClickCount;
}
