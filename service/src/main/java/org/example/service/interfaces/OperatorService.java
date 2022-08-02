package org.example.service.interfaces;

import org.example.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OperatorService {
    List<OperatorDto> getAllOperators();

    OperatorDto getOperatorById(Integer id);

    void updateOperator(OperatorDto dto);

    void deleteOperator(Integer id);

    void createOperator(OperatorDto oper, String adminLabel, Integer elTar, Integer gasTar, Integer heatTar);

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

    long getConsumersOfOperator(String operLabel);

    List<ElectricityTariffDto> getElTariffsCreateByAdmin(String adminLabel);

    List<GasTariffDto> getGasTariffsCreateByAdmin(String adminLabel);

    List<HeatTariffDto> getHeatTariffsCreateByAdmin(String adminLabel);

    List<OperatorDto> getAllAdminsOperators(Integer adminId);

    List<ConsumerDto> getConsumersWithOutTariff(String operLabel);

    List<ConsumerDto> getOperatorConsumersList(String operLabel);

    List<ElectricityTariffDto> getElTariffsWitchOperatorDontHave(Integer adminId, Integer operId);

    List<GasTariffDto> getGasTariffsWitchOperatorDontHave(Integer adminId, Integer operId);

    List<HeatTariffDto> getHeatTariffsWitchOperatorDontHave(Integer adminId, Integer operId);
}
