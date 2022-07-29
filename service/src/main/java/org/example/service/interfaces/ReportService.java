package org.example.service.interfaces;

import org.example.dto.MonthReportDto;

import java.math.BigDecimal;
import java.util.List;

public interface ReportService {

    void createReport(Integer consumerId, BigDecimal elValue, BigDecimal gasValue,
                      BigDecimal heatValue, String month, Integer year);

    List<MonthReportDto> getMonthReports(Integer consumerId, String month, Integer year);

    MonthReportDto getMonthReport(Integer id);

    void editReport(Integer consId, MonthReportDto report);

    MonthReportDto getLastMonthReportOfConsumer(Integer consId);
}
