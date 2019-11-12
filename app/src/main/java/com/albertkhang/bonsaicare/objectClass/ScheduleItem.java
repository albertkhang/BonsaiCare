package com.albertkhang.bonsaicare.objectClass;

public class ScheduleItem {
    private int time;
    private String day;//May 13
    private String name;
    private String location;
    private String supplies;
    private String note;
    private boolean haveNote;
    private boolean isTicked;

    public ScheduleItem() {
        haveNote = false;
        isTicked = false;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupplies() {
        return supplies;
    }

    public void setSupplies(String supplies) {
        this.supplies = supplies;
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
