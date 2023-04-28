package com.example.elbekjonumarovpaymenttask.repository;

import com.example.elbekjonumarovpaymenttask.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    public boolean existsByUsernameAndId(String username, Long id);
}
