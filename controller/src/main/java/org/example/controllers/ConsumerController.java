package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.ConsumerService;
import org.example.service.OperatorService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;
    private final OperatorService operatorService;

    @GetMapping("/{pageNumber}")
    public String getPaginatedConsumers(@PathVariable(value = "pageNumber") int pageNumber, @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir, Model model) {
        Page<ConsumerDto> page = consumerService.findAllPaginated(pageNumber, sortField, sortDir);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<ConsumerDto> cons = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("consumerDto", cons);
        return "/consumer/all-consumers";
    }

    @GetMapping()
    public String showHorsesFirstPage(Model model) {
        return getPaginatedConsumers(1, "id", "asc", model);
    }

    @GetMapping("/editConsumer/{id}")
    public String editConsumer(@PathVariable("id") Integer id, Model model) {
        ConsumerDto cons = consumerService.getConsumerById(id);
        List<OperatorDto> operDto = operatorService.getAllOperators();
        List<ElectricityTariffDto> elTar = operatorService.getAllElTariffs();
        List<GasTariffDto> gasTar = operatorService.getAllGasTariffs();
        List<HeatTariffDto> heatTar = operatorService.getAllHeatTariffs();
        model.addAttribute("operDto", operDto);
        model.addAttribute("elTar", elTar);
        model.addAttribute("gasTar", gasTar);
        model.addAttribute("heatTar", heatTar);
        model.addAttribute("consumer", cons);
        return "/consumer/edit-consumer";
    }

    @PostMapping("/editConsumer")
    public String editConsumer(@RequestParam("operId") Integer operId,
                               @RequestParam("elTar") Integer elId,
                               @RequestParam("gasTar") Integer gasId,
                               @RequestParam("heatTar") Integer heatId,
                               @ModelAttribute("consumer") ConsumerDto consumerDto) {
        consumerService.editConsumer(consumerDto, operId, elId, gasId, heatId);
        return "redirect:/consumer";
    }

    @GetMapping("/deleteConsumer/{id}")
    public String deleteConsumer(@PathVariable("id") Integer id) {
        consumerService.deleteConsumer(id);
        return "redirect:/consumer";
    }

    @GetMapping("/addConsumer")
    public String addConsumer(@ModelAttribute("consumer") ConsumerDto cons, Model model) {
        List<OperatorDto> operDto = operatorService.getAllOperators();
        List<ElectricityTariffDto> elTar = operatorService.getAllElTariffs();
        List<GasTariffDto> gasTar = operatorService.getAllGasTariffs();
        List<HeatTariffDto> heatTar = operatorService.getAllHeatTariffs();
        model.addAttribute("operDto", operDto);
        model.addAttribute("elTar", elTar);
        model.addAttribute("gasTar", gasTar);
        model.addAttribute("heatTar", heatTar);
        return "/consumer/create-consumer";
    }

    @PostMapping("/createConsumer")
    public String createConsumer(@RequestParam("operId") Integer operId,
                                 @RequestParam("elTar") Integer elId,
                                 @RequestParam("gasTar") Integer gasId,
                                 @RequestParam("heatTar") Integer heatId,
                                 @ModelAttribute("consumer") ConsumerDto consumerDto) {
        consumerService.createConsumer(consumerDto, operId, elId, gasId, heatId);
        return "redirect:/consumer";
    }
}
