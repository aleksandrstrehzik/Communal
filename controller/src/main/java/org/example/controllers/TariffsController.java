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
    @GetMapping("/editTar")
    public String editTar(@RequestParam("id") Integer id,
                            @RequestParam("value") String value,
                            Model model) {
        switch (value){
            case "el":
                ElectricityTariffDto elTar = tariffsService.getElTar(id);
                model.addAttribute("elTar", elTar);
                model.addAttribute("value", value);
                break;
            case "gas":
                GasTariffDto gasTar = tariffsService.getGasTar(id);
                model.addAttribute("gasTar", gasTar);
                model.addAttribute("value", value);
                break;
            case "heat":
                HeatTariffDto heatTar = tariffsService.getHeatTar(id);
                model.addAttribute("heatTar", heatTar);
                model.addAttribute("value", value);
                break;
        }
        return "tariffs/edit-tariff";
    }
}
