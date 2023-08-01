package com.developez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class SkillsStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer velocity;
    private Integer shoot;
    private Integer pass;
    private Integer dribbling;
    private Integer defence;
    private Integer physical;

    @OneToOne(mappedBy = "skillsStatistics", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Card card;

}
