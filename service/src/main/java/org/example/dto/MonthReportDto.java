package org.example.dto;

import org.example.entity.enums.Months;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthReportDto {

    private Integer id;
    private ElectricityTariffDto electricityTariff;
    private HeatTariffDto heatTariff;
    private GasTariffDto gasTariff;
    private Months month;
    private ConsumerDto consumer;
    private OperatorDto operator;
    private Integer year;
    private Boolean IsPaid;
    private BigDecimal volumeOfConsumedGas;
    private BigDecimal amountOfHeatEnergyConsumed;
    private BigDecimal amountOfElectricityEnergyConsumed;
    private BigDecimal paymentForGas;
    private BigDecimal paymentHeatEnergy;
    private BigDecimal paymentElEnergy;
}
