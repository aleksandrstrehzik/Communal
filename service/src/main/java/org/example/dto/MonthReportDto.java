package org.example.dto;

import org.example.entity.enums.Months;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MonthReportDto {

    private Integer id;
    private Double electricityTariff;
    private Double gasTariff;
    private Double heatTariff;
    private Months month;
    private ConsumerDto consumer;
    private String operator;
    private Integer year;
    private Boolean IsPaid;
    private BigDecimal volumeOfConsumedGas;
    private BigDecimal amountOfHeatEnergyConsumed;
    private BigDecimal amountOfElectricityEnergyConsumed;
    private BigDecimal paymentForGas;
    private BigDecimal paymentHeatEnergy;
    private BigDecimal paymentElEnergy;
    private BigDecimal totalElConsumed;
    private BigDecimal totalGasConsumed;
    private BigDecimal totalHeatConsumed;
    private BigDecimal totalPayment;
}
