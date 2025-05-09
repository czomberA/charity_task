package org.example.sii_task.models.charityBox;

import org.example.sii_task.models.fundraiser.FundraiserBoxAssignedDTO;

public class CharityBoxAssignedDTO {
    private String identifier;
    private FundraiserBoxAssignedDTO fundraiser;

    public FundraiserBoxAssignedDTO getFundraiser() {
        return fundraiser;
    }

    public void setFundraiser(FundraiserBoxAssignedDTO fundraiser) {
        this.fundraiser = fundraiser;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
