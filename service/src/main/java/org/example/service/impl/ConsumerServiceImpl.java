package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.ConsumerDto;
import org.example.dao.*;
import org.example.dto.OperatorDto;
import org.example.entity.*;
import org.example.mapper.*;
import org.example.service.ConsumerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final ConsumerMapper consumerMapper;
    private final OperatorRepository operatorRepository;
    private final ElectricityTariffRepository elRepository;
    private final HeatTariffRepository heatRepository;
    private final GasTariffRepository gasRepository;


    public Page<ConsumerDto> findAllPaginated(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable paged = PageRequest.of(pageNumber - 1, 5, sort);
        return consumerRepository.findAll(paged).map(consumerMapper::toDto);
    }

    public void editConsumer(Integer id) {

    }

    public ConsumerDto getConsumerById(Integer id) {
        return consumerMapper.toDto(consumerRepository.getReferenceById(id));
    }

    public void deleteConsumer(Integer id) {
        consumerRepository.deleteById(id);
    }

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
    }

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
}
