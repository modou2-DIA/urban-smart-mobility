

package com.city.smart.mobility_graphql_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Appliquer la configuration à TOUTES les requêtes (y compris /graphql)
                .allowedOrigins("http://localhost:4200") // Autoriser explicitement l'origine Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées (OPTIONS est pour le preflight)
                .allowedHeaders("*") // Autoriser tous les en-têtes (y compris Content-Type)
                .allowCredentials(true);
    }
}