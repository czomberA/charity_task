package org.example.sii_task.models.fundraiser;

public class FundraiserBoxAssignedDTO {
    private String name;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FundraiserBoxAssignedDTO(String name, String currency) {
        this.name = name;
        this.currency = currency;
    }
}
