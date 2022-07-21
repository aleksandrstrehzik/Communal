package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.OperatorService;
import org.example.service.impl.UserDetailsServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/operator")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;
    private final UserDetailsServiceImpl userService;


    @GetMapping("/{pageNumber}")
    public String getPaginatedOperators(@PathVariable(value = "pageNumber") int pageNumber, @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir, Model model) {
        Page<OperatorDto> page = operatorService.findAllPaginated(pageNumber, sortField, sortDir);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<OperatorDto> oper = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("operDto", oper);
        return "/operator/all-operators";
    }

    @GetMapping()
    public String showOperatorsFirstPage(Model model) {
        return getPaginatedOperators(1, "id", "asc", model);
    }

   /* @GetMapping("/edit-operator/{id}")
    public String editOperator(@PathVariable("id") Integer id, Model model) {
        OperatorDto operator = operatorService.getOperatorById(id);
        List<AdminDto> allAdmin = operatorService.getAllAdmin();
        model.addAttribute("admin", allAdmin);
        model.addAttribute("oper", operator);
        return "/operator/edit-operator";
    }*/

   /* @PostMapping("/edit-operator")
    public String editOperator(@ModelAttribute("oper") OperatorDto operator,
                               @RequestParam("admi") Integer id) {
        operatorService.updateOperator(operator, id);
        return "redirect:/operator";
    }*/
/*
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        operatorService.deleteOperator(id);
        return "redirect:/operator";
    }*/

   /* @GetMapping("/add-operator")
    public String addOperator(Principal principal) {
        Integer id = userService.getUserByName(principal.getName()).getAdmin().getId();
        System.out.println(id);
        return "/operator/add-operator";
    }*/

  /*  @GetMapping("/allAdmin-operators")
    public String allAdminOperators(Principal principal, Model model) {
        Integer id = userService.getUserByName(principal.getName()).getAdmin().getId();
        List<OperatorDto> oper = operatorService.getAllAdminsOperators(id);
        model.addAttribute("operDto", oper);
        return "/operator/admins-operators";
    }*/

/*    @GetMapping("/find-operator")
    public String findOperator(Model model) {
        List<OperatorDto> allOperators = operatorService.getAllOperators();
        model.addAttribute("oper", allOperators);
        return "/operator/find-operator";
    }*/

    /*@PostMapping("/add-operator")
    public String addOperator(@RequestParam("operLabel") String operLabel,
                              @RequestParam("adminLabel") String adminLabel) {
        operatorService.createOperator(operLabel, adminLabel);
        return "redirect:/operator";
    }*/

    /*@GetMapping("/info-operator")
    public String infoOperator(@RequestParam("operLabel") String operLabel, Model model,
                               HttpSession session) {
        session.setAttribute("name", operLabel);
        long consumer = operatorService.getConsumersOfOperator(operLabel);
        OperatorDto oper = operatorService.getOperatorByLabel(operLabel);
        List<ElectricityTariffDto> elTar = operatorService.electricityTariffsOfOperator(operLabel);
        List<GasTariffDto> gasTar = operatorService.gasTariffsOfOperator(operLabel);
        List<HeatTariffDto> heatTar = operatorService.heatTariffsOfOperator(operLabel);
        model.addAttribute("consumer", consumer);
        model.addAttribute("operator", oper);
        model.addAttribute("elTar", elTar);
        model.addAttribute("gasTar", gasTar);
        model.addAttribute("heatTar", heatTar);
        return "/operator/info-operator";
    }*/

    /*@GetMapping("/all-tariff/{adLabel}")
    public String getAllTariffs(@PathVariable("adLabel") String adminLabel,
                                Model model) {
        List<ElectricityTariffDto> elTar = operatorService.getElTariffsCreateByAdmin(adminLabel);
        List<GasTariffDto> gasTar = operatorService.getGasTariffsCreateByAdmin(adminLabel);
        List<HeatTariffDto> heatTar = operatorService.getHeatTariffsCreateByAdmin(adminLabel);
        model.addAttribute("elTar", elTar);
        model.addAttribute("gasTar", gasTar);
        model.addAttribute("heatTar", heatTar);
        return "/tariffs/all-tariffs";
    }

    @GetMapping("/add-tariff")
    public String addTariffToOperator(@RequestParam("id") Integer id,
                                      @RequestParam("value") String value, HttpSession session) {
        String name = (String) session.getAttribute("name");
        operatorService.addTariffToOperator(value, id, name);
        return "redirect:/operator/info-operator?operLabel=" + name;
    }

    @GetMapping("/deleteTariff")
    public String deleteTariffFromOperator(@RequestParam("id") Integer id,
                                           @RequestParam("value") String value, HttpSession session) {
        String name = (String) session.getAttribute("name");
        operatorService.deleteTariffFromOperator(id, value, name);
        return "redirect:/operator/info-operator?operLabel=" + name;
    }*/
}