package org.example.dto;

import lombok.*;
import org.example.entity.Admin;
import org.example.entity.Consumer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "admin")
public class OperatorDto {

    private Integer id;

    @NotBlank
    @Size(min = 3, max = 20, message = "Длина должна быть в пределах от 3 до 20 символов")
    private String label;
    private AdminDto admin;

}
