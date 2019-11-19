package com.albertkhang.bonsaicare.objectClass;

public class SupplyBillItem {
    private int id;
    private String supplyName;
    private String addressBought;
    private int totalSupplies;
    private String dayBought;
    private int totalMoney;

    public SupplyBillItem() {
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

    public String getAddressBought() {
        return addressBought;
    }

    public void setAddressBought(String addressBought) {
        this.addressBought = addressBought;
    }

    public int getTotalSupplies() {
        return totalSupplies;
    }

    public void setTotalSupplies(int totalSupplies) {
        this.totalSupplies = totalSupplies;
    }

    public String getDayBought() {
        return dayBought;
    }

    public void setDayBought(String dayBought) {
        this.dayBought = dayBought;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
