package org.example.dao;

import org.example.entity.GasTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasTariffRepository extends JpaRepository<GasTariff, Integer> {
}
