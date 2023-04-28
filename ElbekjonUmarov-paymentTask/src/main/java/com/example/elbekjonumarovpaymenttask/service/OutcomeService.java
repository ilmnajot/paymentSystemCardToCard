package com.example.elbekjonumarovpaymenttask.service;

import com.example.elbekjonumarovpaymenttask.entity.Card;
import com.example.elbekjonumarovpaymenttask.entity.Income;
import com.example.elbekjonumarovpaymenttask.entity.Outcome;
import com.example.elbekjonumarovpaymenttask.payload.OutcomeDto;
import com.example.elbekjonumarovpaymenttask.repository.CardRepository;
import com.example.elbekjonumarovpaymenttask.repository.IncomeRepository;
import com.example.elbekjonumarovpaymenttask.repository.OutcomeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.status;

@Service
public class OutcomeService {

    @Value("2")
    private Double commissionPercent;
    private final OutcomeRepository outcomeRepository;
    private final IncomeRepository incomeRepository;
    private final CardRepository cardRepository;

    public OutcomeService(OutcomeRepository outcomeRepository, IncomeRepository incomeRepository, CardRepository cardRepository) {
        this.outcomeRepository = outcomeRepository;
        this.incomeRepository = incomeRepository;
        this.cardRepository = cardRepository;
    }

    public HttpEntity<?> getOne(Long outcomeId, HttpServletRequest request) {
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(outcomeId);
        if (!optionalOutcome.isPresent())
             return new ResponseEntity<>("outcome not found", HttpStatus.NOT_FOUND);
        Outcome outcome = optionalOutcome.get();
        Card card = cardRepository.getReferenceById(outcome.getFrom().getId());
        if (!card.getUsername().equals(request.getUserPrincipal().getName()))
            return badRequest().build();
        return ok(outcome);
    }
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<Outcome> allByFrom_id = outcomeRepository.findAllByFrom_Username(request.getUserPrincipal().getName());
        return ok(allByFrom_id);
    }
    public ResponseEntity<?> transferMoney(OutcomeDto outcomeDto, HttpServletRequest request) {
        if (outcomeDto.getFrom().equals(outcomeDto.getTo())) return badRequest().body("card numbers are not same");
        Optional<Card> optionalFromCard = cardRepository.findById(outcomeDto.getFrom());
        if (!optionalFromCard.isPresent()) return status(HttpStatus.NOT_FOUND).body("card not found");
        Card fromCard = optionalFromCard.get();
        if (!fromCard.getUsername().equals(request.getUserPrincipal().getName()))
            return status(HttpStatus.BAD_REQUEST).body("you are not able to transfer money from this card");
        Optional<Card> optionalToCard = cardRepository.findById(outcomeDto.getTo());
        if (!optionalToCard.isPresent()) return status(HttpStatus.NOT_FOUND).body("card not found ");
        if (dto.getAmount() < 1000) return badRequest().body("minimum amount should be 1000 ");
        double amount = dto.getAmount() * (commissionPercent / 100) + outcomeDto.getAmount();
        if (fromCard.getBalance() < amount) return badRequest().body("there is no enough money");
        fromCard.setBalance(fromCard.getBalance() - amount);
        cardRepository.save(fromCard);
        Card toCard = optionalToCard.get();
        toCard.setBalance(toCard.getBalance() + outcomeDto.getAmount());
        cardRepository.save(toCard);
        incomeRepository.save(new Income(fromCard, toCard, outcomeDto.getAmount(), new Date()));
        outcomeRepository.save(new Outcome(fromCard, toCard, outcomeDto.getAmount(), new Date(), commissionPercent));
        return status(HttpStatus.CREATED).build();
    }
}

