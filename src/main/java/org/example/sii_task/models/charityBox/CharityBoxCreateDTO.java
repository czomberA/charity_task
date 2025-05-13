package org.example.sii_task.models.charityBox;

import org.hibernate.validator.constraints.Length;

public class CharityBoxCreateDTO {
    @Length(min = 1, max = 255)
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
