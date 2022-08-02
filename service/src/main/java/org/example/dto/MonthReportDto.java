package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Month;

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
    private Month month;
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
    @NotNull(message = "Введите количество потребленного электричества")
    private BigDecimal totalElConsumed;
    @NotNull(message = "Введите количество потребленного газа")
    private BigDecimal totalGasConsumed;
    @NotNull(message = "Введите количество потребленной теплоты")
    private BigDecimal totalHeatConsumed;
    private BigDecimal totalPayment;
}
