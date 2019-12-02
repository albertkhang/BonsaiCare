package com.albertkhang.bonsaicare.objectClass;

public class MoneyTakeCareReportItem {
    private String supplyName;
    private int totalBill;
    private int totalMoney;
    private double percentage;

    public MoneyTakeCareReportItem() {
        totalBill = 0;
        totalMoney = 0;
        percentage = 0;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
