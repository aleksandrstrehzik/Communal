package org.example.dao;

import org.example.entity.ElectricityTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ElectricityTariffRepository extends JpaRepository<ElectricityTariff, Integer> {

}
