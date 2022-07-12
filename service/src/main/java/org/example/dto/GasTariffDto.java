package org.example.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GasTariffDto {

    private Integer id;
    private BigDecimal tariff;
    private AdminDto admin;
}
