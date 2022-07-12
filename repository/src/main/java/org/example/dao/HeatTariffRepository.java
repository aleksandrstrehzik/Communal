package org.example.dao;

import org.example.entity.HeatTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatTariffRepository extends JpaRepository<HeatTariff, Integer> {


}
