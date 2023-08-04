package com.developez.requestModels.PUT;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class PUTSkillsStatistic {

    private Integer id;
    private String controlEmail;
    private Integer velocity;
    private Integer shoot;
    private Integer pass;
    private Integer dribbling;
    private Integer defence;
    private Integer physical;

}
