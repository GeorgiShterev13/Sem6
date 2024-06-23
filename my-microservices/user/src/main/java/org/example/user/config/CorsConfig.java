package org.example.user.config;  // Replace with your actual package name

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allows unrestricted cross-origin access to all paths, methods, and headers
                // Adjust as necessary for your security requirements
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Allows only from this origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // or use allowedMethods("*") for all
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
