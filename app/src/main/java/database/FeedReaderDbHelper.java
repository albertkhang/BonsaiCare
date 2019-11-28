package com.albertkhang.bonsaicare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BonsaiCare.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.FeedEntry.SQL_CREATE_BONSAI_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_CREATE_SCHEDULE_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_CREATE_PLACEMENT_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_CREATE_SUPPLY_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_CREATE_SUPPLIES_BILL_TABLE);

        ManipulationDb.createDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FeedReaderContract.FeedEntry.SQL_DELETE_BONSAI_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_DELETE_SCHEDULE_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_DELETE_PLACEMENT_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_DELETE_SUPPLY_TABLE);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_DELETE_SUPPLIES_BILL_TABLE);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
