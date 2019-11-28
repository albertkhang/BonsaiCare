package com.albertkhang.bonsaicare.objectClass;

public class BonsaiItem {
    private int id;
    private String bonsaiName;
    private String bonsaiType;
    private String bonsaiPlacementName;
    private String bonsaiDayPlanted;

    public BonsaiItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBonsaiName() {
        return bonsaiName;
    }

    public void setBonsaiName(String bonsaiName) {
        this.bonsaiName = bonsaiName;
    }

    public String getBonsaiType() {
        return bonsaiType;
    }

    public void setBonsaiType(String bonsaiType) {
        this.bonsaiType = bonsaiType;
    }

    public String getBonsaiPlacementName() {
        return bonsaiPlacementName;
    }

    public void setBonsaiPlacementName(String bonsaiPlacementName) {
        this.bonsaiPlacementName = bonsaiPlacementName;
    }

    public String getBonsaiDayPlanted() {
        return bonsaiDayPlanted;
    }

    public void setBonsaiDayPlanted(String bonsaiDayPlanted) {
        this.bonsaiDayPlanted = bonsaiDayPlanted;
    }
}
