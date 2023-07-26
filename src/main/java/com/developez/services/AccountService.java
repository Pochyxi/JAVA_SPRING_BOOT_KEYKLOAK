package com.developez.services;

import com.developez.model.Accounts;
import com.developez.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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

    public ResponseEntity<?> getAccountDetails( @RequestParam String email ) {

        JwtAuthenticationToken authentication = ( JwtAuthenticationToken ) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> claims = authentication.getToken().getClaims();

        System.out.println( claims.get( "email" ) );
        System.out.println( claims.toString() );

        Optional<Accounts> accounts = accountsRepository.findAccountsByAccountEmail( email );

        if( accounts.isEmpty() ) {
            Accounts newAccount = Accounts.builder()
                    .accountEmail( ( String ) claims.get( "email" ) )
                    .firstName( ( String ) claims.get( "given_name" ) )
                    .lastName( ( String ) claims.get( "family_name" ) )
                    .createDt( LocalDate.now() )
                    .build();

            saveAccountDetails( newAccount );

            return new ResponseEntity<>( newAccount, HttpStatus.OK );
        }


        return new ResponseEntity<>( accounts, HttpStatus.OK );
    }

    public Accounts saveAccountDetails( @RequestBody Accounts accounts ) {

        accounts.setCreateDt( LocalDate.now() );

        return accountsRepository.save( accounts );
    }

    public Accounts modifyAccountDetails( @RequestBody Accounts accounts ) {

        Accounts accountFounded = accountsRepository.findAccountsByAccountEmail( accounts.getAccountEmail() ).orElseThrow( () -> new IllegalStateException( "Account not found" ) );

        Accounts newAccounts = Accounts.builder()
                .accountEmail( accountFounded.getAccountEmail() )
                .firstName( accounts.getFirstName() == null ? accountFounded.getFirstName() : accounts.getFirstName() )
                .lastName( accounts.getLastName() == null ? accountFounded.getLastName() : accounts.getLastName() )
                .telephoneNumber( accounts.getTelephoneNumber() == null ? accountFounded.getTelephoneNumber() :
                        accounts.getTelephoneNumber() )
                .createDt( accountFounded.getCreateDt() )
                .build();

        return accountsRepository.save( newAccounts );
    }
}
