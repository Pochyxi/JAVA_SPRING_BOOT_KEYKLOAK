package com.developez.controller;

import com.developez.model.Accounts;
import com.developez.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController( AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/myAccount")
    public ResponseEntity<?> getAccountDetails(@RequestParam String email) {


        return new ResponseEntity<>(accountService.getAccountDetails( email ), HttpStatus.OK);
    }

    @PostMapping ("/newAccount")
    public ResponseEntity<?> saveAccountDetails( @RequestBody Accounts accounts ) {

        return new ResponseEntity<>( accountService.saveAccountDetails( accounts ), HttpStatus.OK );
    }

    @PutMapping ("/updateAccount")
    public ResponseEntity<?> updateAccountDetails( @RequestBody Accounts accounts ) {

        return new ResponseEntity<>( accountService.modifyAccountDetails( accounts ), HttpStatus.OK );
    }
}
