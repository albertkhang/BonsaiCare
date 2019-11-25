package com.albertkhang.bonsaicare.objectClass;

public class ScheduleItem {
    private int id;
    private String scheduleName;
    private String bonsaiName;
    private String dayCreated;
    private String dayTakeCare;
    private String timeTakeCare;
    private String bonsaiPlace;
    private String supply;
    private int amount;
    private String note;

    private boolean haveNote;
    private boolean isTicked;

    public ScheduleItem() {
        note = "";

        haveNote = false;
        isTicked = false;
    }

    public String getTimeTakeCare() {
        return timeTakeCare;
    }

    public void setTimeTakeCare(String timeTakeCare) {
        this.timeTakeCare = timeTakeCare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getBonsaiName() {
        return bonsaiName;
    }

    public void setBonsaiName(String bonsaiName) {
        this.bonsaiName = bonsaiName;
    }

    public String getDayCreated() {
        return dayCreated;
    }

    public void setDayCreated(String dayCreated) {
        this.dayCreated = dayCreated;
    }

    public String getDayTakeCare() {
        return dayTakeCare;
    }

    public void setDayTakeCare(String dayTakeCare) {
        this.dayTakeCare = dayTakeCare;
    }

    public String getBonsaiPlace() {
        return bonsaiPlace;
    }

    public void setBonsaiPlace(String bonsaiPlace) {
        this.bonsaiPlace = bonsaiPlace;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isHaveNote() {
        return haveNote;
    }

    public void setHaveNote(boolean haveNote) {
        this.haveNote = haveNote;
    }

    public boolean isTicked() {
        return isTicked;
    }

    public void setTicked(boolean ticked) {
        isTicked = ticked;
    }
}
