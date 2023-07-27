package com.developez.services;

import com.developez.model.Accounts;
import com.developez.repository.AccountsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        System.out.println( emailOfUserRequest );

        if( !emailOfUserRequest.equals( email ) ) {
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


    public ResponseEntity<?> getAccountDetails( @RequestParam String email ) {

        Map<String, Object> checkingObjectResult = checkWithClaim( getClaims(), email, "Nono sei autorizzato ad " +
                "accedere alle informazioni di questo account" );

        if( checkingObjectResult.get( "result" ).equals( "ko" ) ) {
            return ( ResponseEntity<?> ) checkingObjectResult.get( "responseEntity" );
        }

        Optional<Accounts> accounts = accountsRepository.findAccountsByAccountEmail( email );

        if( accounts.isEmpty() ) {
            Accounts newAccount = Accounts.builder()
                    .accountEmail( ( String ) getClaims().get( "email" ) )
                    .firstName( ( String ) getClaims().get( "given_name" ) )
                    .lastName( ( String ) getClaims().get( "family_name" ) )
                    .createDt( LocalDate.now() )
                    .build();

            saveAccountDetails( newAccount );

            return new ResponseEntity<>( newAccount, HttpStatus.OK );
        }


        return new ResponseEntity<>( accounts, HttpStatus.OK );
    }

    public ResponseEntity<?> saveAccountDetails( @RequestBody Accounts accounts ) {

        Accounts accountFound = accountsRepository.findAccountsByAccountEmail( accounts.getAccountEmail() ).orElse( null );

        if( accountFound != null ) {
            return new ResponseEntity<>( accountFound, HttpStatus.BAD_REQUEST );
        }

        Map<String, Object> checkingObjectResult = checkWithClaim( getClaims(), accounts.getAccountEmail(), "Nono sei" +
                " autorizzato a creare un account con questa email" );

        if( checkingObjectResult.get( "result" ).equals( "ko" ) ) {
            return ( ResponseEntity<?> ) checkingObjectResult.get( "responseEntity" );
        }

        accounts.setCreateDt( LocalDate.now() );

        return new ResponseEntity<>( accountsRepository.save( accounts ), HttpStatus.OK );
    }

    public ResponseEntity<?> modifyAccountDetails( @RequestBody Accounts accounts ) {
        Map<String, Object> checkingObjectResult = checkWithClaim( getClaims(), accounts.getAccountEmail(), "Nono sei" +
                " autorizzato a modificare un account con questa email" );

        if( checkingObjectResult.get( "result" ).equals( "ko" ) ) {
            return ( ResponseEntity<?> ) checkingObjectResult.get( "responseEntity" );
        }

        Accounts accountFounded = accountsRepository.findAccountsByAccountEmail( accounts.getAccountEmail() ).orElseThrow( () -> new IllegalStateException( "Account not found" ) );

        Accounts newAccounts = Accounts.builder()
                .accountEmail( accountFounded.getAccountEmail() )
                .firstName( accounts.getFirstName() == null ? accountFounded.getFirstName() : accounts.getFirstName() )
                .lastName( accounts.getLastName() == null ? accountFounded.getLastName() : accounts.getLastName() )
                .telephoneNumber( accounts.getTelephoneNumber() == null ? accountFounded.getTelephoneNumber() :
                        accounts.getTelephoneNumber() )
                .createDt( accountFounded.getCreateDt() )
                .build();

        return new ResponseEntity<>( accountsRepository.save( newAccounts ), HttpStatus.OK );
    }

    @Transactional
    public ResponseEntity<?> deleteAccountDetails( String email ) {
        Map<String, Object> checkingObjectResult = checkWithClaim( getClaims(), email, "Nono sei" +
                " autorizzato a eliminare un account con questa email" );

        if( checkingObjectResult.get( "result" ).equals( "ko" ) ) {
            return ( ResponseEntity<?> ) checkingObjectResult.get( "responseEntity" );
        }


        Optional<Accounts> accounts = accountsRepository.findAccountsByAccountEmail( email );

        if( accounts.isEmpty() ) {
            throw new IllegalStateException( "Account not found" );
        }

        accountsRepository.deleteAccountsByAccountEmail( email );

        return new ResponseEntity<>( accounts.get(), HttpStatus.OK );
    }
}
