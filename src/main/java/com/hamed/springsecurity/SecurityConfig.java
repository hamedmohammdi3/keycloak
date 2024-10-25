package com.hamed.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakAuthority());
        return
                http.authorizeHttpRequests(customizer ->
                            customizer
                                    .requestMatchers("/token").permitAll()
                                    .requestMatchers(HttpMethod.GET,"/book").hasRole("book_reader")
                                    .requestMatchers(HttpMethod.POST,"/book").hasRole("book_writer")
                                    .requestMatchers(HttpMethod.POST,"/profile").hasAuthority("SCOPE_PROFILE")
                                    .anyRequest()
                                    .authenticated()
                ).oauth2ResourceServer(
                        configurer ->
                                configurer.jwt(customizer -> customizer.jwtAuthenticationConverter(jwtAuthenticationConverter))
                                ).build();
    }
}
