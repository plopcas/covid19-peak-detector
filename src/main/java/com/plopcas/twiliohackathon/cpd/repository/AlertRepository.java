package com.plopcas.twiliohackathon.cpd.repository;

import com.plopcas.twiliohackathon.cpd.model.Alert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlertRepository {
    public List<Alert> getAlertsByCountry(String country) {
        return null;
    }

    public void delete(Alert alert) {
    }
}
