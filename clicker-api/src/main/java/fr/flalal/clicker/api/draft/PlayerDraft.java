package fr.flalal.clicker.api.draft;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDraft {
    private String firstName;
    private String lastName;
    private String email;
    private String pseudonym;
}
