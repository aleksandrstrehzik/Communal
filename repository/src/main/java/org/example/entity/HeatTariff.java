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
public class HeatTariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "operators_HeatTariffs",
            joinColumns = @JoinColumn(name = "HeatTariff_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id"))
    private List<Operator> operators = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "heat_tariff")
    private BigDecimal tariff;

    @Builder.Default
    @OneToMany(mappedBy = "heatTariff", cascade = CascadeType.MERGE)
    private Set<Consumer> consumers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeatTariff ht = (HeatTariff) o;
        return Objects.equals(id, ht.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
