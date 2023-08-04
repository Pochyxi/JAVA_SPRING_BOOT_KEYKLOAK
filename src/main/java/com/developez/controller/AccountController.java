package com.developez.controller;

import com.developez.model.Accounts;
import com.developez.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController( AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @PreAuthorize("#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> getAccountDetails(@RequestParam String email) {
        Accounts accounts = accountService.GET_Accounts( email );

        if( accounts != null ) {
            return new ResponseEntity<>( accounts, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Nessun account trovato con questa email", HttpStatus.OK );
        }
    }

    @PostMapping
    @PreAuthorize("#accounts.accountEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> saveAccountDetails( @RequestBody Accounts accounts ) {

        Accounts accountsSaved = accountService.POST_Accounts( accounts );

        if( accountsSaved != null ) {
            return new ResponseEntity<>( accountsSaved, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( "Account già esistente per questa email", HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping
    @PreAuthorize("#accounts.accountEmail == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAccountDetails( @RequestBody Accounts accounts ) {

        Accounts accountsModified = accountService.PUT_Accounts( accounts );

        if( accountsModified == null ) {
            return new ResponseEntity<>( "Nessun account trovato con questa email", HttpStatus.NOT_FOUND );
        } else {
            return new ResponseEntity<>( accountsModified, HttpStatus.OK );
        }
    }

    @DeleteMapping
    @PreAuthorize("#email == authentication.principal.claims.get('preferred_username') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccountDetails( @RequestParam String email ) {

        Accounts accountDeleted = accountService.DELETE_Accounts( email );

        if( accountDeleted == null ) {
            return new ResponseEntity<>( "Nessun account trovato con questa email", HttpStatus.NOT_FOUND );
        } else {
            return new ResponseEntity<>( accountDeleted, HttpStatus.OK );
        }
    }
}
