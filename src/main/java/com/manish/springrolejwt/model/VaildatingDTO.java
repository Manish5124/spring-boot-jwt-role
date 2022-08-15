package com.manish.springrolejwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;

public class VaildatingDTO {
    @Id
    @JsonProperty
    private boolean validStatus;

    public VaildatingDTO() {
        this.validStatus = validStatus;
    }

    public boolean isValidStatus() {
        return validStatus;
    }

    public void setValidStatus(boolean validStatus) {
        this.validStatus = validStatus;
    }
}
