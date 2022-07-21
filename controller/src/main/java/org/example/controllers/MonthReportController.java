package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.dto.ConsumerDto;
import org.example.dto.MonthReportDto;
import org.example.entity.enums.Months;
import org.example.service.ConsumerService;
import org.example.service.OperatorService;
import org.example.service.ReportService;
import org.example.service.TariffsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class MonthReportController {

    private final ReportService reportService;
    private final ConsumerService consumerService;
    private final OperatorService operatorService;
    private final TariffsService tariffsService;

    @GetMapping()
    public String createReport(Model model) {
        ConsumerDto consumerById = consumerService.getConsumerById(2);
        Months[] months = Months.values();
        model.addAttribute("months", months);
        model.addAttribute("cons", consumerById);
        return "report/create-report";
    }

    @GetMapping("/allReports")
    public String getAllReports(Model model) {
        ConsumerDto consumerById = consumerService.getConsumerById(2);
        model.addAttribute("months", Months.values());
        model.addAttribute("consId", 2);
        return "/report/all-report";
    }

    @GetMapping("/allReport")
    public String getAllReport(Model model, HttpServletRequest req,
                               @RequestParam("consId") Integer id) {
        String mon = getParam(req, "mon");
        Integer year1 = Integer.parseInt(getParam(req, "year1"));
        Months[] months = Months.values();
        model.addAttribute("months", months);
        model.addAttribute("repList", reportService.getMonthReports(id, mon, year1));
        model.addAttribute("consId", getParam(req, "consId"));
        model.addAttribute("id", getParam(req, "id"));
        model.addAttribute("elCost", getParam(req, "elCost"));
        model.addAttribute("gasCost", getParam(req, "gasCost"));
        model.addAttribute("heatCost", getParam(req, "heatCost"));
        model.addAttribute("elPay", getParam(req, "elPay"));
        model.addAttribute("gasPay", getParam(req, "gasPay"));
        model.addAttribute("heatPay", getParam(req, "heatPay"));
        model.addAttribute("elValue", getParam(req, "elValue"));
        model.addAttribute("gasValue", getParam(req, "gasValue"));
        model.addAttribute("heatValue", getParam(req, "heatValue"));
        model.addAttribute("elCount", getParam(req, "elCount"));
        model.addAttribute("gasCount", getParam(req, "gasCount"));
        model.addAttribute("heatCount", getParam(req, "heatCount"));
        model.addAttribute("IsPaid", getParam(req, "IsPaid"));
        model.addAttribute("operLabel", getParam(req, "operLabel"));
        model.addAttribute("year", getParam(req, "year"));
        model.addAttribute("month", getParam(req, "month"));
        model.addAttribute("totalPayment", getParam(req, "totalPayment"));
        return "/report/all-report";
    }


    @PostMapping("/createReport")
    public String createReport(@RequestParam("id") Integer consumerId,
                               @RequestParam("elValue") BigDecimal elValue,
                               @RequestParam("gasValue") BigDecimal gasValue,
                               @RequestParam("heatValue") BigDecimal heatValue,
                               @RequestParam("mon") String month,
                               @RequestParam("year") Integer year) {
        reportService.createReport(consumerId, elValue, gasValue,
                heatValue, month, year);
        return "redirect:/report";
    }

    @GetMapping("/editReport/{id}")
    public String editReport(@PathVariable("id") Integer id, Model model) {
        String operator = reportService.getMonthReport(id).getOperator();
        model.addAttribute("rep", reportService.getMonthReport(id));
        model.addAttribute("elTar", operatorService.electricityTariffsOfOperator(operator));
        model.addAttribute("gasTar", operatorService.gasTariffsOfOperator(operator));
        model.addAttribute("heatTar", operatorService.heatTariffsOfOperator(operator));
        return "/report/edit-report";
    }

    @PostMapping("/editReport")
    public String editReport(@ModelAttribute("rep") MonthReportDto report,
                             @RequestParam("consId") Integer consId) {
        reportService.editReport(consId, report);
        return "redirect:/consumer/getInfo/" + consId;
    }

    @GetMapping("/editTotalValue/{id}")
    public String editTotalValue(@PathVariable("id") Integer id, Model model) {
        MonthReportDto last = reportService.getLastMonthReportOfConsumer(id);
        model.addAttribute("rep", last);
        return "/";
    }

    public static String getParam(HttpServletRequest req, String fieldName) {
        return Optional.ofNullable(req.getParameter(fieldName))
                .filter(StringUtils::isNotBlank)
                .orElse(null);
    }
}
