package com.example.elbekjonumarovpaymenttask.service;

import com.example.elbekjonumarovpaymenttask.entity.Card;
import com.example.elbekjonumarovpaymenttask.payload.CardDto;
import com.example.elbekjonumarovpaymenttask.repository.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Service

public class CardService {

    private final CardRepository cardRepository;


    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> getALl() {
        return ok(cardRepository.findAll());
    }

    public ResponseEntity<?> getOne(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            return ok(optionalCard.get());
        } else {
            return notFound().build();
        }
    }

    public ResponseEntity<?> createCard(CardDto cardDto, HttpServletRequest request) {
        Card card = new Card(
                request.getUserPrincipal().getName(),
                generateCardId(),
                cardDto.getBalance() != null ? cardDto.getBalance() : 0D,
                new Date(),
                Date.from(LocalDateTime.now().plusYears(10).toInstant(ZoneOffset.UTC)),
                cardDto.isActive()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(cardRepository.save(card));

                )
    }
    public String generateCardId() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 1; i <= 16; i++) {
            cardNumber.append((int) (Math.random() * 10));
        }
        return cardNumber.toString();
    }
}
