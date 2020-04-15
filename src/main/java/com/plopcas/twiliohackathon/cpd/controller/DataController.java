package com.plopcas.twiliohackathon.cpd.controller;

import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;
import com.plopcas.twiliohackathon.cpd.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DataController {
    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/countries/{countryAndProvince}")
    public CountryDTO getCountry(@PathVariable String countryAndProvince) {
        String[] countryAndProvinceArray = countryAndProvince.split(" - ");

        String country = countryAndProvinceArray[0];
        String province = countryAndProvinceArray.length > 1 ? countryAndProvinceArray[1] : null;

        Optional<CountryDTO> countryDTO = dataService.fetch()
                .stream()
                .filter(x -> country.equalsIgnoreCase(x.getCountry()) && (province == null || province.equalsIgnoreCase(x.getProvince())))
                .findFirst();

        return countryDTO.get();
    }
}
