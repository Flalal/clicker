package fr.flalal.clicker.api.representation;

import fr.flalal.clicker.storage.enums.UserRoleType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class PlayerRepresentation {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String pseudonym;
    private UserRoleType role;
}
