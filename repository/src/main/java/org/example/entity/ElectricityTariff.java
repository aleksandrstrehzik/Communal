package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ElectricityTariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "operators_ElectricityTariffs",
            joinColumns = @JoinColumn(name = "ElectricityTariff_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id"))
    private List<Operator> operators = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "electricity_tariff")
    private BigDecimal tariff;

    @Builder.Default
    @OneToMany(mappedBy = "electricityTariff", cascade = CascadeType.MERGE)
    private Set<Consumer> consumers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectricityTariff et = (ElectricityTariff) o;
        return Objects.equals(id, et.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
