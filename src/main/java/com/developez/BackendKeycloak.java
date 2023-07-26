package com.developez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity( prePostEnabled = true, securedEnabled = true, jsr250Enabled = true )
@PropertySource("classpath:application-secret.properties")
public class BackendKeycloak {

    public static void main( String[] args ) {
        SpringApplication.run( BackendKeycloak.class, args );
    }

}
