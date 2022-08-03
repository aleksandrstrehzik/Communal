package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userName;
    private ConsumerDto consumer;
    private OperatorDto operator;
    private AdminDto admin;
}
