package com.example.stock_microservice.security;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class StockResourceServerSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // GET para leer stock requiere scope "read"
                        .requestMatchers(HttpMethod.GET, "/api/stock/**").hasAuthority("SCOPE_read")

                        // POST para modificar stock requiere scope "write"
                        .requestMatchers(HttpMethod.POST, "/api/stock/**").hasAuthority("SCOPE_write")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                // Habilita JWT Resource Server
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // ✅ Nueva forma
        return http.build();
    }
}
