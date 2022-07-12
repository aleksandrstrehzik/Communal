package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @Builder.Default
    @OneToMany(mappedBy = "consumer", cascade = CascadeType.MERGE)
    private Set<MonthReport> monthReports = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "consumer", cascade = CascadeType.MERGE)
    private Set<Message> messages = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "electricityTariff_id")
    private ElectricityTariff electricityTariff;

    @ManyToOne
    @JoinColumn(name = "heatTariff_id")
    private HeatTariff heatTariff;

    @ManyToOne
    @JoinColumn(name = "gasTariff_id")
    private GasTariff gasTariff;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "apartment_square")
    private Double apartmentSquare;

    @Column(name = "number_of_residents")
    private Integer numberOfResidents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer con = (Consumer) o;
        return Objects.equals(id, con.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
