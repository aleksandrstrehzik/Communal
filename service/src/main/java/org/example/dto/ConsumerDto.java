package org.example.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerDto {

    private Integer id;
    private OperatorDto operator;
    private ElectricityTariffDto electricityTariff;
    private HeatTariffDto heatTariff;
    private GasTariffDto gasTariff;
    private String name;
    private String surname;
    private Double apartmentSquare;
    private Integer numberOfResidents;
    private BigDecimal sw;
}
