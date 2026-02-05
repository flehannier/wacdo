package com.wacdo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "https://wacdo.neofit.fr",
                "http://wacdo.neofit.fr",
                "/v3/api-docs/**",
                "/swagger-ui/**"
        ));
        // Méthodes HTTP autorisées
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Tous les headers sont autorisés
        config.setAllowedHeaders(List.of("*"));

        // Expose Authorization pour JWT par exemple
        config.setExposedHeaders(List.of("Authorization"));

        // Autorise l'envoi de cookies et credentials
        config.setAllowCredentials(true);

        // Configuration source
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
