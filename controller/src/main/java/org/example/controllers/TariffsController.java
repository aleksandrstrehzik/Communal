package org.example.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.dto.AdminDto;
import org.example.dto.ElectricityTariffDto;
import org.example.dto.GasTariffDto;
import org.example.dto.HeatTariffDto;
import org.example.service.OperatorService;
import org.example.service.TariffsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/tariffs")
@RequiredArgsConstructor
public class TariffsController {

    private final TariffsService tariffsService;
    private final OperatorService operatorService;

    @GetMapping("/elTar")
    public String allElTar(Model model) {
        List<ElectricityTariffDto> allElTar = tariffsService.getAllElTar();
        model.addAttribute("elTar", allElTar);
        return "/tariffs/el-tar";
    }

    @GetMapping("/gasTar")
    public String allGasTar(Model model) {
        List<GasTariffDto> allGasTar = tariffsService.getAllGasTar();
        model.addAttribute("gasTar", allGasTar);
        return "/tariffs/gas-tar";
    }

    @GetMapping("/heatTar")
    public String allHeatTar(Model model) {
        List<HeatTariffDto> allHeatTar = tariffsService.getAllHeatTar();
        model.addAttribute("heatTar", allHeatTar);
        return "/tariffs/heat-tar";
    }

    @GetMapping("/deleteElTar/{id}")
    public String deleteElTar(@PathVariable("id") Integer id) {
        tariffsService.deleteElTar(id);
        return "redirect:/tariffs/elTar";
    }

    @GetMapping("/deleteGasTar/{id}")
    public String deleteGasTar(@PathVariable("id") Integer id) {
        tariffsService.deleteGasTar(id);
        return "redirect:/tariffs/gasTar";
    }

    @GetMapping("/deleteHeatTar/{id}")
    public String deleteHeatTar(@PathVariable("id") Integer id) {
        tariffsService.deleteHeatTar(id);
        return "redirect:/tariffs/heatTar";
    }

    @GetMapping("/editElTar/{id}")
    public String editElTar(@PathVariable("id") Integer id, Model model) {
        ElectricityTariffDto elTar = tariffsService.getElTar(id);
        List<AdminDto> allAdmin = operatorService.getAllAdmin();
        model.addAttribute("admin", allAdmin);
        model.addAttribute("elTar", elTar);
        return "/tariffs/edit-elTar";
    }

    @PostMapping("/editElTar")
    public String editElTar(@ModelAttribute("elTar") ElectricityTariffDto el,
                            @RequestParam("admi") Integer id) {
        tariffsService.updateElTar(el, id);
        return "redirect:/tariffs/elTar";

    }

    @GetMapping("/editGasTar/{id}")
    public String editGasTar(@PathVariable("id") Integer id, Model model) {
        GasTariffDto gasTar = tariffsService.getGasTar(id);
        List<AdminDto> allAdmin = operatorService.getAllAdmin();
        model.addAttribute("admin", allAdmin);
        model.addAttribute("gasTar", gasTar);
        return "/tariffs/edit-gasTar";
    }

    @PostMapping("/editGasTar")
    public String editElTar(@ModelAttribute("gasTar") GasTariffDto gas,
                            @RequestParam("admi") Integer id) {
        tariffsService.updateGasTar(gas, id);
        return "redirect:/tariffs/gasTar";

    }

    @GetMapping("/editHeatTar/{id}")
    public String editHeatTar(@PathVariable("id") Integer id, Model model) {
        HeatTariffDto heatTar = tariffsService.getHeatTar(id);
        List<AdminDto> allAdmin = operatorService.getAllAdmin();
        model.addAttribute("admin", allAdmin);
        model.addAttribute("heatTar", heatTar);
        return "/tariffs/edit-heatTar";
    }

    @PostMapping("/editHeatTar")
    public String editHeatTar(@ModelAttribute("heatTar") HeatTariffDto heat,
                            @RequestParam("admi") Integer id) {
        tariffsService.updateHeatTar(heat, id);
        return "redirect:/tariffs/heatTar";

    }

    @GetMapping("/addTar/{value}")
    public String addTar(Model model,
                         @PathVariable("value") String value) {
        List<AdminDto> admin = operatorService.getAllAdmin();
        model.addAttribute("value", value);
        model.addAttribute("admin", admin);
        return "tariffs/create-tariff";
    }

    @PostMapping("/addElTar")
    public String addElTar(@RequestParam("admi") Integer adminId,
                           @RequestParam("value") String value,
                           @RequestParam("tarr") BigDecimal tariff) {
        tariffsService.createTariff(adminId, value, tariff);
        return "redirect:/tariffs/" + value + "Tar";
    }

}
