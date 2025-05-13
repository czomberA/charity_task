package org.example.sii_task.models.fundraiser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.sii_task.models.currency.Currency;

public class FundraiserDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be at most 255 characters")
    private String name;
    @NotBlank(message = "Currency required")
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return switch (currency) {
            case "EUR" -> Currency.EUR;
            case "GBP" -> Currency.GBP;
            case "PLN" -> Currency.PLN;
            default -> throw new IllegalArgumentException("Invalid currency: " + currency);
        };

    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
