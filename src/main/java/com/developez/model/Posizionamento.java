package com.developez.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Posizionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Side side;

    private Role role;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "schieramento_id")
    private Schieramento schieramento;
}
