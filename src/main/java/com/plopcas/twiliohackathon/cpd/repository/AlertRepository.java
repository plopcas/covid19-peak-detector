package com.plopcas.twiliohackathon.cpd.repository;

import com.plopcas.twiliohackathon.cpd.model.Alert;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
public interface AlertRepository extends CrudRepository<Alert, String> {

    public List<Alert> findByCountry(String country);
    public void delete(Alert alert);
}
