package org.example.dao;

import org.example.entity.ElectricityTariff;
import org.example.entity.MonthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ElectricityTariffRepository extends JpaRepository<ElectricityTariff, Integer> {

/*    @Query(value = "select * from electricity_tariff where admin_id = :adminId and id !=" +
            "(select electricity_tariff_id from operators_electricity_tariffs where operator_id = :operId)", nativeQuery = false)
    ElectricityTariff getElectricityTariff(Integer adminId, Integer operId);*/


}
