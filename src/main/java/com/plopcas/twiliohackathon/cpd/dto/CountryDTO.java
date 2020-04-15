package com.plopcas.twiliohackathon.cpd.dto;

public class CountryDTO {
    private String country;
    private String province;
    private TimelineDTO timeline;

    public CountryDTO() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TimelineDTO getTimeline() {
        return timeline;
    }

    public void setTimeline(TimelineDTO timeline) {
        this.timeline = timeline;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
