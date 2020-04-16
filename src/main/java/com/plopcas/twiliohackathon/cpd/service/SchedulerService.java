package com.plopcas.twiliohackathon.cpd.service;

import com.plopcas.twiliohackathon.cpd.dto.TimelineDTO;
import com.plopcas.twiliohackathon.cpd.model.Alert;
import com.plopcas.twiliohackathon.cpd.service.AlertService;
import com.plopcas.twiliohackathon.cpd.utils.CountryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.reverseOrder;

/**
 * Service to check the data for each country every day and trigger the alerts if needed.
 */
@Service
public class SchedulerService {
    private static Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private final DataService dataService;
    private final SmsService smsService;
    private final AlertService alertService;

    public SchedulerService(DataService dataService, SmsService smsService, AlertService alertService) {
        this.dataService = dataService;
        this.smsService = smsService;
        this.alertService = alertService;
    }

    /**
     * Detect peak and send alerts every 12 hours. Naively, it deletes the alert afterwards.
     *
     * @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
     */
    @Scheduled(fixedRate = 30000)
    public void scheduleFixedRateTask() {
        log.info("Running scheduled job to detect peaks");
        List<String> countries = detectPeak();
        for (String country : countries) {
            log.info("Peak detected for " + country + " - Sending alerts!");
            // fetch all phone numbers that signed up for the country
            List<Alert> alertsByCountry = alertService.findByCountry(country);

            // send alert to each of those phone numbers
            for (Alert alert : alertsByCountry) {
                smsService.sendAlertSms(alert.getPhone(), country);
                alertService.delete(alert);
            }
        }
    }

    /**
     * Attempts to detect the peak for each country.
     *
     * @return list of countries that have reached the peak
     */
    public List<String> detectPeak() {
        List<String> countries = new ArrayList<>();

        dataService.fetch().stream().forEach(countryDTO -> {
            TimelineDTO timeline = countryDTO.getTimeline();
            Map<String, Integer> cases = timeline.getCases();
            Set<String> keysSet = cases.keySet();
            List<String> keysList = new ArrayList<>(keysSet);
            Collections.sort(keysList, reverseOrder());
            Integer today = cases.get(keysList.get(0));
            Integer yesterday = cases.get(keysList.get(1));
            Integer dayBeforeYesterday = cases.get(keysList.get(2));
            if (today < yesterday && today < dayBeforeYesterday) {
                countries.add(CountryUtils.buildCountryString(countryDTO));
            }
        });

        return countries;
    }
}
