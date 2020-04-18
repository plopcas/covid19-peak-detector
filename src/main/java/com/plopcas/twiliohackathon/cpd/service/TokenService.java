package com.plopcas.twiliohackathon.cpd.service;

import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;
import com.twilio.jwt.accesstoken.Grant;
import com.twilio.jwt.accesstoken.SyncGrant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final String accountSid;
    private final String apiKey;
    private final String apiSecret;
    private final String chatServiceSid;
    private final String syncServiceSid;

    public TokenService(@Value("${twilio.accountSid}") String accountSid,
                        @Value("${twilio.apiKey}") String apiKey,
                        @Value("${twilio.apiSecret}") String apiSecret,
                        @Value("${twilio.chatServiceSid}") String chatServiceSid,
                        @Value("${twilio.syncServiceSid}") String syncServiceSid) {

        this.accountSid = accountSid;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.chatServiceSid = chatServiceSid;
        this.syncServiceSid = syncServiceSid;
    }

    public String getChatToken(String identity) {
        ChatGrant grant = new ChatGrant();
        grant.setServiceSid(chatServiceSid);
        return getToken(identity, grant);
    }

    public String getSyncToken(String identity) {
        SyncGrant grant = new SyncGrant();
        grant.setServiceSid(syncServiceSid);
        return getToken(identity, grant);
    }

    private String getToken(String identity, Grant grant) {
        AccessToken token = new AccessToken.Builder(accountSid, apiKey, apiSecret)
                .identity(identity)
                .grant(grant)
                .build();
        return token.toJwt();
    }
}
