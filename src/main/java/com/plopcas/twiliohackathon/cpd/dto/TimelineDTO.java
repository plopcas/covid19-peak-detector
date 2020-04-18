package com.plopcas.twiliohackathon.cpd.dto;

import java.util.Map;

public class TimelineDTO {
    private Map<String, Integer> cases;
    private Map<String, Integer> deaths;
    private Map<String, Integer> recovered;

    public TimelineDTO() {
    }

    public Map<String, Integer> getCases() {
        return cases;
    }

    public void setCases(Map<String, Integer> cases) {
        this.cases = cases;
    }

    public Map<String, Integer> getDeaths() {
        return deaths;
    }

    public void setDeaths(Map<String, Integer> deaths) {
        this.deaths = deaths;
    }

    public Map<String, Integer> getRecovered() {
        return recovered;
    }

    public void setRecovered(Map<String, Integer> recovered) {
        this.recovered = recovered;
    }
}
