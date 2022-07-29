package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.ConsumerRepository;
import org.example.dao.MonthReportRepository;
import org.example.dto.MonthReportDto;
import org.example.entity.Consumer;
import org.example.entity.MonthReport;
import org.example.entity.enums.Months;
import org.example.mapper.MonthReportMapper;
import org.example.service.interfaces.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MonthReportRepository repRep;
    private final MonthReportMapper reportMapper;
    private final ConsumerRepository consumerRepository;


    @Transactional
    public void createReport(Integer consumerId, BigDecimal elValue, BigDecimal gasValue,
                             BigDecimal heatValue, String month, Integer year) {
        Consumer consumer = consumerRepository.getReferenceById(consumerId);
        String operLabel = consumer.getOperator().getLabel();
        MonthReport previous = repRep.findPreviousReport(consumerId);
        BigDecimal el = consumer.getElectricityTariff().getTariff();
        BigDecimal gas = consumer.getGasTariff().getTariff();
        BigDecimal heat = consumer.getHeatTariff().getTariff();
        if (previous == null) {
            MonthReport report = MonthReport.builder()
                    .electricityTariff(el)
                    .heatTariff(heat)
                    .gasTariff(gas)
                    .month(Months.valueOf(month))
                    .consumer(consumer)
                    .operator(operLabel)
                    .year(year)
                    .IsPaid(true)
                    .amountOfElectricityEnergyConsumed(elValue)
                    .volumeOfConsumedGas(gasValue)
                    .amountOfHeatEnergyConsumed(heatValue)
                    .paymentElEnergy(calculationElPayment(elValue, el))
                    .paymentForGas(calculationGasPayment(gasValue, gas))
                    .paymentHeatEnergy(calculationHeatPayment(heatValue, heat))
                    .totalElConsumed(elValue)
                    .totalGasConsumed(gasValue)
                    .totalHeatConsumed(heatValue)
                    .totalPayment(calculationElPayment(elValue, el)
                            .add(calculationGasPayment(gasValue, gas))
                            .add(calculationHeatPayment(heatValue, heat)))
                    .build();
            repRep.save(report);
        } else {
            BigDecimal elConsumed = elValue.subtract(previous.getTotalElConsumed());
            BigDecimal gasConsumed = gasValue.subtract(previous.getTotalGasConsumed());
            BigDecimal heatConsumed = heatValue.subtract(previous.getTotalHeatConsumed());
            MonthReport report = MonthReport.builder()
                    .electricityTariff(el)
                    .heatTariff(heat)
                    .gasTariff(gas)
                    .month(Months.valueOf(month))
                    .consumer(consumer)
                    .operator(operLabel)
                    .year(year)
                    .IsPaid(true)
                    .amountOfElectricityEnergyConsumed(elConsumed)
                    .volumeOfConsumedGas(gasConsumed)
                    .amountOfHeatEnergyConsumed(heatConsumed)
                    .paymentElEnergy(calculationElPayment(elConsumed, el))
                    .paymentForGas(calculationGasPayment(gasConsumed, gas))
                    .paymentHeatEnergy(calculationHeatPayment(heatConsumed, heat))
                    .totalElConsumed(elValue)
                    .totalGasConsumed(gasValue)
                    .totalHeatConsumed(heatValue)
                    .totalPayment(calculationElPayment(elConsumed, el)
                            .add(calculationGasPayment(gasConsumed, gas))
                            .add(calculationHeatPayment(heatConsumed, heat)))
                    .build();
            repRep.save(report);
        }
    }

    public BigDecimal calculationElPayment(BigDecimal elValue, BigDecimal elTar) {
        return elValue.multiply(elTar);
    }

    public BigDecimal calculationGasPayment(BigDecimal gasValue, BigDecimal gasTar) {
        return gasValue.multiply(gasTar);
    }

    public BigDecimal calculationHeatPayment(BigDecimal heatValue, BigDecimal heatTar) {
        return heatValue.multiply(heatTar);
    }

    public List<MonthReportDto> getMonthReports(Integer consumerId, String month, Integer year) {
        if (month == null && year == 1) {
            return repRep.findAllByConsumer_Id(consumerId).stream()
                    .map(reportMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (month != null && year == 1) {
            Months month1 = Months.valueOf(month);
            return repRep.findAllByConsumer_IdAndMonth(consumerId, month1).stream()
                    .map(reportMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (month == null && year != 1) {
            return repRep.findAllByConsumer_IdAndYear(consumerId, year).stream()
                    .map(reportMapper::toDto)
                    .collect(Collectors.toList());
        }
        Months month1 = Months.valueOf(month);
        return repRep.findAllByConsumer_IdAndYearAndMonth(consumerId, year, month1).stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

    public MonthReportDto getMonthReport(Integer id) {
        return reportMapper.toDto(repRep.getReferenceById(id));
    }

    @Transactional
    public void editReport(Integer consId, MonthReportDto report) {
        MonthReport repNew = reportMapper.toEntity(report);
        MonthReport repOld = repRep.getReferenceById(report.getId());
        Consumer cons = consumerRepository.getReferenceById(consId);
        repNew.setConsumer(cons);
        if (repNew.getElectricityTariff() == null) {
            repNew.setElectricityTariff(repOld.getElectricityTariff());
        }
        if (repNew.getGasTariff() == null) {
            repNew.setGasTariff(repOld.getGasTariff());
        }
        if (repNew.getHeatTariff() == null) {
            repNew.setHeatTariff(repOld.getHeatTariff());
        }
        BigDecimal elDif = repNew.getAmountOfElectricityEnergyConsumed()
                .subtract(repOld.getAmountOfElectricityEnergyConsumed());
        BigDecimal gasDif = repNew.getVolumeOfConsumedGas()
                .subtract(repOld.getVolumeOfConsumedGas());
        BigDecimal heatDif = repNew.getAmountOfHeatEnergyConsumed()
                .subtract(repOld.getAmountOfHeatEnergyConsumed());
        repNew.setTotalElConsumed(repOld.getTotalElConsumed()
                .add(elDif));
        repNew.setTotalGasConsumed(repOld.getTotalGasConsumed().
                add(gasDif));
        repNew.setTotalHeatConsumed(repOld.getTotalHeatConsumed()
                .add(heatDif));
        repNew.setPaymentElEnergy(calculationElPayment(repNew.getAmountOfElectricityEnergyConsumed(), repNew.getElectricityTariff()));
        repNew.setPaymentForGas(calculationGasPayment(repNew.getVolumeOfConsumedGas(), repNew.getGasTariff()));
        repNew.setPaymentHeatEnergy(calculationHeatPayment(repNew.getAmountOfHeatEnergyConsumed(), repNew.getGasTariff()));
        BigDecimal totalPay = repNew.getPaymentElEnergy().add(repNew.getPaymentForGas().add(repNew.getPaymentHeatEnergy()));
        repNew.setTotalPayment(totalPay);
        if (totalPay.compareTo(repOld.getTotalPayment()) >= 0) {
            repNew.setIsPaid(false);
        } else {
            repNew.setIsPaid(true);
        }
        if (cons.getSw() != null) {
            BigDecimal add = cons.getSw().add(repOld.getTotalPayment().subtract(totalPay));
            cons.setSw(add);
        } else {
            cons.setSw(repOld.getTotalPayment().subtract(totalPay));
        }
        repRep.save(repNew);
        System.out.println(repNew);
    }

    public MonthReportDto getLastMonthReportOfConsumer(Integer consId) {
        return reportMapper.toDto(repRep.findPreviousReport(consId));
    }
}
