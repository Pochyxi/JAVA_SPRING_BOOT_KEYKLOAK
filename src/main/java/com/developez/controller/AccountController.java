package com.developez.controller;

import com.developez.model.Accounts;
import com.developez.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getAccountDetails(@RequestParam String email) {


        return new ResponseEntity<>(accountService.getAccountDetails( email ), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> saveAccountDetails( @RequestBody Accounts accounts ) {

        return new ResponseEntity<>( accountService.saveAccountDetails( accounts ), HttpStatus.OK );
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> updateAccountDetails( @RequestBody Accounts accounts ) {

        return new ResponseEntity<>( accountService.modifyAccountDetails( accounts ), HttpStatus.OK );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccountDetails( @RequestParam String email ) {

        return new ResponseEntity<>( accountService.deleteAccountDetails( email ), HttpStatus.OK );
    }
}
