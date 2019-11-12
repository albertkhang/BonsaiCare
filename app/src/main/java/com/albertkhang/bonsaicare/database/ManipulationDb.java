package com.albertkhang.bonsaicare.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.objectClass.PlacementItem;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

import java.util.ArrayList;

public class ManipulationDb {
    public static void createDefaultData(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_PLACEMENT_BALCONY_DEFAULT_DATA);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_PLACEMENT_WINDOW_DEFAULT_DATA);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_PLACEMENT_GATE_DEFAULT_DATA);

        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_SUPPLY_WATER_DEFAULT_DATA);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_SUPPLY_NITROGEN_FERTILIZER_DEFAULT_DATA);
    }

    public static void getAllDataBonsaiTable(FeedReaderDbHelper dbHelper, ArrayList<BonsaiItem> bonsaiArrayList) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.BONSAI_NAME,
                FeedReaderContract.FeedEntry.BONSAI_TYPE,
                FeedReaderContract.FeedEntry.BONSAI_PLACEMENT_ID,
                FeedReaderContract.FeedEntry.BONSAI_DAY_PLANTED
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.BONSAI_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        bonsaiArrayList.clear();
        while (cursor.moveToNext()) {
            int bonsaiId = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            String bonsaiName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_NAME));
            String bonsaiType = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_TYPE));
            int bonsaiPlacementId = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_PLACEMENT_ID));
            String bonsaiDayPlanted = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_DAY_PLANTED));

            BonsaiItem item = new BonsaiItem();
            item.setId(bonsaiId);
            item.setBonsaiName(bonsaiName);
            item.setBonsaiType(bonsaiType);
            item.setBonsaiPlacementName(getPlacementNameFromPlacementId(dbHelper, bonsaiPlacementId));
            item.setBonsaiDayPlanted(bonsaiDayPlanted);

            bonsaiArrayList.add(item);
        }

        cursor.close();
        Log.d("_ManipulationDb", bonsaiArrayList.toString());
    }

    public static void getAllDataPlacementTable(FeedReaderDbHelper dbHelper, ArrayList<PlacementItem> placementArrayList) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.PLACEMENT_NAME
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.PLACEMENT_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        placementArrayList.clear();
        while (cursor.moveToNext()) {
            int placementId = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            String placementName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.PLACEMENT_NAME));

            PlacementItem item = new PlacementItem();
            item.setId(placementId);
            item.setPlaccementName(placementName);
            placementArrayList.add(item);

            Log.d("_ManipulationDb", "placementId: " + placementId);
            Log.d("_ManipulationDb", "placementName: " + placementName);
        }

        cursor.close();
        Log.d("_ManipulationDb", placementArrayList.toString());
    }

    public static void getAllDataSupplyTable(FeedReaderDbHelper dbHelper, ArrayList<SupplyItem> supplyArrayList) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.SUPPLY_NAME,
                FeedReaderContract.FeedEntry.SUPPLY_UNIT,
                FeedReaderContract.FeedEntry.SUPPLY_TOTAL_SUPPLIES
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.SUPPLY_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        supplyArrayList.clear();
        while (cursor.moveToNext()) {
            int supplyId = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            String supplyName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.SUPPLY_NAME));
            String supplyUnit = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.SUPPLY_UNIT));
            int supplyTotalSupply = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.SUPPLY_TOTAL_SUPPLIES));

            SupplyItem item = new SupplyItem();
            item.setId(supplyId);
            item.setSupplyName(supplyName);
            item.setSupplyUnit(supplyUnit);
            item.setTotal(supplyTotalSupply);

            supplyArrayList.add(item);
        }

        cursor.close();
    }

    public static void addNewBonsaiToDb(FeedReaderDbHelper dbHelper, BonsaiItem bonsaiItem) {
        //get placementId from placementName
        //insert to Db

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.BONSAI_NAME, bonsaiItem.getBonsaiName());
        values.put(FeedReaderContract.FeedEntry.BONSAI_TYPE, bonsaiItem.getBonsaiType());

        int placementId = getPlacementIdFromPlacementName(dbHelper, bonsaiItem.getBonsaiPlacementName());
        values.put(FeedReaderContract.FeedEntry.BONSAI_PLACEMENT_ID, placementId);

        values.put(FeedReaderContract.FeedEntry.BONSAI_DAY_PLANTED, bonsaiItem.getBonsaiDayPlanted());

        db.insert(FeedReaderContract.FeedEntry.BONSAI_TABLE_NAME, null, values);
    }

    private static int getPlacementIdFromPlacementName(FeedReaderDbHelper dbHelper, String placementName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID
        };

        String selection = FeedReaderContract.FeedEntry.PLACEMENT_NAME + " = ?";
        String[] selectionArgs = {placementName};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.PLACEMENT_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int placementId = 0;
        while (cursor.moveToNext()) {
            placementId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
        }
        cursor.close();

        return placementId;
    }

    private static String getPlacementNameFromPlacementId(FeedReaderDbHelper dbHelper, int placementId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry.PLACEMENT_NAME
        };

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(placementId)};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.PLACEMENT_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String placementName = null;
        while (cursor.moveToNext()) {
            placementName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.PLACEMENT_NAME));
        }
        cursor.close();

        return placementName;
    }
}
