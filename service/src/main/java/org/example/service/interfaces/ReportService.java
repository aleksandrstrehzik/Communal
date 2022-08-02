package org.example.service.interfaces;

import org.example.dto.MonthReportDto;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public interface ReportService {

    List<Month> createReport(Integer consumerId, BigDecimal elValue, BigDecimal gasValue,
                             BigDecimal heatValue, int month, Integer year);

    List<MonthReportDto> getMonthReports(Integer consumerId, String month, Integer year);

    MonthReportDto getMonthReport(Integer id);

    void editReport(Integer consId, MonthReportDto report);

    MonthReportDto getLastMonthReportOfConsumer(Integer consId);

    List<String> monthList();
}
