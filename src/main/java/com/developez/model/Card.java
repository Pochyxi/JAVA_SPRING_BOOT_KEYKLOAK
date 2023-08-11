package com.developez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
    private Teams teams;

    private String name;

    private String surname;

    private String accountEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skills_statistics_id")
    @JsonManagedReference
    private SkillsStatistics skillsStatistics;

}
