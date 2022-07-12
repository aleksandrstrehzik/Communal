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
public class GasTariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "operators_gasTariffs",
            joinColumns = @JoinColumn(name = "gasTariff_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id"))
    private List<Operator> operators = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "gas_tariff")
    private BigDecimal tariff;

    @Builder.Default
    @OneToMany(mappedBy = "gasTariff", cascade = CascadeType.MERGE)
    private Set<Consumer> consumers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GasTariff gt = (GasTariff) o;
        return Objects.equals(id, gt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
