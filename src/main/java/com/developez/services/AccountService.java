package com.developez.services;

import com.developez.model.Accounts;
import com.developez.repository.AccountsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Service
public class AccountService {

    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountService( AccountsRepository accountsRepository ) {
        this.accountsRepository = accountsRepository;
    }


    private Map<String, Object> getClaims() {
        JwtAuthenticationToken authentication = ( JwtAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();

        return authentication.getToken().getClaims();
    }

    private Map<String, Object> checkWithClaim( Map<String, Object> claims, String email, String errorMessage ) {
        String emailOfUserRequest = getClaims().get( "email" ).toString();
        ResponseEntity<?> responseEntity = null;
        Map<String, Object> resultObject = new HashMap<>();
        Collection<SimpleGrantedAuthority> authorities = ( Collection<SimpleGrantedAuthority> )
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if( !emailOfUserRequest.equals( email ) && authorities.stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ) {
            responseEntity = new ResponseEntity<>(
                    errorMessage,
                    HttpStatus.FORBIDDEN );

            resultObject.put( "responseEntity", responseEntity );
            resultObject.put( "result", "ko" );
        } else {
            resultObject.put( "responseEntity", responseEntity );
            resultObject.put( "result", "ok" );
        }

        return resultObject;
    }



    // GET
    public Accounts GET_Accounts( @RequestParam String email ) {

        // se l'account esiste, lo ritorna, invece se non esiste ritorna null e lo gestisco nel controller
      return accountsRepository.findAccountsByAccountEmail( email ).orElse( null );
    }

    // POST
    public Accounts POST_Accounts( @RequestBody Accounts accounts ) {

        // verifico se l'account che si vuole creare risulta esistente
        Accounts accountFound = accountsRepository.findAccountsByAccountEmail( accounts.getAccountEmail() ).orElse( null );

        // se non è esistente, lo aggiungo
        if( accountFound == null ) {
            accounts.setCreateDt( LocalDate.now() );
            return accountsRepository.save( accounts );
        } else {
            // se è presente un account con questa email, ritorno null e lo gestisco nel controller
            return null;
        }
    }

    public Accounts PUT_Accounts( @RequestBody Accounts accounts ) {

        Accounts accountFounded =
                accountsRepository.findAccountsByAccountEmail( accounts.getAccountEmail() ).orElse( null );

        if( accountFounded == null ) {
            return null;
        } else {
            Accounts modifiedAccounts = Accounts.builder()
                    .accountEmail( accountFounded.getAccountEmail() )
                    .firstName( accounts.getFirstName() == null ? accountFounded.getFirstName() : accounts.getFirstName() )
                    .lastName( accounts.getLastName() == null ? accountFounded.getLastName() : accounts.getLastName() )
                    .telephoneNumber( accounts.getTelephoneNumber() == null ? accountFounded.getTelephoneNumber() :
                            accounts.getTelephoneNumber() )
                    .createDt( accountFounded.getCreateDt() )
                    .build();

            return accountsRepository.save( modifiedAccounts );
        }
    }

    @Transactional
    public Accounts DELETE_Accounts( String email ) {
        Accounts accounts = accountsRepository.findAccountsByAccountEmail( email ).orElse( null);

        if( accounts == null ) {
            return null;
        } else {
            accountsRepository.deleteAccountsByAccountEmail( email );
            return accounts;
        }
    }
}
