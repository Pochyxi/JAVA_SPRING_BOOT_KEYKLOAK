package com.developez.requestModels.POST;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class POSTTeamRequest {
    private String ownerEmail;
    private String teamName;
}
