package org.example.service;

import org.example.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OperatorService {
    List<OperatorDto> getAllOperators();

    OperatorDto getOperatorById(Integer id);

    void updateOperator(OperatorDto dto, Integer adminId);

    void deleteOperator(Integer id);

    void createOperator(String operLabel, String adminLabel);

    Page<OperatorDto> findAllPaginated(int pageNumber, String sortField, String sortDirection);

    OperatorDto getOperatorByLabel(String operLabel);

    List<ElectricityTariffDto> electricityTariffsOfOperator(String operatorLabel);

    List<HeatTariffDto> heatTariffsOfOperator(String operatorLabel);

    List<GasTariffDto> gasTariffsOfOperator(String operatorLabel);

    List<ElectricityTariffDto> getAllElTariffs();

    List<GasTariffDto> getAllGasTariffs();

    List<HeatTariffDto> getAllHeatTariffs();

    void addTariffToOperator(String value, Integer id, String oper);

    void deleteTariffFromOperator(Integer id, String value, String oper);

    List<AdminDto> getAllAdmin();
}
