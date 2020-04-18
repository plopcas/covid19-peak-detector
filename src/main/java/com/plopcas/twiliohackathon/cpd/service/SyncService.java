package com.plopcas.twiliohackathon.cpd.service;

import com.twilio.Twilio;
import com.twilio.rest.sync.v1.service.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SyncService {
    private static Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private final NewsService newsService;

    private Document document;

    public SyncService(@Value("${twilio.accountSid}") String accountSid,
                       @Value("${twilio.authToken}") String authToken,
                       @Value("${twilio.syncServiceSid}") String syncServiceSid,
                       NewsService newsService) {
        this.newsService = newsService;

        Twilio.init(accountSid, authToken);
        try {
            document = Document.fetcher(syncServiceSid, "news").fetch();
        } catch (Exception e) {
            document = Document.creator(syncServiceSid).setData(new HashMap<String, Object>()).setUniqueName("news").create();
        }
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void postNews() {
        Map<String, Object> data = new HashMap<>();
        data.put("news", newsService.getNews().getArticles().get(0));

        Document.updater(document.getServiceSid(), document.getSid())
                .setData(data)
                .update();
        log.info("Updated news sync document");
    }

}
