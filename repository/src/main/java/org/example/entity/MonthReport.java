package org.example.entity;

import org.example.entity.enums.Months;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MonthReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal electricityTariff;

    private BigDecimal heatTariff;

    private BigDecimal gasTariff;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Months month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    private String operator;

    @Column
    private Integer year;

    @Column(name = "paid")
    private Boolean IsPaid;

    @Column(name = "volume_of_consumed_gas")
    private BigDecimal volumeOfConsumedGas;

    @Column(name = "amount_of_heat_energy_consumed")
    private BigDecimal amountOfHeatEnergyConsumed;

    @Column(name = "amount_of_electricity_energy_consumed")
    private BigDecimal amountOfElectricityEnergyConsumed;

    @Column(name = "pyament_for_gas")
    private BigDecimal paymentForGas;

    @Column(name = "pyament_heat_energy")
    private BigDecimal paymentHeatEnergy;

    @Column(name = "pyament_for_electricity_energy")
    private BigDecimal paymentElEnergy;

    private BigDecimal totalPayment;

    private BigDecimal totalElConsumed;

    private BigDecimal totalGasConsumed;

    private BigDecimal totalHeatConsumed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthReport mr = (MonthReport) o;
        return Objects.equals(id, mr.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
