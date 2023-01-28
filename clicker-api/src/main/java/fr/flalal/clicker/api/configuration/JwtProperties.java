package fr.flalal.clicker.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey = "flzxsqcysyhljt";
    private long validityInMs = 3_600_000;
}
