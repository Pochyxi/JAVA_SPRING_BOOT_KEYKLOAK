package com.developez.requestModels.POST;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class POSTCardRequest {
    private Integer teamsId;
    private String name;
    private String surname;
    private String accountEmail;
}
