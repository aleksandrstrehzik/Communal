package org.example.service;

import org.example.dto.ElectricityTariffDto;
import org.example.dto.GasTariffDto;
import org.example.dto.HeatTariffDto;
import org.example.entity.ElectricityTariff;
import org.example.entity.GasTariff;
import org.example.entity.HeatTariff;

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

    void updateGasTar(GasTariffDto gas);

    void updateHeatTar(HeatTariffDto heat);
}
