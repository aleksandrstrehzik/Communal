package org.example.service.interfaces;

import org.example.dto.ElectricityTariffDto;
import org.example.dto.GasTariffDto;
import org.example.dto.HeatTariffDto;

import java.math.BigDecimal;
import java.util.List;

public interface TariffsService {

    List<ElectricityTariffDto> getAllElTar();

    List<GasTariffDto> getAllGasTar();

    List<HeatTariffDto> getAllHeatTar();

    void deleteElTar(Integer id);

    void deleteGasTar(Integer id);

    void deleteHeatTar(Integer id);

    ElectricityTariffDto getElTar(Integer id);

    GasTariffDto getGasTar(Integer id);

    HeatTariffDto getHeatTar(Integer id);

    void updateElTar(ElectricityTariffDto el, Integer adminId);

    void updateGasTar(GasTariffDto gas, Integer adminId);

    void updateHeatTar(HeatTariffDto heat, Integer adminId);

    void createTariff(Integer adminId, String value, BigDecimal tariff);
}
