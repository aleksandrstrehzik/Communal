package org.example.dao;

import org.example.entity.ElectricityTariff;
import org.example.entity.HeatTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeatTariffRepository extends JpaRepository<HeatTariff, Integer> {

    @Query(value = "select * from heat_tariff where admin_id = :adminId and id not in" +
            "(select heat_tariff_id from operators_heat_tariffs where operator_id = :operId)", nativeQuery = true)
    List<HeatTariff> getHeatTariffWhichOperatorDontHave(Integer adminId, Integer operId);
}
