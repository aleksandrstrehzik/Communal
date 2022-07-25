package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ConsumerDto;
import org.example.entity.enums.Months;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;

import static org.example.controllers.MockUtils.*;

@Controller
@RequestMapping(CONSUMER)
@RequiredArgsConstructor
public class ConsumerController {

    private final ReportService reportService;
    private final UserService userService;
    private final HttpSession session;

    @GetMapping(CREATE_REPORT)
    public String createReport(Model model, Principal principal) {
        String name = principal.getName();
        ConsumerDto consumer = userService.getUserByName(name).getConsumer();
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        if (consumer.getOperator() == null) {
            model.addAttribute(MESSAGE, MESSAGE1);
            return REPORT_ERROR;
        }
        if (consumer.getElectricityTariff() == null || consumer.getGasTariff() == null
                || consumer.getHeatTariff() == null) {
            model.addAttribute(MESSAGE, MESSAGE2);
            return REPORT_ERROR;
        }
        Months[] months = Months.values();
        model.addAttribute(REP, reportService.getLastMonthReportOfConsumer(consumer.getId()));
        model.addAttribute(MONTHS, months);
        model.addAttribute(CONS, consumer);
        return REPORT_CREATE_REPORT;
    }

    @PostMapping(CREATE_REPORT)
    public String createReport(@RequestParam(ID) Integer consumerId,
                               @RequestParam(EL_VALUE) BigDecimal elValue,
                               @RequestParam(GAS_VALUE) BigDecimal gasValue,
                               @RequestParam(HEAT_VALUE) BigDecimal heatValue,
                               @RequestParam(MON) String month,
                               @RequestParam(YEAR) Integer year) {
        reportService.createReport(consumerId, elValue, gasValue,
                heatValue, month, year);
        return REDIRECT_CONSUMER_CREATE_REPORT;
    }

    @GetMapping(ALL_REPORTS)
    public String getAllReports(Model model, Principal principal) {
        String name = principal.getName();
        Integer id = userService.getUserByName(name).getConsumer().getId();
        model.addAttribute(MONTHS, Months.values());
        model.addAttribute(CONS_ID, id);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return REPORT_ALL_REPORT;
    }

    @GetMapping(ALL_REPORT)
    public String getAllReport(Model model, HttpServletRequest req,
                               @RequestParam(CONS_ID) Integer id) {
        String mon = getParam(req, MON);
        Integer year1 = Integer.parseInt(getParam(req, YEAR_1));
        Months[] months = Months.values();
        model.addAttribute(MONTHS, months);
        model.addAttribute(REP_LIST, reportService.getMonthReports(id, mon, year1));
        model.addAttribute(CONS_ID, getParam(req, CONS_ID));
        model.addAttribute(ID, getParam(req, ID));
        model.addAttribute(EL_COST, getParam(req, EL_COST));
        model.addAttribute(GAS_COST, getParam(req, GAS_COST));
        model.addAttribute(HEAT_COST, getParam(req, HEAT_COST));
        model.addAttribute(EL_PAY, getParam(req, EL_PAY));
        model.addAttribute(GAS_PAY, getParam(req, GAS_PAY));
        model.addAttribute(HEAT_PAY, getParam(req, HEAT_PAY));
        model.addAttribute(EL_VALUE, getParam(req, EL_VALUE));
        model.addAttribute(GAS_VALUE, getParam(req, GAS_VALUE));
        model.addAttribute(HEAT_VALUE, getParam(req, HEAT_VALUE));
        model.addAttribute(EL_COUNT, getParam(req, EL_COUNT));
        model.addAttribute(GAS_COUNT, getParam(req, GAS_COUNT));
        model.addAttribute(HEAT_COUNT, getParam(req, HEAT_COUNT));
        model.addAttribute(IS_PAID, getParam(req, IS_PAID));
        model.addAttribute(OPER_LABEL, getParam(req, OPER_LABEL));
        model.addAttribute(YEAR, getParam(req, YEAR));
        model.addAttribute(MONTH, getParam(req, MONTH));
        model.addAttribute(TOTAL_PAYMENT, getParam(req, TOTAL_PAYMENT));
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return REPORT_ALL_REPORT;
    }
}
