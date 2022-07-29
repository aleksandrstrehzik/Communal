package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.*;
import org.example.dto.ConsumerDto;
import org.example.entity.*;
import org.example.mapper.ConsumerMapper;
import org.example.service.interfaces.ConsumerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.service.impl.MockUtils.CONSUMER;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final ConsumerMapper consumerMapper;
    private final OperatorRepository operatorRepository;
    private final ElectricityTariffRepository elRepository;
    private final HeatTariffRepository heatRepository;
    private final GasTariffRepository gasRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public Page<ConsumerDto> findAllPaginated(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable paged = PageRequest.of(pageNumber - 1, 5, sort);
        return consumerRepository.findAll(paged).map(consumerMapper::toDto);
    }

    public ConsumerDto getConsumerById(Integer id) {
        return consumerMapper.toDto(consumerRepository.getReferenceById(id));
    }

    @Transactional
    public void deleteConsumer(Integer id) {
        Consumer o = consumerRepository.getReferenceById(id);
        o.getMonthReports()
                .forEach(report -> report.setConsumer(null));
        consumerRepository.deleteById(id);
    }

    @Transactional
    public void createConsumer(ConsumerDto cons, Integer operId, Integer elId,
                               Integer gasId, Integer heatId) {
        Consumer consumer = consumerMapper.toEntity(cons);
        Operator oper = operatorRepository.getReferenceById(operId);
        ElectricityTariff el = elRepository.getReferenceById(elId);
        GasTariff gas = gasRepository.getReferenceById(gasId);
        HeatTariff heat = heatRepository.getReferenceById(heatId);
        consumer.setOperator(oper);
        consumer.setElectricityTariff(el);
        consumer.setGasTariff(gas);
        consumer.setHeatTariff(heat);
        consumerRepository.save(consumer);
        String s = String.valueOf(consumerRepository.findAll().size());
        User user = User.builder()
                .userName(s)
                .password(new BCryptPasswordEncoder().encode(consumer.getName()))
                .consumer(consumer)
                .build();
        Role role = roleRepository.findByName(CONSUMER);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void editConsumer(ConsumerDto cons, Integer operId, Integer elId,
                             Integer gasId, Integer heatId) {
        Consumer consumer = consumerMapper.toEntity(cons);
        Operator oper = operatorRepository.getReferenceById(operId);
        ElectricityTariff el = elRepository.getReferenceById(elId);
        GasTariff gas = gasRepository.getReferenceById(gasId);
        HeatTariff heat = heatRepository.getReferenceById(heatId);
        consumer.setOperator(oper);
        consumer.setElectricityTariff(el);
        consumer.setGasTariff(gas);
        consumer.setHeatTariff(heat);
        consumerRepository.save(consumer);
    }

    public List<ConsumerDto> getAllFindConsumers(String name, String surname) {
        return consumerRepository.findAllByNameAndSurname(name, surname).stream()
                .map(consumerMapper::toDto)
                .collect(Collectors.toList());
    }
}
