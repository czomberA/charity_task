package org.example.sii_task.models.fundraiser;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import org.example.sii_task.models.currency.Currency;
import org.example.sii_task.models.charityBox.CharityBox;

import java.io.Serializable;
import java.util.List;

@Entity
public class Fundraiser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double account;
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
        account = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
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

    public void donate(double amount) {
        this.account += amount;
    }

    @Override
    public String toString() {
        return "Fundraiser{" +
                "name='" + name + '\'' +
                '}';
    }
}
