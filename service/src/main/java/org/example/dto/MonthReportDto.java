package org.example.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Month;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MonthReportDto {

    public static final String POSITIVE = "Введите неотрицательное значение";
    public static final String HEAT = "Введите количество потребленной теплоты";
    public static final String ELECTRICITY = "Введите количество потребленного электричества";
    public static final String GAS = "Введите количество потребленного газа";
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
    @NotNull(message = ELECTRICITY)
    @Positive(message = POSITIVE)
    private BigDecimal totalElConsumed;
    @Positive(message = POSITIVE)
    @NotNull(message = GAS)
    private BigDecimal totalGasConsumed;
    @Positive(message = POSITIVE)
    @NotNull(message = HEAT)
    private BigDecimal totalHeatConsumed;
    private BigDecimal totalPayment;
}
