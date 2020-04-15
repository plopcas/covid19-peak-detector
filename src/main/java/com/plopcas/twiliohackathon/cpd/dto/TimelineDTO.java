package com.plopcas.twiliohackathon.cpd.dto;

import java.util.Map;

public class TimelineDTO {
    private Map<String, Integer> cases;
    private Map<String, Integer> death;
    private Map<String, Integer> recovered;

    public TimelineDTO() {
    }

    public Map<String, Integer> getCases() {
        return cases;
    }

    public void setCases(Map<String, Integer> cases) {
        this.cases = cases;
    }

    public Map<String, Integer> getDeath() {
        return death;
    }

    public void setDeath(Map<String, Integer> death) {
        this.death = death;
    }

    public Map<String, Integer> getRecovered() {
        return recovered;
    }

    public void setRecovered(Map<String, Integer> recovered) {
        this.recovered = recovered;
    }
}
