package com.albertkhang.bonsaicare.objectClass;

public class PlacementItem {
    int id;
    String placementName;
    int totalBonsai;

    public PlacementItem() {
    }

    public PlacementItem(int id, String placementName) {
        this.id = id;
        this.placementName = placementName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlacementName() {
        return placementName;
    }

    public void setPlacementName(String placementName) {
        this.placementName = placementName;
    }

    public int getTotalBonsai() {
        return totalBonsai;
    }

    public void setTotalBonsai(int totalBonsai) {
        this.totalBonsai = totalBonsai;
    }
}
