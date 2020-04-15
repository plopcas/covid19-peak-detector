package com.plopcas.twiliohackathon.cpd.controller;

import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;
import com.plopcas.twiliohackathon.cpd.service.DataService;
import com.plopcas.twiliohackathon.cpd.utils.CountryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Welcome page controller.
 */
@Controller
public class WelcomeController {
    private final DataService dataService;

    public WelcomeController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String getWelcome(Model model) {
        // get data from COVID-19 API
        List<CountryDTO> historicalData = dataService.fetch();

        List<String> countries = historicalData.stream().map(x -> CountryUtils.buildCountryString(x)).sorted().collect(Collectors.toList());
        model.addAttribute("countries", countries);

        // return view
        return "index";
    }
}
