package org.hexed.hackathonapp.model.api.calls;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDetail {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Quantity")
    private int quantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RequestDetail{" +
                "type='" + type + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
