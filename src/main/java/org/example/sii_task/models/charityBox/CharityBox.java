package org.example.sii_task.models.charityBox;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import org.example.sii_task.exception.BadCurrencyException;
import org.example.sii_task.models.collected.Collected;
import org.example.sii_task.models.currency.Currency;
import org.example.sii_task.models.fundraiser.Fundraiser;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class CharityBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;
    @ManyToOne()
    @JoinColumn
    private Fundraiser fundraiser;
    @OneToMany(mappedBy = "charityBox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collected> collections;

    public CharityBox() {
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Fundraiser getFundraiser() {
        return fundraiser;
    }

    public void setFundraiser(Fundraiser fundraiser) {
        this.fundraiser = fundraiser;
        fundraiser.addBox(this);
    }

    public List<Collected> getCollections() {
        return collections;
    }

    public void setCollections(List<Collected> collections) {
        this.collections = collections;
    }

    public void donate(BigDecimal amount, String currency) {
        Collected collection;
        if (collections.isEmpty()) {
            CreateNewCollection(amount, currency);
            return;
        }
        for (Collected collected : collections) {
            if (collected.getCurrency().name().equals(currency)) {
                collection = collected;
                collection.donate(amount);
                return;
            }
        }
        CreateNewCollection(amount, currency);
    }

    private void CreateNewCollection (BigDecimal amount, String currency){
        Collected newCollection = new Collected();
        switch (currency){
            case "EUR":
                newCollection.setCurrency(Currency.EUR);
                break;
            case "GBP":
                newCollection.setCurrency(Currency.GBP);
                break;
            case "PLN":
                newCollection.setCurrency(Currency.PLN);
                break;
            default: throw new BadCurrencyException("Currency not accepted");
        }
        newCollection.setAmount(amount);
        collections.add(newCollection);
        newCollection.setCharityBox(this);
        System.out.println("NC " + newCollection);
    }

    @Override
    public String toString() {
        return "CharityBox{" +
                "identifier='" + identifier + '\'' +
                ", collections=" + collections +
                '}';
    }
}
