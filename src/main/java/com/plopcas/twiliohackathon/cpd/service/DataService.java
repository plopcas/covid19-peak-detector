package com.plopcas.twiliohackathon.cpd.service;

import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;
import com.plopcas.twiliohackathon.cpd.utils.CountryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<String> getCountries() {
        return this.fetch().stream().map(x -> CountryUtils.buildCountryString(x)).sorted().collect(Collectors.toList());
    }

    public Map<String, Integer> getActiveCases(String countryAndProvince) {
        String[] countryAndProvinceArray = countryAndProvince.split(" - ");

        String country = countryAndProvinceArray[0];
        String province = countryAndProvinceArray.length > 1 ? countryAndProvinceArray[1] : null;

        Optional<CountryDTO> optionalCountryDTO = this.fetch()
                .stream()
                .filter(x -> country.equalsIgnoreCase(x.getCountry()) && (province == null || province.equalsIgnoreCase(x.getProvince())))
                .findFirst();

        CountryDTO countryDTO = optionalCountryDTO.get();
        Map<String, Integer> activeCases = new TreeMap<>();
        Map<String, Integer> cases = countryDTO.getTimeline().getCases();
        Map<String, Integer> deaths = countryDTO.getTimeline().getDeaths();
        Map<String, Integer> recovered = countryDTO.getTimeline().getRecovered();

        cases.forEach((key, value) -> activeCases.put(key, value -
                ((deaths != null && deaths.get(key) != null ? deaths.get(key) : 0)
                        + (recovered != null && recovered.get(key) != null ? recovered.get(key) : 0))));

        return activeCases;
    }

    private List<CountryDTO> fetch() {
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
