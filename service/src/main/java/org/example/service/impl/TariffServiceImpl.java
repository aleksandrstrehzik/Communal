package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.AdminRepository;
import org.example.dao.ElectricityTariffRepository;
import org.example.dao.GasTariffRepository;
import org.example.dao.HeatTariffRepository;
import org.example.dto.ElectricityTariffDto;
import org.example.dto.GasTariffDto;
import org.example.dto.HeatTariffDto;
import org.example.entity.Admin;
import org.example.entity.ElectricityTariff;
import org.example.entity.GasTariff;
import org.example.entity.HeatTariff;
import org.example.mapper.ElectricityTariffMapper;
import org.example.mapper.GasTariffMapper;
import org.example.mapper.HeatTariffMapper;
import org.example.service.TariffsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffsService {

    private final ElectricityTariffMapper electricityTariffMapper;
    private final AdminRepository adminRepository;
    private final GasTariffMapper gasTariffMapper;
    private final HeatTariffMapper heatTariffMapper;
    private final ElectricityTariffRepository elRepository;
    private final HeatTariffRepository heatRepository;
    private final GasTariffRepository gasRepository;

    public List<ElectricityTariffDto> getAllElTar() {
        return elRepository.findAll().stream()
                .map(electricityTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GasTariffDto> getAllGasTar() {
        return gasRepository.findAll().stream()
                .map(gasTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<HeatTariffDto> getAllHeatTar() {
        return heatRepository.findAll().stream()
                .map(heatTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteElTar(Integer id) {
        ElectricityTariff el = elRepository.getReferenceById(id);
        el.getConsumers()
                .forEach(consumer -> consumer.setElectricityTariff(null));
        elRepository.deleteById(id);
    }

    public void deleteGasTar(Integer id) {
        GasTariff gas = gasRepository.getReferenceById(id);
        gas.getConsumers().forEach(consumer -> consumer.setGasTariff(null));
        gasRepository.deleteById(id);
    }

    public void deleteHeatTar(Integer id) {
        HeatTariff heat = heatRepository.getReferenceById(id);
        heat.getConsumers().forEach(consumer -> consumer.setHeatTariff(null));
        heatRepository.deleteById(id);
    }

    public ElectricityTariffDto getElTar(Integer id) {
        return electricityTariffMapper.toDto(elRepository.getReferenceById(id));
    }

    public GasTariffDto getGasTar(Integer id) {
        return gasTariffMapper.toDto(gasRepository.getReferenceById(id));
    }

    public HeatTariffDto getHeatTar(Integer id) {
        return heatTariffMapper.toDto(heatRepository.getReferenceById(id));
    }

    public void updateElTar(ElectricityTariffDto el, Integer adminId) {
        Admin admin = adminRepository.getReferenceById(adminId);
        ElectricityTariff electricityTariff = electricityTariffMapper.toEntity(el);
        electricityTariff.setAdmin(admin);
        elRepository.save(electricityTariff);
    }

    public void updateGasTar(GasTariffDto gas, Integer adminId) {
        Admin admin = adminRepository.getReferenceById(adminId);
        GasTariff gasTariff = gasTariffMapper.toEntity(gas);
        gasTariff.setAdmin(admin);
        gasRepository.save(gasTariff);
    }

    public void updateHeatTar(HeatTariffDto heat, Integer adminId) {
        Admin admin = adminRepository.getReferenceById(adminId);
        HeatTariff heatTariff = heatTariffMapper.toEntity(heat);
        heatTariff.setAdmin(admin);
        heatRepository.save(heatTariff);
    }

    public void createTariff(Integer adminId, String value, BigDecimal tariff) {
        Admin admin = adminRepository.getReferenceById(adminId);
        switch (value){
            case "el":
                ElectricityTariff el = ElectricityTariff.builder()
                        .admin(admin)
                        .tariff(tariff)
                        .build();
                elRepository.save(el);
                break;
            case "gas":
                GasTariff gas = GasTariff.builder()
                        .admin(admin)
                        .tariff(tariff)
                        .build();
                gasRepository.save(gas);
                break;
            case "heat":
                HeatTariff heat = HeatTariff.builder()
                        .admin(admin)
                        .tariff(tariff)
                        .build();
                heatRepository.save(heat);
                break;
        }
    }
}
