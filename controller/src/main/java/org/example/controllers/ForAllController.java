package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.AdminService;
import org.example.service.OperatorService;
import org.example.service.TariffsService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.example.controllers.MockUtils.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(FOR_ALL)
public class ForAllController {

    private final OperatorService operatorService;
    private final TariffsService tariffsService;
    private final AdminService adminService;
    private final HttpSession session;

    @GetMapping(INFO_OPERATOR)
    public String infoOperator(@RequestParam(OPER_LABEL) String operLabel, Model model) {
        session.setAttribute(NAME, operLabel);
        long consumer = operatorService.getConsumersOfOperator(operLabel);
        OperatorDto oper = operatorService.getOperatorByLabel(operLabel);
        String currentUserName = (String) session.getAttribute(CURRENT_USER_NAME);
        if (oper.getAdmin().equals(adminService.getAdmin(currentUserName))) {
            model.addAttribute(FLAG, true);
        }
        if (oper.equals(operatorService.getOperatorByLabel(currentUserName))){
            model.addAttribute(FLAG_2, true);
        }
        List<ConsumerDto> conWithOut = operatorService.getConsumersWithOutTariff(operLabel);
        List<ElectricityTariffDto> elTar = operatorService.electricityTariffsOfOperator(operLabel);
        List<GasTariffDto> gasTar = operatorService.gasTariffsOfOperator(operLabel);
        List<HeatTariffDto> heatTar = operatorService.heatTariffsOfOperator(operLabel);
        model.addAttribute(CONSUMER1, consumer);
        model.addAttribute(OPERATOR1, oper);
        model.addAttribute(EL_TAR, elTar);
        model.addAttribute(GAS_TAR, gasTar);
        model.addAttribute(HEAT_TAR, heatTar);
        model.addAttribute(CON_WITH_OUT, conWithOut);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_INFO_OPERATOR;
    }

    @GetMapping(FIND_OPERATOR)
    public String findOperator(Model model) {
        List<OperatorDto> allOperators = operatorService.getAllOperators();
        model.addAttribute(OPER, allOperators);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_FIND_OPERATOR;
    }

    @GetMapping(PAGE_NUMBER1)
    public String getPaginatedOperators(@PathVariable(value = PAGE_NUMBER) int pageNumber, @RequestParam(SORT_FIELD) String sortField,
                                        @RequestParam(SORT_DIR) String sortDir, Model model) {
        Page<OperatorDto> page = operatorService.findAllPaginated(pageNumber, sortField, sortDir);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<OperatorDto> oper = page.getContent();
        model.addAttribute(CURRENT_PAGE, pageNumber);
        model.addAttribute(TOTAL_PAGES, totalPages);
        model.addAttribute(TOTAL_ITEMS, totalItems);
        model.addAttribute(SORT_FIELD, sortField);
        model.addAttribute(SORT_DIR, sortDir);
        model.addAttribute(REVERSE_SORT_DIR, sortDir.equals(ASC) ? DESC : ASC);
        model.addAttribute(OPER_DTO, oper);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_ALL_OPERATORS;
    }

    @GetMapping()
    public String showOperatorsFirstPage(Model model) {
        return getPaginatedOperators(1, ID, ASC, model);
    }

    @GetMapping(EL_TAR1)
    public String allElTar(Model model) {
        List<ElectricityTariffDto> allElTar = tariffsService.getAllElTar();
        String currentUserName = (String) session.getAttribute(CURRENT_USER_NAME);
        if (adminService.getAdmin(currentUserName) != null) {
            model.addAttribute(FLAG, true);
        }
        model.addAttribute(EL_TAR, allElTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_EL_TAR;
    }

    @GetMapping(GAS_TAR1)
    public String allGasTar(Model model) {
        List<GasTariffDto> allGasTar = tariffsService.getAllGasTar();
        String currentUserName = (String) session.getAttribute(CURRENT_USER_NAME);
        if (adminService.getAdmin(currentUserName) != null) {
            model.addAttribute(FLAG, true);
        }
        model.addAttribute(GAS_TAR, allGasTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_GAS_TAR;
    }

    @GetMapping(HEAT_TAR1)
    public String allHeatTar(Model model) {
        List<HeatTariffDto> allHeatTar = tariffsService.getAllHeatTar();
        String currentUserName = (String) session.getAttribute(CURRENT_USER_NAME);
        if (adminService.getAdmin(currentUserName) != null) {
            model.addAttribute(FLAG, true);
        }
        model.addAttribute(HEAT_TAR, allHeatTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_HEAT_TAR;
    }
}
