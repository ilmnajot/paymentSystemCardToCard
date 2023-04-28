package com.example.elbekjonumarovpaymenttask.controller;

import com.example.elbekjonumarovpaymenttask.service.IncomeService;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/{cardId}")
    public HttpEntity<?>getIncome(@PathVariable Long cardId, HttpServletRequest request){
        return incomeService.getOne(cardId, request);
    }
}
