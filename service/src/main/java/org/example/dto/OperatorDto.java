package org.example.dto;

import lombok.*;
import org.example.entity.Admin;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatorDto {

    private Integer id;
    private String label;
    private AdminDto admin;
}
