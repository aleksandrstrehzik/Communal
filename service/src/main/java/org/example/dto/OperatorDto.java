package org.example.dto;

import lombok.*;
import org.example.entity.Admin;
import org.example.entity.Consumer;

import java.util.HashSet;
import java.util.Set;

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
