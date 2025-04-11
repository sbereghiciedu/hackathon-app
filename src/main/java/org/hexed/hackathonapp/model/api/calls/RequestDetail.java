package org.hexed.hackathonapp.model.api.calls;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDetail {
    @JsonProperty("Quantity")
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
