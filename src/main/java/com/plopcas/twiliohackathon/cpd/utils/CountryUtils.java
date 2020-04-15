package com.plopcas.twiliohackathon.cpd.utils;

import com.plopcas.twiliohackathon.cpd.dto.CountryDTO;

public class CountryUtils {
    public static String buildCountryString(CountryDTO countryDTO) {
        return countryDTO.getCountry().trim() + (countryDTO.getProvince() != null ? " - " + countryDTO.getProvince() : "");
    }
}
