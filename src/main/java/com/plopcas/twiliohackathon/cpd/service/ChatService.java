package com.plopcas.twiliohackathon.cpd.service;

import com.twilio.Twilio;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final String accountSid;
    private final String apiKey;
    private final String apiSecret;
    private final String serviceSid;

    public ChatService(@Value("${twilio.accountSid}") String accountSid,
                       @Value("${twilio.apiKey}") String apiKey,
                       @Value("${twilio.apiSecret}") String apiSecret,
                       @Value("${twilio.serviceSid}") String serviceSid) {

        this.accountSid = accountSid;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.serviceSid = serviceSid;
    }

    public String getToken(String identity) {
        ChatGrant grant = new ChatGrant();
        grant.setServiceSid(serviceSid);

        AccessToken token = new AccessToken.Builder(accountSid, apiKey, apiSecret)
                .identity(identity)
                .grant(grant)
                .build();

        return token.toJwt();
    }
}
