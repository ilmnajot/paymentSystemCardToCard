package com.example.elbekjonumarovpaymenttask.repository;

import com.example.elbekjonumarovpaymenttask.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> getAllByToCardId_id(Long toCardId_id);
}
