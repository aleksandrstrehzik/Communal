package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.MERGE)
    private Set<GasTariff> gasTariffs = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.MERGE)
    private Set<ElectricityTariff> electricityTariffs = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.MERGE)
    private Set<HeatTariff> heatTariffs = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.MERGE)
    private Set<Operator> operators = new HashSet<>();

    private String label;

    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin ad = (Admin) o;
        return Objects.equals(id, ad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
