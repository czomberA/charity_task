package org.example.sii_task.models.charityBox;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class CharityBoxCreateDTO {
    @Length(min = 1, max = 255)
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Only letters and numbers are allowed")
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
