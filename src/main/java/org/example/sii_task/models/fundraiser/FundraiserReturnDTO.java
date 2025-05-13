package org.example.sii_task.models.fundraiser;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public class FundraiserReturnDTO {
    private String name;
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal amount;
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
