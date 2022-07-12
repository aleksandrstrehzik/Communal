package org.example.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatTariffDto {

    private BigDecimal tariff;
    private Integer id;
    private AdminDto admin;
}
