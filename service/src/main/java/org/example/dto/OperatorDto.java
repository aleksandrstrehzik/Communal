package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
