package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricityTariffDto {

    private Integer id;
    @NotNull(message = "Введите новое значение")
    private BigDecimal tariff;
    private AdminDto admin;

}
