package org.example.sii_task.models.collected;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class CollectedDTO {
    @NotBlank
    private String charityBox;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;
    @NotBlank
    private String currency;

    public String getCharityBox() {
        return charityBox;
    }

    public void setCharityBox(String charityBox) {
        this.charityBox = charityBox;
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
