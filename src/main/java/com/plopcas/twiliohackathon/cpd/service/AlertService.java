package com.plopcas.twiliohackathon.cpd.service;

import com.plopcas.twiliohackathon.cpd.model.Alert;
import com.plopcas.twiliohackathon.cpd.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    final private AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void create(String phone, String country) {
        Alert alert = new Alert();
        alert.setCountry(country);
        alert.setPhone(phone);
        alertRepository.save(alert);
    }

    public void delete(Alert alert) {
        alertRepository.delete(alert);
    }

    public List<Alert> findByCountry(String country) {
        return alertRepository.findByCountry(country);
    }
}
