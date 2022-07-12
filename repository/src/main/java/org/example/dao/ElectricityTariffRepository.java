package org.example.dao;

import org.example.entity.ElectricityTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface ElectricityTariffRepository extends JpaRepository<ElectricityTariff, Integer> {
}
