package com.developez.config;

import com.developez.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

/* @Configuration, indica che è una classe di configurazione per l'applicazione Spring.
 */
@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain( HttpSecurity http ) throws Exception {
        // Crea un nuovo gestore di attributi di richiesta CSRF
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        // Imposta il nome dell'attributo della richiesta CSRF a "_csrf"
        requestHandler.setCsrfRequestAttributeName( "_csrf" );

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // Configura la catena di filtri di sicurezza
        http
                // Configura la gestione della sessione
                .sessionManagement( ( sessionManagement ) -> sessionManagement
                        .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                )
                // Configura CORS
                .cors( ( httpSecurityCorsConfigurer ) -> httpSecurityCorsConfigurer
                        .configurationSource( ( httpServletRequest ) -> {
                            // Crea una nuova configurazione CORS
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            // Consente le richieste da "http://localhost:4200"
                            corsConfiguration.setAllowedOrigins( List.of( "http://127.0.0.1:5173/", "http://localhost:5173/" ) );
                            // Consente tutti i metodi HTTP
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD",
                                    "OPTIONS"));
                            // Consente tutti gli header
                            corsConfiguration.setAllowedHeaders( List.of( "*" ) );
                            // Consente le credenziali
                            corsConfiguration.setAllowCredentials( true );
                            corsConfiguration.setExposedHeaders( Arrays.asList( "Authorization", "X-XSRF-TOKEN" ) );
                            // Imposta l'età massima del risultato preflight (in secondi) a 3600
                            corsConfiguration.setMaxAge( 3600L );
                            return corsConfiguration;
                        } )
                )
                // Configura CSRF
                .csrf( ( httpSecurityCsrfConfigurer ) -> httpSecurityCsrfConfigurer
                        // Imposta il gestore di attributi di richiesta CSRF
                        .csrfTokenRequestHandler( requestHandler )
                        // Ignora la protezione CSRF per i percorsi "/contact" e "/register"
                        .ignoringRequestMatchers( "/contact", "/register" )
                        // Utilizza un repository di token CSRF basato su cookie con l'opzione HttpOnly disabilitata
                        .csrfTokenRepository( CookieCsrfTokenRepository.withHttpOnlyFalse() )
                )
                // Aggiunge il filtro CSRF personalizzato dopo il filtro di autenticazione di base
                .addFilterAfter(
                        new CsrfCookieFilter(), BasicAuthenticationFilter.class
                )
                // Configura l'autorizzazione delle richieste
                .authorizeHttpRequests( ( requests ) -> requests

                        .requestMatchers("/api/v1/accounts").authenticated()

                        .requestMatchers( "/api/v1/teams" ).authenticated()

                        .requestMatchers( "/api/v1/card" ).authenticated()

                        .requestMatchers( "/api/v1/skillsStatistics" ).authenticated()
                )
                // Configura la gestione del login tramite form oauth2
                .oauth2ResourceServer( ( oauth2RS ) -> oauth2RS
                        .jwt( ( jwt ) -> jwt
                                .jwtAuthenticationConverter( jwtAuthenticationConverter )
                        )
                );



        // Costruisce e restituisce la catena di filtri di sicurezza
        return http.build();
    }

}
