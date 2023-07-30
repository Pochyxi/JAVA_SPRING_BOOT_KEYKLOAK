package com.developez.requestModels;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NewCardRequest {
    private Integer teamsId;
    private String name;
    private String surname;
    private String accountEmail;
}
