package com.developez.repository;

import com.developez.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    // Metodo per trovare un oggetto "Accounts" in base all'ID del cliente.
    Optional<Accounts> findAccountsByAccountEmail( String email);

    // Metodo per eliminare un oggetto "Accounts" in base all'ID del cliente.
    void deleteAccountsByAccountEmail( String email );
}
