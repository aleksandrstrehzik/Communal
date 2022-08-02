package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.ConsumerRepository;
import org.example.dao.MonthReportRepository;
import org.example.dto.MonthReportDto;
import org.example.entity.Consumer;
import org.example.entity.MonthReport;
import org.example.mapper.MonthReportMapper;
import org.example.service.interfaces.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MonthReportRepository repRep;
    private final MonthReportMapper reportMapper;
    private final ConsumerRepository consumerRepository;


    @Transactional
    public List<Month> createReport(Integer consumerId, BigDecimal elValue, BigDecimal gasValue,
                                    BigDecimal heatValue, int month, Integer year) {
        Consumer consumer = consumerRepository.getReferenceById(consumerId);
        String operLabel = consumer.getOperator().getLabel();
        MonthReport previous = repRep.findPreviousReport(consumerId);
        BigDecimal el = consumer.getElectricityTariff().getTariff();
        BigDecimal gas = consumer.getGasTariff().getTariff();
        BigDecimal heat = consumer.getHeatTariff().getTariff();
        Month monthNew = Month.of(month + 1);
        if (previous == null) {
            MonthReport report = getMonthReport(el, heat, gas, monthNew, consumer, operLabel, year, elValue,
                    elValue, gasValue, gasValue, heatValue, heatValue);
            repRep.save(report);
            return null;
        }
        if (monthNew.minus(1) == previous.getMonth()) {
            BigDecimal elConsumed = elValue.subtract(previous.getTotalElConsumed());
            BigDecimal gasConsumed = gasValue.subtract(previous.getTotalGasConsumed());
            BigDecimal heatConsumed = heatValue.subtract(previous.getTotalHeatConsumed());
            MonthReport report = getMonthReport(el, heat, gas, monthNew, consumer, operLabel, year, elConsumed,
                    elValue, gasConsumed, gasValue, heatConsumed, heatValue);
            repRep.save(report);
            return null;
        }
        Month m = monthNew;
        List<Month> ListM = new ArrayList<>();
        do {
            ListM.add(m);
            m = m.minus(1);
        } while (m != previous.getMonth());
        return ListM;
    }

    private MonthReport getMonthReport(BigDecimal el, BigDecimal heat, BigDecimal gas, Month monthNew, Consumer consumer,
                                       String operLabel, Integer year, BigDecimal elValue, BigDecimal elValue1, BigDecimal gasValue,
                                       BigDecimal gasValue1, BigDecimal heatValue, BigDecimal heatValue1) {
        MonthReport report = MonthReport.builder()
                .electricityTariff(el)
                .heatTariff(heat)
                .gasTariff(gas)
                .month(monthNew)
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
                .totalElConsumed(elValue1)
                .totalGasConsumed(gasValue1)
                .totalHeatConsumed(heatValue1)
                .totalPayment(calculationElPayment(elValue, el)
                        .add(calculationGasPayment(gasValue, gas))
                        .add(calculationHeatPayment(heatValue, heat)))
                .build();
        return report;
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
            int index = Integer.parseInt(month);
            Month month1 = Month.of(index + 1);
            return repRep.findAllByConsumer_IdAndMonth(consumerId, month1).stream()
                    .map(reportMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (month == null && year != 1) {
            return repRep.findAllByConsumer_IdAndYear(consumerId, year).stream()
                    .map(reportMapper::toDto)
                    .collect(Collectors.toList());
        }
        int index = Integer.parseInt(month);
        Month month1 = Month.of(index + 1);
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
        repNew.setPaymentHeatEnergy(calculationHeatPayment(repNew.getAmountOfHeatEnergyConsumed(), repNew.getHeatTariff()));
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

    public List<String> monthList() {
        return Arrays.stream(Month.values())
                .map(month -> month.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")).toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());
    }
}
