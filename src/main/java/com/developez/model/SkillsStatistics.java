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
}
