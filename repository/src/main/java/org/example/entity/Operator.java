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
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "operators_gasTariffs",
            joinColumns = @JoinColumn(name = "operator_id"),
            inverseJoinColumns = @JoinColumn(name = "gasTariff_id"))
    private Set<GasTariff> gasTariffs = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "operators_ElectricityTariffs",
            joinColumns = @JoinColumn(name = "operator_id"),
            inverseJoinColumns = @JoinColumn(name = "ElectricityTariff_id"))
    private Set<ElectricityTariff> electricityTariffs = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "operators_HeatTariffs",
            joinColumns = @JoinColumn(name = "operator_id"),
            inverseJoinColumns = @JoinColumn(name = "HeatTariff_id"))
    private Set<HeatTariff> heatTariffs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Builder.Default
    @OneToMany(mappedBy = "operator", cascade = {CascadeType.REFRESH})
    private Set<Consumer> consumers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "operator", cascade = CascadeType.MERGE)
    private Set<Message> messages = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "operator", cascade = CascadeType.MERGE)
    private Set<MonthReport> monthReports = new HashSet<>();

    private String label;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator oper = (Operator) o;
        return Objects.equals(id, oper.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
