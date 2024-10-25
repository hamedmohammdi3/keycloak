package com.hamed.springsecurity;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Stream;

public class KeycloakAuthority implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return Stream.concat(roles(jwt),scopes(jwt)).toList();
    }
    private Stream<GrantedAuthority> roles(Jwt jwt) {
     Map<String, Object> realmAccess =(Map<String, Object>) jwt.getClaims().getOrDefault("realm_access",List.of());
     if(realmAccess.isEmpty()) return Stream.empty();
     return ((List<String>) realmAccess.getOrDefault("reles",List.of())).stream()
             .map(role -> new SimpleGrantedAuthority("ROLE_"+role));
    }
    private Stream<GrantedAuthority> scopes(Jwt jwt) {
        String scope =(String) jwt.getClaims().getOrDefault("scope",List.of());
        return Arrays.stream(scope.split(" "))
                .map(s -> new SimpleGrantedAuthority("SCOPE_"+s));
    }
}
