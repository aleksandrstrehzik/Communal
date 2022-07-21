package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.dto.*;
import org.example.entity.enums.Months;
import org.example.service.ConsumerService;
import org.example.service.OperatorService;
import org.example.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;
    private final OperatorService operatorService;
    private final ReportService reportService;

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
        System.out.println(operId);
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

    @GetMapping("/findConsInfo")
    public String getConsInfo() {
        return "/consumer/find-consumer";
    }

    @GetMapping("/showAllFind")
    public String showAllFind(@RequestParam("consName") String consName,
                              @RequestParam("consSurname") String consSurname, Model model) {
        List<ConsumerDto> allFindConsumers = consumerService.getAllFindConsumers(consName, consSurname);
        model.addAttribute("consumerDto", allFindConsumers);
        return "/consumer/find-consumer";
    }

    @GetMapping("/getInfo/{id}")
    public String showInfo(@PathVariable("id") Integer id,
                           Model model) {
        ConsumerDto consumerById = consumerService.getConsumerById(id);
        model.addAttribute("cons", consumerById);

        return "/consumer/info-consumer";
    }

    @GetMapping("/allReport")
    public String getAllReport(Model model, HttpServletRequest req,
                               @RequestParam("consId") Integer id) {
        String mon = getParam(req, "mon");
        Integer year1 = Integer.parseInt(getParam(req, "year1"));
        Months[] months = Months.values();
        model.addAttribute("cons", consumerService.getConsumerById(id));
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
        return "/consumer/info-consumer";
    }

    public static String getParam(HttpServletRequest req, String fieldName) {
        return Optional.ofNullable(req.getParameter(fieldName))
                .filter(StringUtils::isNotBlank)
                .orElse(null);
    }

}
