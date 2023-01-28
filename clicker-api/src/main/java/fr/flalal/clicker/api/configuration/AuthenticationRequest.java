package fr.flalal.clicker.api.configuration;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;

}
