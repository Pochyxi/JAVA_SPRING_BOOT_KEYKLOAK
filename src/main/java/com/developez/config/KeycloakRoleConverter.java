package com.developez.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// bin/kc.sh start-dev --http-port 8180
// bin/kc.sh start-dev --http-port 8180 --spi-theme-static-max-age=-1 --spi-theme-cache-themes=false
// bin/kc.bat start-dev --http-port 8180 --spi-theme-static-max-age=-1 --spi-theme-cache-themes=false
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    @SuppressWarnings( "unchecked" )
    public Collection<GrantedAuthority> convert( Jwt jwt ) {
        Map<String, Object> realmAccess = ( Map<String, Object> ) jwt.getClaims().get( "realm_access" );

        if( realmAccess == null || realmAccess.isEmpty() ) {
            return new ArrayList<>();
        }

        return (( List<String> ) realmAccess.get( "roles" ))
                .stream().map( roleName -> "ROLE_" + roleName )
                .map( SimpleGrantedAuthority::new )
                .collect( Collectors.toList() );
    }

}
