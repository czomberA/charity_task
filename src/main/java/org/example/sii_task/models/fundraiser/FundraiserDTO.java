package org.example.sii_task.models.fundraiser;

import org.example.sii_task.models.currency.Currency;

public class FundraiserDTO {
    private String name;
    private String currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        switch (currency){
            case "EUR":
                return Currency.EUR;
            case "GBP":
                return Currency.GBP;
            case "PLN":
                return Currency.PLN;
            default:
                return Currency.EUR;
        }

    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
