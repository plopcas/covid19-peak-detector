package com.plopcas.twiliohackathon.cpd.repository;

import com.plopcas.twiliohackathon.cpd.model.Alert;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertRepository extends CrudRepository<Alert, String> {
    @EnableScan
    public List<Alert> findAll();
    public void delete(Alert alert);
}
