package com.plopcas.twiliohackathon.cpd.controller;

import com.github.javafaker.Faker;
import com.plopcas.twiliohackathon.cpd.service.DataService;
import com.plopcas.twiliohackathon.cpd.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("countries", dataService.getCountries());

        String username = faker.gameOfThrones().character();
        model.addAttribute("chatUsername", username);
        model.addAttribute("chatToken", tokenService.getChatToken(username));
        model.addAttribute("syncToken", tokenService.getSyncToken(username));

        return "index";
    }
}
