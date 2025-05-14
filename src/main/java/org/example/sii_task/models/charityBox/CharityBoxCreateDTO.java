package org.example.sii_task.models.charityBox;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class CharityBoxCreateDTO {
    @Length(min = 1, max = 255)
    @Pattern(regexp = "[a-zA-Z ]+", message = "Only letters and spaces are allowed")
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
