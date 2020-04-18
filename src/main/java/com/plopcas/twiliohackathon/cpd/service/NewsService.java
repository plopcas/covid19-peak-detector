package com.plopcas.twiliohackathon.cpd.service;

import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;
import com.plopcas.twiliohackathon.cpd.dto.NewsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class NewsService {
    private final RestTemplate restTemplate;

    @Value("${newsapi.apiKey}")
    private String apiKey;

    @Value("${newsapi.url.topHeadlines}")
    private String apiUrl;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NewsDTO getNews() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        LocalDate localDate = LocalDate.now();
        String from = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", "COVID")
                .queryParam("from", from)
                .queryParam("sortBy", "publishedAt")
                .queryParam("pageSize", 1)
                .queryParam("page", getRandomNumberInRange(1, 50))
                .queryParam("language", "en")
                .queryParam("apiKey", apiKey)
                .toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, NewsDTO.class).getBody();
    }

    private int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }
}
