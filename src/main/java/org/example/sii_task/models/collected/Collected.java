package org.example.sii_task.models.collected;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import org.example.sii_task.models.currency.Currency;
import org.example.sii_task.models.charityBox.CharityBox;

import java.math.BigDecimal;

@Entity
public class Collected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "charitybox_id")
    private CharityBox charityBox;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;
    public Collected() {
    }


    public void donate(BigDecimal donation){
        this.amount = amount.add(donation);
    }

    public CharityBox getCharityBox() {
        return charityBox;
    }

    public void setCharityBox(CharityBox charityBox) {
        this.charityBox = charityBox;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
