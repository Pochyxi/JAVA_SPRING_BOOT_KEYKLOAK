package com.developez.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Schieramento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Teams team;

    @OneToMany(mappedBy = "schieramento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posizionamento> posizionamenti;
}
