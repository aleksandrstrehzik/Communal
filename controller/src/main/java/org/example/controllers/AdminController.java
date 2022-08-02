package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.interfaces.AdminService;
import org.example.service.interfaces.OperatorService;
import org.example.service.interfaces.TariffsService;
import org.example.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.example.controllers.MockUtils.*;

@Controller
@RequestMapping(ADMIN_)
@RequiredArgsConstructor
public class AdminController {

    public static final String ALL_TARIFF = "/all-tariff";
    public static final String OPERATOR_ADMINS_OPERATORS = "/operator/admins-operators";
    public static final String MESSAGE4 = "Создайте тарифам по всем видам отпускаемой энергии";
    private final OperatorService operatorService;
    private final UserService userService;
    private final TariffsService tariffsService;
    private final AdminService adminService;
    private final HttpSession session;


    @GetMapping(ADD_OPERATOR)
    public String addOperator(@ModelAttribute(OPER) OperatorDto oper, Principal principal, Model model) {
        String label = userService.getUserByName(principal.getName()).getAdmin().getLabel();
        List<ElectricityTariffDto> elTar = operatorService.getElTariffsCreateByAdmin(label);
        List<GasTariffDto> gasTar = operatorService.getGasTariffsCreateByAdmin(label);
        List<HeatTariffDto> heatTar = operatorService.getHeatTariffsCreateByAdmin(label);
        if (elTar.isEmpty() || gasTar.isEmpty() || heatTar.isEmpty()) {
            model.addAttribute(MESSAGE, MESSAGE4);
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            return OPERATOR_ERROR;
        }
        model.addAttribute(ADMIN_LABEL, label);
        model.addAttribute(EL_TAR, elTar);
        model.addAttribute(GAS_TAR, gasTar);
        model.addAttribute(HEAT_TAR, heatTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_ADD_OPERATOR;
    }

    @PostMapping(ADD_OPERATOR)
    public String addOperator(@ModelAttribute(OPER) @Valid OperatorDto oper, BindingResult bindingResult,
                              @RequestParam(ADMIN_LABEL) String adminLabel,
                              @RequestParam(EL_TAR) Integer elId,
                              @RequestParam(GAS_TAR) Integer gasId,
                              @RequestParam(HEAT_TAR) Integer heatId) {
        if (bindingResult.hasErrors())
            return REDIRECT_ADMIN_ADD_OPERATOR;
        operatorService.createOperator(oper, adminLabel, elId, gasId, heatId);
        return REDIRECT_FOR_ALL;
    }

    @GetMapping(EDIT_OPERATOR_ID)
    public String editOperator(@PathVariable(ID) Integer id, Model model) {
        OperatorDto operator = operatorService.getOperatorById(id);
        model.addAttribute(OPER, operator);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_EDIT_OPERATOR;
    }

    @PostMapping(EDIT_OPERATOR)
    public String editOperator(@ModelAttribute(OPER) @Valid OperatorDto operator,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            return OPERATOR_EDIT_OPERATOR;
        }
        operatorService.updateOperator(operator);
        return REDIRECT_ADMIN_ALL_ADMIN_OPERATORS;
    }


    @GetMapping(DELETE_ID)
    public String deleteOperator(@PathVariable(ID) Integer id) {
        operatorService.deleteOperator(id);
        return REDIRECT_ADMIN_ALL_ADMIN_OPERATORS;
    }

    @GetMapping(ALL_ADMIN_OPERATORS)
    public String allAdminOperators(Principal principal, Model model) {
        Integer id = userService.getUserByName(principal.getName()).getAdmin().getId();
        List<OperatorDto> oper = operatorService.getAllAdminsOperators(id);
        model.addAttribute(OPER_DTO, oper);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_ADMINS_OPERATORS;
    }

    @GetMapping(ALL_TARIFF)
    public String getAllTariffs(@RequestParam(ADMIN_ID) Integer adminId,
                                @RequestParam(OPER_ID) Integer operId,
                                Model model) {
        List<ElectricityTariffDto> elTar = operatorService.getElTariffsWitchOperatorDontHave(adminId, operId);
        List<GasTariffDto> gasTar = operatorService.getGasTariffsWitchOperatorDontHave(adminId, operId);
        List<HeatTariffDto> heatTar = operatorService.getHeatTariffsWitchOperatorDontHave(adminId, operId);
        model.addAttribute(EL_TAR, elTar);
        model.addAttribute(GAS_TAR, gasTar);
        model.addAttribute(HEAT_TAR, heatTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_ALL_TARIFFS;
    }

    @GetMapping(ADD_TARIFF)
    public String addTariffToOperator(@RequestParam(ID) Integer id,
                                      @RequestParam(VALUE) String value) {
        String name = (String) session.getAttribute(NAME);
        operatorService.addTariffToOperator(value, id, name);
        return REDIRECT_FOR_ALL_INFO_OPERATOR_OPER_LABEL + name;
    }

    @GetMapping(DELETE_TARIFF)
    public String deleteTariffFromOperator(@RequestParam(ID) Integer id,
                                           @RequestParam(VALUE) String value) {
        String name = (String) session.getAttribute(NAME);
        operatorService.deleteTariffFromOperator(id, value, name);
        return REDIRECT_FOR_ALL_INFO_OPERATOR_OPER_LABEL + name;
    }

    @GetMapping(DELETE_EL_TAR_ID)
    public String deleteElTar(@PathVariable(ID) Integer id) {
        tariffsService.deleteElTar(id);
        return REDIRECT_FOR_ALL_EL_TAR;
    }

    @GetMapping(DELETE_GAS_TAR_ID)
    public String deleteGasTar(@PathVariable(ID) Integer id) {
        tariffsService.deleteGasTar(id);
        return REDIRECT_FOR_ALL_GAS_TAR;
    }

    @GetMapping(DELETE_HEAT_TAR_ID)
    public String deleteHeatTar(@PathVariable(ID) Integer id) {
        tariffsService.deleteHeatTar(id);
        return REDIRECT_FOR_ALL_HEAT_TAR;
    }

    @GetMapping(EDIT_EL_TAR_ID)
    public String editElTar(@PathVariable(ID) Integer id, Model model) {
        ElectricityTariffDto elTar = tariffsService.getElTar(id);
        model.addAttribute(EL_TAR, elTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_EDIT_EL_TAR;
    }

    @PostMapping(EDIT_EL_TAR)
    public String editElTar(@ModelAttribute(EL_TAR) @Valid ElectricityTariffDto el,
                            BindingResult bindingResult, Model model) {
        Integer id = userService.getUserByName(session.getAttribute(CURRENT_USER_NAME).toString()).getAdmin().getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute(EL_TAR, el);
            return TARIFFS_EDIT_EL_TAR;
        }
        tariffsService.updateElTar(el, id);
        return REDIRECT_FOR_ALL_EL_TAR;
    }

    @GetMapping(EDIT_GAS_TAR_ID)
    public String editGasTar(@PathVariable(ID) Integer id, Model model) {
        GasTariffDto gasTar = tariffsService.getGasTar(id);
        model.addAttribute(GAS_TAR, gasTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_EDIT_GAS_TAR;
    }

    @PostMapping(EDIT_GAS_TAR)
    public String editElTar(@ModelAttribute(GAS_TAR) @Valid GasTariffDto gas,
                            BindingResult bindingResult, Model model) {
        Integer id = userService.getUserByName(session.getAttribute(CURRENT_USER_NAME).toString()).getAdmin().getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute(GAS_TAR, gas);
            return TARIFFS_EDIT_GAS_TAR;
        }
        tariffsService.updateGasTar(gas, id);
        return REDIRECT_FOR_ALL_GAS_TAR;

    }

    @GetMapping(EDIT_HEAT_TAR_ID)
    public String editHeatTar(@PathVariable(ID) Integer id, Model model) {
        HeatTariffDto heatTar = tariffsService.getHeatTar(id);
        model.addAttribute(HEAT_TAR, heatTar);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_EDIT_HEAT_TAR;
    }

    @PostMapping(EDIT_HEAT_TAR)
    public String editHeatTar(@ModelAttribute(HEAT_TAR) @Valid HeatTariffDto heat,
                              BindingResult bindingResult, Model model) {
        Integer id = userService.getUserByName(session.getAttribute(CURRENT_USER_NAME).toString()).getAdmin().getId();
        tariffsService.updateHeatTar(heat, id);
        if (bindingResult.hasErrors()) {
            model.addAttribute(HEAT_TAR, heat);
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            return TARIFFS_EDIT_HEAT_TAR;
        }
        return REDIRECT_FOR_ALL_HEAT_TAR;
    }

    @GetMapping(ADD_TAR_VALUE)
    public String addTar(Model model,
                         @PathVariable(VALUE) String value) {
        model.addAttribute(VALUE, value);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return TARIFFS_CREATE_TARIFF;
    }

    @PostMapping(ADD_TAR)
    public String addTar(@RequestParam(VALUE) String value,
                         @RequestParam(TARR) BigDecimal tariff) {
        Integer adminId = userService.getUserByName(session.getAttribute(CURRENT_USER_NAME).toString()).getAdmin().getId();
        tariffsService.createTariff(adminId, value, tariff);
        return REDIRECT_FOR_ALL1 + value + TAR;
    }

    @GetMapping(CREATE_ADMIN)
    public String createAdmin(@ModelAttribute(ADMIN) AdminDto admin, Model model) {
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_CREATE_ADMIN;
    }

    @PostMapping(CREATE_ADMIN)
    public String saveAdmin(@ModelAttribute(ADMIN) @Valid AdminDto admin, BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
            return OPERATOR_CREATE_ADMIN;
        }
        adminService.createAdmin(admin);
        List<AdminDto> allAdmins = adminService.getAllAdmins();
        model.addAttribute(ADMIN_DTO, allAdmins);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_ALL_ADMINS;
    }

    @GetMapping(ADMIN_LIST)
    public String getAdmins(Model model) {
        List<AdminDto> allAdmins = adminService.getAllAdmins();
        model.addAttribute(ADMIN_DTO, allAdmins);
        model.addAttribute(ROLE, session.getAttribute(ROLE_VALUE));
        return OPERATOR_ALL_ADMINS;
    }
}
