package com.plopcas.twiliohackathon.cpd.controller;

import com.github.javafaker.Faker;
import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;
import com.plopcas.twiliohackathon.cpd.service.TokenService;
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
    private final TokenService tokenService;
    private final Faker faker;

    public WelcomeController(DataService dataService, TokenService tokenService) {
        this.dataService = dataService;
        this.tokenService = tokenService;
        faker = new Faker();
    }

    @GetMapping("/")
    public String getWelcome(Model model) {
        List<CountryDTO> historicalData = dataService.fetch();

        List<String> countries = historicalData.stream().map(x -> CountryUtils.buildCountryString(x)).sorted().collect(Collectors.toList());
        model.addAttribute("countries", countries);

        String username = faker.gameOfThrones().character();
        model.addAttribute("chatUsername", username);
        model.addAttribute("chatToken", tokenService.getChatToken(username));
        model.addAttribute("syncToken", tokenService.getSyncToken(username));

        return "index";
    }
}
