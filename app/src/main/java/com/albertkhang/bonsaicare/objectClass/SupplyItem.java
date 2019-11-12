package com.albertkhang.bonsaicare.objectClass;

public class SupplyItem {
    private int id;
    private String supplyName;
    private String supplyUnit;
    private int total;


    public SupplyItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getSupplyUnit() {
        return supplyUnit;
    }

    public void setSupplyUnit(String supplyUnit) {
        this.supplyUnit = supplyUnit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
