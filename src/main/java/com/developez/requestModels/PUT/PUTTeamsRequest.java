package com.developez.requestModels.PUT;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class PUTTeamsRequest {
    private String ownerEmail;
    private Integer teamsId;
    private String teamName;
}
