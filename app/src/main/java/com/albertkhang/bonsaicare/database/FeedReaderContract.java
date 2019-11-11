package com.albertkhang.bonsaicare.database;

import android.provider.BaseColumns;

import com.albertkhang.bonsaicare.R;

public final class FeedReaderContract {
    private FeedReaderContract() {
    }

    public static class FeedEntry implements BaseColumns {
        /* BONSAI TABLE */
        public static final String BONSAI_TABLE_NAME = "bonsai";
        public static final String BONSAI_NAME = "bonsai_name";
        public static final String BONSAI_TYPE = "bonsai_type";//input: need light; need shade
        public static final String BONSAI_PLACEMENT_ID = "bonsai_placement_id";
        public static final String BONSAI_DAY_PLANTED = "bonsai_day_planted";

        /* SCHEDULE TABLE */
        public static final String SCHEDULE_TABLE_NAME = "schedule";
        public static final String SCHEDULE_NAME = "schedule_name";
        public static final String SCHEDULE_BONSAI_ID = "schedule_bonsai_id";
        public static final String SCHEDULE_DATE_CREATED = "schedule_date_created";
        public static final String SCHEDULE_DATE_TAKE_CARE = "schedule_date_take_care";
        public static final String SCHEDULE_PLACEMENT_ID = "schedule_placement_id";
        public static final String SCHEDULE_SUPPLY_ID = "schedule_supply_id";
        public static final String SCHEDULE_AMOUNT = "schedule_amount";
        public static final String SCHEDULE_NOTE = "schedule_note";

        /* PLACEMENT TABLE */
        public static final String PLACEMENT_TABLE_NAME = "placement";
        public static final String PLACEMENT_NAME = "placement_name";//default: balcony; window; front of the gate

        /* SUPPLY TABLE */
        public static final String SUPPLY_TABLE_NAME = "supply";
        public static final String SUPPLY_NAME = "supply_name";//default: water; nitrogen fertilizer
        public static final String SUPPLY_UNIT = "supply_unit";//default: liter; gram
        public static final String SUPPLY_TOTAL_SUPPLIES = "supply_total_supplies";

        /* SUPPLIES_BILL TABLE */
        public static final String SUPPLIES_BILL_TABLE_NAME = "supply_bill";
        public static final String SUPPLIES_BILL_SUPPLIES_ID = "supply_bill_supply_id";
        public static final String SUPPLIES_BILL_ADDRESS_BROUGHT = "supply_bill_address_bought";
        public static final String SUPPLIES_BILL_SUPPLIES_AMOUNT = "supply_bill_supplies_amount";
        public static final String SUPPLIES_BILL_DATE_BOUGHT = "supply_bill_date_bought";
        public static final String SUPPLIES_BILL_TOTAL_MONEY = "supply_bill_total_money";

        /* ========== CREATE ========== */
        /* CREATE BONSAI TABLE */
        public static final String SQL_CREATE_BONSAI_TABLE =
                "CREATE TABLE " + FeedEntry.BONSAI_TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.BONSAI_NAME + " TEXT," +
                        FeedEntry.BONSAI_TYPE + " TEXT," +
                        FeedEntry.BONSAI_PLACEMENT_ID + " INTEGER," +
                        FeedEntry.BONSAI_DAY_PLANTED + " TEXT)";

        /* CREATE SCHEDULE TABLE */
        public static final String SQL_CREATE_SCHEDULE_TABLE =
                "CREATE TABLE " + FeedEntry.SCHEDULE_TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.SCHEDULE_NAME + " TEXT," +
                        FeedEntry.SCHEDULE_BONSAI_ID + " INTEGER," +
                        FeedEntry.SCHEDULE_DATE_CREATED + " TEXT," +
                        FeedEntry.SCHEDULE_DATE_TAKE_CARE + " TEXT," +
                        FeedEntry.SCHEDULE_PLACEMENT_ID + " INTEGER," +
                        FeedEntry.SCHEDULE_SUPPLY_ID + " INTEGER," +
                        FeedEntry.SCHEDULE_AMOUNT + " INTEGER," +
                        FeedEntry.SCHEDULE_NOTE + " TEXT)";

        /* CREATE PLACEMENT TABLE */
        public static final String SQL_CREATE_PLACEMENT_TABLE =
                "CREATE TABLE " + FeedEntry.PLACEMENT_TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.PLACEMENT_NAME + " TEXT)";

        /* CREATE SUPPLY TABLE */
        public static final String SQL_CREATE_SUPPLY_TABLE =
                "CREATE TABLE " + FeedEntry.SUPPLY_TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.SUPPLY_NAME + " TEXT," +
                        FeedEntry.SUPPLY_UNIT + " TEXT," +
                        FeedEntry.SUPPLY_TOTAL_SUPPLIES + " INTEGER)";

        /* CREATE SUPPLIES_BILL TABLE */
        public static final String SQL_CREATE_SUPPLIES_BILL_TABLE =
                "CREATE TABLE " + FeedEntry.SUPPLIES_BILL_TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.SUPPLIES_BILL_SUPPLIES_ID + " INTEGER," +
                        FeedEntry.SUPPLIES_BILL_ADDRESS_BROUGHT + " TEXT," +
                        FeedEntry.SUPPLIES_BILL_SUPPLIES_AMOUNT + " INTEGER," +
                        FeedEntry.SUPPLIES_BILL_DATE_BOUGHT + " TEXT," +
                        FeedEntry.SUPPLIES_BILL_TOTAL_MONEY + " INTEGER)";

        /* ========== DELETE ========== */
        /* DELETE BONSAI TABLE */
        public static final String SQL_DELETE_BONSAI_TABLE =
                "DROP TABLE IF EXISTS " + FeedEntry.BONSAI_TABLE_NAME;

        /* DELETE SCHEDULE TABLE */
        public static final String SQL_DELETE_SCHEDULE_TABLE =
                "DROP TABLE IF EXISTS " + FeedEntry.SCHEDULE_TABLE_NAME;

        /* DELETE PLACEMENT TABLE */
        public static final String SQL_DELETE_PLACEMENT_TABLE =
                "DROP TABLE IF EXISTS " + FeedEntry.PLACEMENT_TABLE_NAME;

        /* DELETE SUPPLY TABLE */
        public static final String SQL_DELETE_SUPPLY_TABLE =
                "DROP TABLE IF EXISTS " + FeedEntry.SUPPLY_TABLE_NAME;

        /* DELETE SUPPLIES_BILL TABLE */
        public static final String SQL_DELETE_SUPPLIES_BILL_TABLE =
                "DROP TABLE IF EXISTS " + FeedEntry.SUPPLIES_BILL_TABLE_NAME;

        /* MANIPULATION */
        //PLACEMENT
        //INSERT
        public static final String SQL_INSERT_PLACEMENT_BALCONY_DEFAULT_DATA =
                "INSERT INTO " + FeedEntry.PLACEMENT_TABLE_NAME +
                        " (" + FeedEntry.PLACEMENT_NAME + ")" +
                        " VALUES ('Balcony')";

        public static final String SQL_INSERT_PLACEMENT_WINDOW_DEFAULT_DATA =
                "INSERT INTO " + FeedEntry.PLACEMENT_TABLE_NAME +
                        " (" + FeedEntry.PLACEMENT_NAME + ")" +
                        " VALUES ('Window')";

        public static final String SQL_INSERT_PLACEMENT_GATE_DEFAULT_DATA =
                "INSERT INTO " + FeedEntry.PLACEMENT_TABLE_NAME +
                        " (" + FeedEntry.PLACEMENT_NAME + ")" +
                        " VALUES ('Gate')";

        //=================================
        //SUPPLY
        public static final String SQL_INSERT_SUPPLY_WATER_DEFAULT_DATA =
                "INSERT INTO " + FeedEntry.SUPPLY_TABLE_NAME +
                        " (" + FeedEntry.SUPPLY_NAME + ", " + FeedEntry.SUPPLY_UNIT+ ", " + FeedEntry.SUPPLY_TOTAL_SUPPLIES + ")" +
                        " VALUES ('Water', 'liter', 0)";

        public static final String SQL_INSERT_SUPPLY_NITROGEN_FERTILIZER_DEFAULT_DATA =
                "INSERT INTO " + FeedEntry.SUPPLY_TABLE_NAME +
                        " (" + FeedEntry.SUPPLY_NAME + ", " + FeedEntry.SUPPLY_UNIT+ ", " + FeedEntry.SUPPLY_TOTAL_SUPPLIES + ")" +
                        " VALUES ('Nitrogen fertilizer', 'gram', 0)";
    }
}
