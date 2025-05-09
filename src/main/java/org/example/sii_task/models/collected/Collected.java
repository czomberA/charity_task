package org.example.sii_task.models.collected;

import jakarta.persistence.*;
import org.example.sii_task.models.currency.Currency;
import org.example.sii_task.models.charityBox.CharityBox;

@Entity
public class Collected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "charitybox_id")
    private CharityBox charityBox;
    private double amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;
    public Collected() {
    }


    public void donate(double donation){
        this.amount = amount + donation;
    }

    public CharityBox getCharityBox() {
        return charityBox;
    }

    public void setCharityBox(CharityBox charityBox) {
        this.charityBox = charityBox;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Collected{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }

}
