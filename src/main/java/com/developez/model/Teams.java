package com.developez.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accounts_owner_email", nullable = false)
    private Accounts accountsOwner;

    @Column(name="team_name", nullable = false)
    private String teamName;

    @OneToMany(mappedBy = "teams", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonBackReference
    private List<Card> cards;
}
