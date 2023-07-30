package com.developez.requestModels;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NewTeamRequest {
    private String ownerEmail;
    private String teamName;
}
