package com.albertkhang.bonsaicare.objectClass;

import com.albertkhang.bonsaicare.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleItem {
    private int id;

    private String bonsaiName;

    private String dayCreated;
    private String dayTakeCare;

    private String timeTakeCare;

    private String bonsaiPlace;
    private String supplyName;

    private int amount;
    private String note;

    private boolean haveNote;
    private boolean isTicked;

    public Date getDateTakeCare() {
        Date c = null;
        if (!dayTakeCare.isEmpty()) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM - dd - yyyy");
                c = df.parse(dayTakeCare);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return c;
    }

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

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
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
