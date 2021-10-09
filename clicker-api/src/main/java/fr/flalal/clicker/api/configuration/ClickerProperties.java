package fr.flalal.clicker.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("clicker-api")
public class ClickerProperties {
    private int averageHumanClickPerSecond = 10;
}
