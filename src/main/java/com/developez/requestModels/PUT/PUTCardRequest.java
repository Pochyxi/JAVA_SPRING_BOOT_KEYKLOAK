package com.developez.requestModels.PUT;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class PUTCardRequest {
    private Integer cardId;
    private String name;
    private String surname;
    private String accountEmail;
    private Integer skillStatisticsId;
}
