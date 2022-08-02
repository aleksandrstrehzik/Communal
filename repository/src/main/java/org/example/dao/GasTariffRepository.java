package org.example.dao;

import org.example.entity.ElectricityTariff;
import org.example.entity.GasTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasTariffRepository extends JpaRepository<GasTariff, Integer> {

    @Query(value = "select * from gas_tariff where admin_id = :adminId and id not in" +
            "(select gas_tariff_id from operators_gas_tariffs where operator_id = :operId)", nativeQuery = true)
    List<GasTariff> getGasTariffWhichOperatorDontHave(Integer adminId, Integer operId);
}
