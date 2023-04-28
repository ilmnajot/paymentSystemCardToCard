package com.example.elbekjonumarovpaymenttask.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OutcomeDto {
    @NotBlank(message = "Enter the debit card id")
    private String from;    // from_card_id
    @NotBlank(message = "Enter the card id for the payment")
    private String to;      // to_card_id
    @NotNull(message = "Enter the amount of funds for the expense")
    private Double amount;

}
