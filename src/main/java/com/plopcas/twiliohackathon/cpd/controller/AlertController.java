package com.plopcas.twiliohackathon.cpd.controller;

import com.plopcas.twiliohackathon.cpd.dto.AlertFormDTO;
import com.plopcas.twiliohackathon.cpd.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller to create new alerts.
 */
@RestController
public class AlertController {


    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/alerts")
    public ResponseEntity<?> postAlerts(@RequestBody AlertFormDTO alertFormDTO) {
        if (alertFormDTO == null || alertFormDTO.getCountry() == null) {
            return ResponseEntity.badRequest().build();
        } else if (!isValidPhoneNumber(alertFormDTO.getPhone())) {
            return ResponseEntity.badRequest().build();
        }

        // TODO store phone and country

        // send confirmation SMS
        alertService.sendConfirmationSms(alertFormDTO);

        return ResponseEntity.ok().build();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
        Matcher m = p.matcher(phoneNumber);
        return (m.find() && m.group().equals(phoneNumber));
    }
}
