package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatTariffDto {

    public static final String NOT_NULL = "Введите новое значение";
    private Integer id;
    @NotNull(message = NOT_NULL)
    private BigDecimal tariff;
    private AdminDto admin;
}
