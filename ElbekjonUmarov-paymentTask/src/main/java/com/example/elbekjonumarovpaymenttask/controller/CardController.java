package com.example.elbekjonumarovpaymenttask.controller;

import com.example.elbekjonumarovpaymenttask.entity.Card;
import com.example.elbekjonumarovpaymenttask.payload.CardDto;
import com.example.elbekjonumarovpaymenttask.service.CardService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(cardService.getALl());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        return cardService.getOne(id);
    }

    @PostMapping
    public HttpEntity<?> saveCard(@RequestBody CardDto cardDto, HttpServletRequest request) {
        return cardService.createCard(cardDto, request);
    }


}
