package org.example.sii_task.models.charityBox;

import jakarta.validation.constraints.NotEmpty;

public class CharityBoxAssignDTO {
    @NotEmpty
    private String fundraiser;

    public String getFundraiser() {
        return fundraiser;
    }

    public void setFundraiser(String fundraiser) {
        this.fundraiser = fundraiser;
    }
}
