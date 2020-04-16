package com.plopcas.twiliohackathon.cpd.controller;

import com.plopcas.twiliohackathon.cpd.dto.AlertFormDTO;
import com.plopcas.twiliohackathon.cpd.service.AlertService;
import com.plopcas.twiliohackathon.cpd.service.SmsService;
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
    private final SmsService smsService;
    private final AlertService alertService;

    public AlertController(SmsService smsService, AlertService alertService) {
        this.smsService = smsService;
        this.alertService = alertService;
    }

    @PostMapping("/alerts")
    public ResponseEntity<?> postAlerts(@RequestBody AlertFormDTO alertFormDTO) {
        if (alertFormDTO == null || alertFormDTO.getCountry() == null) {
            return ResponseEntity.badRequest().build();
        } else if (!isValidPhoneNumber(alertFormDTO.getPhone())) {
            return ResponseEntity.badRequest().build();
        }

        alertService.create(alertFormDTO.getPhone(), alertFormDTO.getCountry());

        smsService.sendConfirmationSms(alertFormDTO.getPhone());

        return ResponseEntity.ok().build();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
        Matcher m = p.matcher(phoneNumber);
        return (m.find() && m.group().equals(phoneNumber));
    }
}
