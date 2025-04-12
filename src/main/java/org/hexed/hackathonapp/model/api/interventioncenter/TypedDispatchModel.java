package org.hexed.hackathonapp.model.api.interventioncenter;

public class TypedDispatchModel extends DispatchModel {

    private String type;

    public TypedDispatchModel(String sourceCounty, String sourceCity, String targetCounty, String targetCity, int quantity, String type) {
        super(sourceCounty, sourceCity, targetCounty, targetCity, quantity);
        this.type = type;
    }

    public TypedDispatchModel(DispatchModel dispatch, String type) {
        this(dispatch.getSourceCounty(), dispatch.getSourceCity(), dispatch.getTargetCounty(), dispatch.getTargetCity(), dispatch.getQuantity(), type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypedDispatchModel{" + super.toString() +
                ", type='" + type + '\'' +
                '}';
    }
}
