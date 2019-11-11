package com.albertkhang.bonsaicare.ObjectClass;

public class PlacementItem {
    int id;
    String placcementName;

    public PlacementItem() {
    }

    public PlacementItem(int id, String placcementName) {
        this.id = id;
        this.placcementName = placcementName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaccementName() {
        return placcementName;
    }

    public void setPlaccementName(String placcementName) {
        this.placcementName = placcementName;
    }
}
