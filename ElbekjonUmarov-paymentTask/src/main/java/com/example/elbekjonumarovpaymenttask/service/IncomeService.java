package com.example.elbekjonumarovpaymenttask.service;

import com.example.elbekjonumarovpaymenttask.entity.Card;
import com.example.elbekjonumarovpaymenttask.entity.Income;
import com.example.elbekjonumarovpaymenttask.repository.CardRepository;
import com.example.elbekjonumarovpaymenttask.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CardRepository cardRepository;

    public IncomeService(IncomeRepository incomeRepository, CardRepository cardRepository) {
        this.incomeRepository = incomeRepository;
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> getOne(@PathVariable Long cardId, HttpServletRequest request) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (!optionalCard.isPresent()) return notFound().build();
        Card card = optionalCard.get();
        String name = request.getUserPrincipal().getName();
        if (card.getUsername().equals(name)) return status(401).build();
        List<Income> allByToCardIdId = incomeRepository.getAllByToCardId_id(cardId);
        return ok(allByToCardIdId);
    }
}
