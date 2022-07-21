package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.*;
import org.example.dto.*;
import org.example.entity.*;
import org.example.mapper.*;
import org.example.service.OperatorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;
    private final AdminRepository adminRepository;
    private final ConsumerRepository consumerRepository;
    private final ConsumerMapper consumerMapper;
    private final AdminMapper adminMapper;
    private final OperatorMapper operatorMapper;
    private final ElectricityTariffMapper electricityTariffMapper;
    private final GasTariffMapper gasTariffMapper;
    private final HeatTariffMapper heatTariffMapper;
    private final ElectricityTariffRepository elRepository;
    private final HeatTariffRepository heatRepository;
    private final GasTariffRepository gasRepository;

    @Override
    public List<OperatorDto> getAllOperators() {
        return operatorRepository.findAll().stream()
                .map(operatorMapper::toDto)
                .collect(Collectors.toList());
    }

    public OperatorDto getOperatorById(Integer id) {
        return operatorMapper.toDto(operatorRepository.getReferenceById(id));
    }

    public void updateOperator(OperatorDto dto, Integer adminId) {
        Admin admin = adminRepository.getReferenceById(adminId);
        Operator operator = operatorMapper.toEntity(dto);
        operator.setAdmin(admin);
        operatorRepository.save(operator);
    }

    public void deleteOperator(Integer id) {
        Operator o = operatorRepository.getReferenceById(id);
        o.getConsumers()
                .forEach(consumer -> consumer.setOperator(null));
        operatorRepository.deleteById(id);
    }

    public void createOperator(String operLabel, String adminLabel) {
        Admin adminByLabel = adminRepository.findAdminByLabel(adminLabel);
        Operator build = Operator.builder()
                .label(operLabel)
                .admin(adminByLabel)
                .build();
        operatorRepository.save(build);
    }

    public Page<OperatorDto> findAllPaginated(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable paged = PageRequest.of(pageNumber - 1, 5, sort);
        return operatorRepository.findAll(paged).map(operatorMapper::toDto);
    }

    public OperatorDto getOperatorByLabel(String operLabel) {
        return operatorMapper.toDto(operatorRepository.findOperatorByLabel(operLabel));
    }

    public List<ElectricityTariffDto> electricityTariffsOfOperator(String operatorLabel) {
        Operator operator = operatorRepository.findOperatorByLabel(operatorLabel);
        return operator.getElectricityTariffs().stream()
                .map(electricityTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GasTariffDto> gasTariffsOfOperator(String operatorLabel) {
        Operator operator = operatorRepository.findOperatorByLabel(operatorLabel);
        return operator.getGasTariffs().stream()
                .map(gasTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<HeatTariffDto> heatTariffsOfOperator(String operatorLabel) {
        Operator operator = operatorRepository.findOperatorByLabel(operatorLabel);
        return operator.getHeatTariffs().stream()
                .map(heatTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ElectricityTariffDto> getAllElTariffs() {
        return elRepository.findAll().stream()
                .map(electricityTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<HeatTariffDto> getAllHeatTariffs() {
        return heatRepository.findAll().stream()
                .map(heatTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GasTariffDto> getAllGasTariffs() {
        return gasRepository.findAll().stream()
                .map(gasTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addTariffToOperator(String value, Integer id, String oper) {
        Operator opert = operatorRepository.findOperatorByLabel(oper);
        switch (value) {
            case "el":
                ElectricityTariff el = elRepository.getReferenceById(id);
                opert.getElectricityTariffs().add(el);
                operatorRepository.save(opert);
                break;
            case "gas":
                GasTariff gas = gasRepository.getReferenceById(id);
                opert.getGasTariffs().add(gas);
                operatorRepository.save(opert);
                break;
            case "heat":
                HeatTariff heat = heatRepository.getReferenceById(id);
                opert.getHeatTariffs().add(heat);
                operatorRepository.save(opert);
                break;
        }
    }

    public void deleteTariffFromOperator(Integer id, String value, String oper) {
        Operator opert = operatorRepository.findOperatorByLabel(oper);
        switch (value) {
            case "el":
                ElectricityTariff el = elRepository.getReferenceById(id);
                opert.getElectricityTariffs().remove(el);
                operatorRepository.save(opert);
                break;
            case "gas":
                GasTariff gas = gasRepository.getReferenceById(id);
                opert.getGasTariffs().remove(gas);
                operatorRepository.save(opert);
                break;
            case "heat":
                HeatTariff heat = heatRepository.getReferenceById(id);
                opert.getHeatTariffs().remove(heat);
                operatorRepository.save(opert);
                break;
        }
    }

    public List<AdminDto> getAllAdmin() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ElectricityTariffDto> getElTariffsCreateByAdmin(String adminLabel) {
        return adminRepository.findAdminByLabel(adminLabel).getElectricityTariffs().stream()
                .map(electricityTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GasTariffDto> getGasTariffsCreateByAdmin(String adminLabel) {
        return adminRepository.findAdminByLabel(adminLabel).getGasTariffs().stream()
                .map(gasTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<HeatTariffDto> getHeatTariffsCreateByAdmin(String adminLabel) {
        return adminRepository.findAdminByLabel(adminLabel).getHeatTariffs().stream()
                .map(heatTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public long getConsumersOfOperator(String operLabel) {
        Integer id = operatorRepository.findOperatorByLabel(operLabel).getId();
        return consumerRepository.findAllByOperator_Id(id).stream()
                .map(consumerMapper::toDto)
                .count();
    }

    public List<OperatorDto> getAllAdminsOperators(Integer adminId) {
        return adminRepository.getReferenceById(adminId).getOperators().stream()
                .map(operatorMapper::toDto)
                .collect(Collectors.toList());
    }
}











