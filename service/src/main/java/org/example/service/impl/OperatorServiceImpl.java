package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.*;
import org.example.dto.*;
import org.example.entity.*;
import org.example.mapper.*;
import org.example.service.interfaces.OperatorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.service.impl.MockUtils.*;

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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<OperatorDto> getAllOperators() {
        return operatorRepository.findAll().stream()
                .map(operatorMapper::toDto)
                .collect(Collectors.toList());
    }

    public OperatorDto getOperatorById(Integer id) {
        return operatorMapper.toDto(operatorRepository.getReferenceById(id));
    }

    @Transactional
    public void updateOperator(OperatorDto dto) {
        String future = dto.getLabel();
        if (operatorRepository.findOperatorByLabel(future) == null) {
            String old = operatorRepository.getReferenceById(dto.getId()).getLabel();
            User byUserName = userRepository.findByUserName(old);
            byUserName.setUserName(future);
            userRepository.save(byUserName);
            Operator operator = operatorRepository.getReferenceById(dto.getId());
            operator.setLabel(future);
            operatorRepository.save(operator);
        }
    }

    @Transactional
    public void deleteOperator(Integer id) {
        Operator o = operatorRepository.getReferenceById(id);
        User byUserName = userRepository.findByUserName(o.getLabel());
        userRepository.deleteUserRole(byUserName.getId());
        byUserName.setOperator(null);
        o.setUser(null);
        userRepository.deleteUserByUserName(o.getLabel());
        o.getConsumers()
                .forEach(consumer -> consumer.setOperator(null));
        operatorRepository.deleteById(id);
    }

    @Transactional
    public void createOperator(OperatorDto oper, String adminLabel, Integer elTar, Integer gasTar, Integer heatTar) {
        if (operatorRepository.findOperatorByLabel(oper.getLabel()) == null) {
            Admin adminByLabel = adminRepository.findAdminByLabel(adminLabel);
            Operator build = operatorMapper.toEntity(oper);
            build.setAdmin(adminByLabel);
            build.getElectricityTariffs().add(elRepository.getReferenceById(elTar));
            build.getGasTariffs().add(gasRepository.getReferenceById(gasTar));
            build.getHeatTariffs().add(heatRepository.getReferenceById(heatTar));
            operatorRepository.save(build);
            User user = User.builder()
                    .operator(build)
                    .password(bCryptPasswordEncoder.encode(build.getLabel()))
                    .userName(build.getLabel())
                    .build();
            Role role = roleRepository.findByName(OPERATOR);
            user.getRoles().add(role);
            userRepository.save(user);
        }
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

    @Transactional
    public void addTariffToOperator(String value, Integer id, String oper) {
        Operator opert = operatorRepository.findOperatorByLabel(oper);
        switch (value) {
            case EL:
                ElectricityTariff el = elRepository.getReferenceById(id);
                opert.getElectricityTariffs().add(el);
                operatorRepository.save(opert);
                break;
            case GAS:
                GasTariff gas = gasRepository.getReferenceById(id);
                opert.getGasTariffs().add(gas);
                operatorRepository.save(opert);
                break;
            case HEAT:
                HeatTariff heat = heatRepository.getReferenceById(id);
                opert.getHeatTariffs().add(heat);
                operatorRepository.save(opert);
                break;
        }
    }

    @Transactional
    public void deleteTariffFromOperator(Integer id, String value, String oper) {
        Operator opert = operatorRepository.findOperatorByLabel(oper);
        switch (value) {
            case EL:
                ElectricityTariff el = elRepository.getReferenceById(id);
                opert.getElectricityTariffs().remove(el);
                operatorRepository.save(opert);
                break;
            case GAS:
                GasTariff gas = gasRepository.getReferenceById(id);
                opert.getGasTariffs().remove(gas);
                operatorRepository.save(opert);
                break;
            case HEAT:
                HeatTariff heat = heatRepository.getReferenceById(id);
                opert.getHeatTariffs().remove(heat);
                operatorRepository.save(opert);
                break;
        }
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

    public List<ConsumerDto> getConsumersWithOutTariff(String operLabel) {
        Operator oper = operatorRepository.findOperatorByLabel(operLabel);
        return oper.getConsumers().stream()
                .filter(consumer -> consumer.getElectricityTariff() == null)
                .filter(consumer -> consumer.getGasTariff() == null)
                .filter(consumer -> consumer.getHeatTariff() == null)
                .map(consumerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ConsumerDto> getOperatorConsumersList(String operLabel) {
        return operatorRepository.findOperatorByLabel(operLabel).getConsumers().stream()
                .map(consumerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ElectricityTariffDto> getElTariffsWitchOperatorDontHave(Integer adminId, Integer operId) {
        return elRepository.getElectricityTariffWhichOperatorDontHave(adminId, operId).stream()
                .map(electricityTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GasTariffDto> getGasTariffsWitchOperatorDontHave(Integer adminId, Integer operId) {
        return gasRepository.getGasTariffWhichOperatorDontHave(adminId, operId).stream()
                .map(gasTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<HeatTariffDto> getHeatTariffsWitchOperatorDontHave(Integer adminId, Integer operId) {
        return heatRepository.getHeatTariffWhichOperatorDontHave(adminId, operId).stream()
                .map(heatTariffMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ConsumerDto> getConsumersWithOutOperator() {
        return consumerRepository.findConsumersByOperatorIsNull().stream()
                .map(consumerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addConsumerToOperator(String operLabel, Integer consumerId) {
        Consumer consumer = consumerRepository.getReferenceById(consumerId);
        consumer.setOperator(operatorRepository.findOperatorByLabel(operLabel));
        consumerRepository.save(consumer);
    }
}











