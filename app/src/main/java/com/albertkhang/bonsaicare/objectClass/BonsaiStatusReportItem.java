package com.albertkhang.bonsaicare.objectClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BonsaiStatusReportItem {
    private String bonsaiName;
    private String bonsaiType;
    private String bonsaiPlace;
    private String bonsaiDayTakeCare;
    private String timeTakeCare;
    private boolean isTicked;

    public BonsaiStatusReportItem() {
        isTicked = false;
    }

    public Date getDayTakeCare() {
        Date c = null;
        if (!bonsaiDayTakeCare.isEmpty()) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM - dd - yyyy");
                c = df.parse(bonsaiDayTakeCare);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return c;
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

    public String getBonsaiPlace() {
        return bonsaiPlace;
    }

    public void setBonsaiPlace(String bonsaiPlace) {
        this.bonsaiPlace = bonsaiPlace;
    }

    public String getBonsaiDayTakeCare() {
        return bonsaiDayTakeCare;
    }

    public void setBonsaiDayTakeCare(String bonsaiDayTakeCare) {
        this.bonsaiDayTakeCare = bonsaiDayTakeCare;
    }

    public String getTimeTakeCare() {
        return timeTakeCare;
    }

    public void setTimeTakeCare(String timeTakeCare) {
        this.timeTakeCare = timeTakeCare;
    }

    public boolean isTicked() {
        return isTicked;
    }

    public void setTicked(boolean ticked) {
        isTicked = ticked;
    }
}
