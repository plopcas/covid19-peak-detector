package com.plopcas.twiliohackathon.cpd.service;

import com.plopcas.twiliohackathon.cpd.dto.AlertFormDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to send alerts to a given phone number.
 */
@Service
public class AlertService {

    private String accountSid;
    private String authToken;
    private String fromPhone;

    public AlertService(@Value("${twilio.accountSid}") String accountSid,
                        @Value("${twilio.authToken}") String authToken,
                        @Value("${twilio.fromPhone}") String fromPhone) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromPhone = fromPhone;
        Twilio.init(accountSid, authToken);
    }


    public void sendConfirmationSms(AlertFormDTO alertFormDTO) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(alertFormDTO.getPhone()),
                new com.twilio.type.PhoneNumber(fromPhone),
                "COVID-19 Peak Detector - Your alert has been created, thanks!")
                .create();
    }

    public void sendAlertSms(String phone, String country) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(phone),
                new com.twilio.type.PhoneNumber(fromPhone),
                "COVID-19 Peak Detector - We are happy to inform you that the peak has been reached in "
                        + country + "! Thanks for using our service :)")
                .create();
    }
}
