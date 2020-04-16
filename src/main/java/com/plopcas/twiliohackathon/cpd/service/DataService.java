package com.plopcas.twiliohackathon.cpd.service;

import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataService {
    private final RestTemplate restTemplate;

    private List<CountryDTO> historicalData;

    private LocalDateTime fetchDate;

    @Value("${url}")
    private String apiUrl;

    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CountryDTO> fetch() {
        if (historicalData == null
                || (fetchDate != null
                && LocalDateTime.now().isAfter(fetchDate.plusHours(12l)))) {

            HttpHeaders headers = new HttpHeaders();
            HttpEntity entity = new HttpEntity(headers);

            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .toUriString();

            historicalData = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<CountryDTO>>() {
            }).getBody();

            fetchDate = LocalDateTime.now();
        }

        return historicalData;
    }
}
