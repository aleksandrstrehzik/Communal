package org.example.dao;

import org.example.entity.ElectricityTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricityTariffRepository extends JpaRepository<ElectricityTariff, Integer> {

    @Query(value = "select * from electricity_tariff where admin_id = :adminId and id not in" +
            "(select electricity_tariff_id from operators_electricity_tariffs where operator_id = :operId)", nativeQuery = true)
    List<ElectricityTariff> getElectricityTariffWhichOperatorDontHave(Integer adminId, Integer operId);


}
