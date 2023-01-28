package fr.flalal.clicker.api;

import fr.flalal.clicker.api.configuration.JwtTokenAuthenticationFilter;
import fr.flalal.clicker.api.provider.JwtTokenProvider;
import fr.flalal.clicker.api.repository.PlayerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ClickerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickerApiApplication.class, args);
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityWebFilterChain springWebFilterChain(
            ServerHttpSecurity http,
            JwtTokenProvider tokenProvider) {
        return http
                .csrf(it -> it.disable())
                .httpBasic(it -> it.disable())
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it ->
                        it.pathMatchers("/api/v0").permitAll()
                                .anyExchange().permitAll()
                )
                .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(PlayerRepository users) {
        return username -> Mono.fromCallable(() -> users.findPlayerByPseudonym(username))
                .map(u -> User.withUsername("u.getUsername()")
                        .password("u.getPassword()")
                        .authorities("u.getRoles().toArray(new String[0])")
                        .accountExpired("!u.isActive()" != null)
                        .credentialsExpired("!u.isActive()" != null)
                        .disabled("!u.isActive()" != null)
                        .accountLocked("!u.isActive()" != null)
                        .build()
                ).subscribeOn(Schedulers.boundedElastic());
    }
}
