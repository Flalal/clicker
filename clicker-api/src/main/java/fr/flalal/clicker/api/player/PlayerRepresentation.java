package fr.flalal.clicker.api.player;

import fr.flalal.clicker.storage.enums.UserRoleType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PlayerRepresentation {
    private UUID id;
    private String firstName;
    private String lastname;
    private String email;
    private String pseudonym;
    private UserRoleType role;
}
