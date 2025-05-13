package org.example.sii_task.models.fundraiser;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import org.example.sii_task.models.currency.Currency;
import org.example.sii_task.models.charityBox.CharityBox;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Fundraiser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal account;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;
    @OneToMany(mappedBy = "fundraiser")
    private List<CharityBox> boxes;

    public Fundraiser() {
    }

    public Fundraiser(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
        account = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<CharityBox> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<CharityBox> boxes) {
        this.boxes = boxes;
    }

    public void addBox(CharityBox charityBox) {
        boxes.add(charityBox);
    }

    public void donate(BigDecimal amount) {
        if (this.account == null) {
            this.account = amount;
        } else {
            this.account = account.add(amount);
        }

    }

    @Override
    public String toString() {
        return "Fundraiser{" +
                "name='" + name + '\'' +
                '}';
    }
}
