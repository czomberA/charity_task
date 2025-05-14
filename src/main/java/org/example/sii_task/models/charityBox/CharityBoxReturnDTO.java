package org.example.sii_task.models.charityBox;

public class CharityBoxReturnDTO {
    private String identifier;
    private boolean isAssigned;
    private boolean isEmpty;


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public CharityBoxReturnDTO() {
    }
}
