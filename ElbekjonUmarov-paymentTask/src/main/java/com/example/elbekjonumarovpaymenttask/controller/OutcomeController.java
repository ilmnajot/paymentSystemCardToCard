package com.example.elbekjonumarovpaymenttask.controller;

import com.example.elbekjonumarovpaymenttask.payload.OutcomeDto;
import com.example.elbekjonumarovpaymenttask.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    private final OutcomeService service;

    @Autowired
    public OutcomeController(OutcomeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        return service.getAll(request);
    }

    @GetMapping("/{outcomeId}")
    public ResponseEntity<?> getOne(@PathVariable Long outcomeId, HttpServletRequest request) {
        return ResponseEntity.ok(service.getOne(outcomeId, request));
    }

    @PostMapping
    public ResponseEntity<?> transfer(@Valid @RequestBody OutcomeDto outcomeDto, HttpServletRequest request) {
        return service.transferMoney(outcomeDto, request);
    }

}
