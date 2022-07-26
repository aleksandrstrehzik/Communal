package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.entity.enums.Months;
import org.example.service.ConsumerService;
import org.example.service.OperatorService;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.example.controllers.MockUtils.*;

@Controller
@RequestMapping(OPERATOR_)
@RequiredArgsConstructor
public class OperatorController {

    public static final String GET_CONS_WITH_OUT_TARIFFS = "/getConsWithOutTariffs";
    private final OperatorService operatorService;
    private final ConsumerService consumerService;
    private final ReportService reportService;
    private final HttpSession session;
    private final UserService userDetailsService;

    @GetMapping(PAGE_NUMBER1)
    public String getPaginatedConsumers(@PathVariable(value = PAGE_NUMBER) int pageNumber, @RequestParam(SORT_FIELD) String sortField,
                                        @RequestParam(SORT_DIR) String sortDir, Model model) {
        Page<ConsumerDto> page = consumerService.findAllPaginated(pageNumber, sortField, sortDir);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<ConsumerDto> cons = page.getContent();
        model.addAttribute(CURRENT_PAGE, pageNumber);
        model.addAttribute(TOTAL_PAGES, totalPages);
        model.addAttribute(TOTAL_ITEMS, totalItems);
        model.addAttribute(SORT_FIELD, sortField);
        model.addAttribute(SORT_DIR, sortDir);
        model.addAttribute(REVERSE_SORT_DIR, sortDir.equals(ASC) ? DESC : ASC);
        model.addAttribute(CONSUMER_DTO, cons);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_ALL_CONSUMERS;
    }

    @GetMapping()
    public String showConsumersFirstPage(Model model) {
        return getPaginatedConsumers(1, ID, ASC, model);
    }

    @GetMapping(ADD_CONSUMER)
    public String addConsumer(@ModelAttribute(CONSUMER1) ConsumerDto cons, Model model,
                              Principal principal) {
        String name = principal.getName();
        List<ElectricityTariffDto> elTar = operatorService.electricityTariffsOfOperator(name);
        List<GasTariffDto> gasTar = operatorService.gasTariffsOfOperator(name);
        List<HeatTariffDto> heatTar = operatorService.heatTariffsOfOperator(name);
        if (elTar.isEmpty() || gasTar.isEmpty() || heatTar.isEmpty()) {
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            model.addAttribute(MESSAGE, MESSAGE3);
            return "/operator/error";
        }
        model.addAttribute(EL_TAR, elTar);
        model.addAttribute(GAS_TAR, gasTar);
        model.addAttribute(HEAT_TAR, heatTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_CREATE_CONSUMER;
    }

    @PostMapping(CREATE_CONSUMER)
    public String createConsumer(@RequestParam(EL_TAR) Integer elId,
                                 @RequestParam(GAS_TAR) Integer gasId,
                                 @RequestParam(HEAT_TAR) Integer heatId,
                                 @ModelAttribute(CONSUMER1) @Valid ConsumerDto consumerDto,
                                 BindingResult bindingResult, Model model, Principal principal) {
        String name = principal.getName();
        Integer operId = operatorService.getOperatorByLabel(name).getId();
        if (bindingResult.hasErrors()) {
            List<ElectricityTariffDto> elTar = operatorService.electricityTariffsOfOperator(name);
            List<GasTariffDto> gasTar = operatorService.gasTariffsOfOperator(name);
            List<HeatTariffDto> heatTar = operatorService.heatTariffsOfOperator(name);
            model.addAttribute(EL_TAR, elTar);
            model.addAttribute(GAS_TAR, gasTar);
            model.addAttribute(HEAT_TAR, heatTar);
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            return CONSUMER_CREATE_CONSUMER;
        }
        consumerService.createConsumer(consumerDto, operId, elId, gasId, heatId);
        return REDIRECT_OPERATOR;
    }

    @GetMapping(EDIT_CONSUMER_ID)
    public String editConsumer(@PathVariable(ID) Integer id, Model model) {
        String name = session.getAttribute(CURRENT_USER_NAME).toString();
        ConsumerDto cons = consumerService.getConsumerById(id);
        List<ElectricityTariffDto> elTar = operatorService.electricityTariffsOfOperator(name);
        List<GasTariffDto> gasTar = operatorService.gasTariffsOfOperator(name);
        List<HeatTariffDto> heatTar = operatorService.heatTariffsOfOperator(name);
        if (elTar.isEmpty() || gasTar.isEmpty() || heatTar.isEmpty()) {
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            model.addAttribute(MESSAGE, MESSAGE3);
            return "/operator/error";
        }
        model.addAttribute(EL_TAR, elTar);
        model.addAttribute(GAS_TAR, gasTar);
        model.addAttribute(HEAT_TAR, heatTar);
        model.addAttribute(CONSUMER1, cons);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_EDIT_CONSUMER;
    }

    @PostMapping(EDIT_CONSUMER)
    public String editConsumer(@RequestParam(EL_TAR) Integer elId,
                               @RequestParam(GAS_TAR) Integer gasId,
                               @RequestParam(HEAT_TAR) Integer heatId,
                               @ModelAttribute(CONSUMER1) @Valid ConsumerDto consumerDto,
                               BindingResult bindingResult, Model model) {
        String name = session.getAttribute(CURRENT_USER_NAME).toString();
        Integer operId = operatorService.getOperatorByLabel(name).getId();
        if(bindingResult.hasErrors()) {
            ConsumerDto cons = consumerService.getConsumerById(consumerDto.getId());
            List<ElectricityTariffDto> elTar = operatorService.electricityTariffsOfOperator(name);
            List<GasTariffDto> gasTar = operatorService.gasTariffsOfOperator(name);
            List<HeatTariffDto> heatTar = operatorService.heatTariffsOfOperator(name);
            model.addAttribute(EL_TAR, elTar);
            model.addAttribute(GAS_TAR, gasTar);
            model.addAttribute(HEAT_TAR, heatTar);
            model.addAttribute(CONSUMER1, cons);
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            return CONSUMER_EDIT_CONSUMER;
        }
        consumerService.editConsumer(consumerDto, operId, elId, gasId, heatId);
        return REDIRECT_OPERATOR_FIND_OP_CONS;
    }

    @GetMapping(DELETE_CONSUMER_ID)
    public String deleteConsumer(@PathVariable(ID) Integer id) {
        consumerService.deleteConsumer(id);
        return REDIRECT_OPERATOR_FIND_OP_CONS;
    }

    @GetMapping(FIND_CONS_INFO)
    public String getConsInfo(Model model) {
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_FIND_CONSUMER;
    }

    @GetMapping(SHOW_ALL_FIND)
    public String showAllFind(@RequestParam(CONS_NAME) String consName,
                              @RequestParam(CONS_SURNAME) String consSurname, Model model) {
        List<ConsumerDto> allFindConsumers = consumerService.getAllFindConsumers(consName, consSurname);
        model.addAttribute(CONSUMER_DTO, allFindConsumers);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_FIND_CONSUMER;
    }

    @GetMapping(GET_INFO_ID)
    public String showInfo(@PathVariable(ID) Integer id,
                           Model model) {
        String currentUserName = session.getAttribute(CURRENT_USER_NAME).toString();
        ConsumerDto consumerById = consumerService.getConsumerById(id);
        model.addAttribute(CONS, consumerById);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_INFO_CONSUMER;
    }

    @GetMapping(ALL_REPORT)
    public String getAllReport(Model model, HttpServletRequest req,
                               @RequestParam(CONS_ID) Integer id) {
        String mon = getParam(req, MON);
        Integer year1 = Integer.parseInt(getParam(req, YEAR_1));
        Months[] months = Months.values();
        String currentUserName = session.getAttribute(CURRENT_USER_NAME).toString();
        ConsumerDto consumerById = consumerService.getConsumerById(id);
        if (consumerById.getOperator() != null &&
                consumerById.getOperator().equals(userDetailsService.getUserByName(currentUserName).getOperator())) {
            model.addAttribute(FLAG, true);
        }
        model.addAttribute(CONS, consumerById);
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
        return CONSUMER_INFO_CONSUMER;
    }

    @GetMapping(EDIT_REPORT_ID)
    public String editReport(@PathVariable(ID) Integer id, Model model) {
        String operator = reportService.getMonthReport(id).getOperator();
        model.addAttribute(REP, reportService.getMonthReport(id));
        model.addAttribute(EL_TAR, operatorService.electricityTariffsOfOperator(operator));
        model.addAttribute(GAS_TAR, operatorService.gasTariffsOfOperator(operator));
        model.addAttribute(HEAT_TAR, operatorService.heatTariffsOfOperator(operator));
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return REPORT_EDIT_REPORT;
    }

    @PostMapping(EDIT_REPORT)
    public String editReport(@ModelAttribute(REP) MonthReportDto report,
                             @RequestParam(CONS_ID) Integer consId) {
        reportService.editReport(consId, report);
        return REDIRECT_OPERATOR_GET_INFO + consId;
    }

    @GetMapping(FIND_OP_CONS)
    public String findOpCons(Model model) {
        String currentUserName = session.getAttribute(CURRENT_USER_NAME).toString();
        List<ConsumerDto> list = operatorService.getOperatorConsumersList(currentUserName);
        model.addAttribute(CONSUMER_DTO, list);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_OPERATOR_CONSUMERS;
    }

    @GetMapping("/getConsWithOutTariffs")
    public String getConsWithOutTariffs(Model model) {
        String currentUserName = session.getAttribute(CURRENT_USER_NAME).toString();
        List<ConsumerDto> list = operatorService.getConsumersWithOutTariff(currentUserName);
        model.addAttribute(CONSUMER_DTO, list);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return CONSUMER_OPERATOR_CONSUMERS;
    }
}
