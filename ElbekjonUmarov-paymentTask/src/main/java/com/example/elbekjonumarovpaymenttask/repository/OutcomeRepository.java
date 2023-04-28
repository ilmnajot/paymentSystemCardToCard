package com.example.elbekjonumarovpaymenttask.repository;

import com.example.elbekjonumarovpaymenttask.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

    List<Outcome> findAllByFrom_Username(String fromUsername);
}
