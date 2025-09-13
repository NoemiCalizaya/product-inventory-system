package com.example.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .authorizeExchange(authz -> authz
                        // Endpoints públicos necesarios para OIDC
                        .pathMatchers("/.well-known/openid-configuration", "/oauth2/jwks").permitAll()
                        .pathMatchers("/authorized", "/logout").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/categories/create", "/api/products/create", "/api/suppliers/create",
                                "/api/purchases/create", "/api/purchase-items/create")
                                .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/stock/**", "/api/booking/**", "/api/users/**", "/api/suppliers/create",
                                "/api/purchases/**", "/api/purchase-items/**")
                                .permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/items/{id}", "/api/products/**", "/api/users/{id}")
                        .hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/api/items/**", "/api/products/**", "/api/users/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .oauth2Login(login -> login
                        .loginPage("/oauth2/authorization/gateway-app")
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            return webFilterExchange.getExchange()
                                    .getResponse()
                                    .setComplete()
                                    .then(Mono.fromRunnable(() -> {
                                        webFilterExchange.getExchange().getResponse().getHeaders()
                                                .setLocation(URI.create("https://oauthdebugger.com/debug"));
                                    }));
                        })
                )
                //.oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(source -> {
                            Collection<String> roles = Optional.ofNullable(source.getClaimAsStringList("scope"))
                                    .orElse(Collections.emptyList());
                            Collection<GrantedAuthority> authorities = roles.stream()
                                    .map(role -> "ROLE_" + role)
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            return Mono.just(new JwtAuthenticationToken(source, authorities));
                        })
                ))
                .build();
    }
}
