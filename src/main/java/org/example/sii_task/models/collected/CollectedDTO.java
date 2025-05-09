package org.example.sii_task.models.collected;

public class CollectedDTO {
    private String charityBox;
    private double amount;
    private String currency;

    public String getCharityBox() {
        return charityBox;
    }

    public void setCharityBox(String charityBox) {
        this.charityBox = charityBox;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
