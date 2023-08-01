package com.developez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Accounts {

    @Id
    @Column(name="account_email", nullable = false)
    private String accountEmail;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "create_dt", nullable = false)
    private LocalDate createDt;


    @OneToMany(mappedBy = "accountsOwner", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonBackReference
    private List<Teams> teams;

}
