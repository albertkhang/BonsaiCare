package com.albertkhang.bonsaicare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.albertkhang.bonsaicare.R;
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

    public static void getBonsai(FeedReaderDbHelper dbHelper, BonsaiItem bonsaiItem) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry.BONSAI_NAME,
                FeedReaderContract.FeedEntry.BONSAI_TYPE,
                FeedReaderContract.FeedEntry.BONSAI_PLACEMENT_ID,
                FeedReaderContract.FeedEntry.BONSAI_DAY_PLANTED
        };

        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(bonsaiItem.getId())};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.BONSAI_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            bonsaiItem.setBonsaiName(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_NAME)));
            bonsaiItem.setBonsaiType(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_TYPE)));
            int placeId = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_TYPE));
            bonsaiItem.setBonsaiPlacementName(getPlacementNameFromPlacementId(dbHelper, placeId));
            bonsaiItem.setBonsaiDayPlanted(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.BONSAI_DAY_PLANTED)));
        }
        cursor.close();
    }

    public static void addNewBonsai(FeedReaderDbHelper dbHelper, BonsaiItem bonsaiItem) {
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

    public static void updateBonsai(FeedReaderDbHelper dbHelper, BonsaiItem bonsaiItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.BONSAI_NAME, bonsaiItem.getBonsaiName());
        values.put(FeedReaderContract.FeedEntry.BONSAI_TYPE, bonsaiItem.getBonsaiType());

        int placementId = getPlacementIdFromPlacementName(dbHelper, bonsaiItem.getBonsaiPlacementName());
        values.put(FeedReaderContract.FeedEntry.BONSAI_PLACEMENT_ID, placementId);

        values.put(FeedReaderContract.FeedEntry.BONSAI_DAY_PLANTED, bonsaiItem.getBonsaiDayPlanted());

        String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(bonsaiItem.getId())};

        db.update(
                FeedReaderContract.FeedEntry.BONSAI_TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public static boolean deleteBonsai(FeedReaderDbHelper dbHelper, int bonsai_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(bonsai_id)};

        int deletedRows = db.delete(FeedReaderContract.FeedEntry.BONSAI_TABLE_NAME, selection, selectionArgs);

        if (deletedRows == 1) {
            return true;
        } else {
            return false;
        }
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

    public static int countBonsaiInPlacement(FeedReaderDbHelper dbHelper, String placementName) {
        int placementId = getPlacementIdFromPlacementName(dbHelper, placementName);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID
        };

        String selection = FeedReaderContract.FeedEntry.BONSAI_PLACEMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(placementId)};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.BONSAI_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = 0;
        while (cursor.moveToNext()) {
            count++;
        }
        cursor.close();
        Log.d("_ManipulationDb", placementName + " count: " + count);

        return count;
    }

    //return true: change setting
    //return false: not change setting
    private static boolean newMaxBonsaiValid(FeedReaderDbHelper dbHelper, int newMaxBonsai, String placementName) {
        int currentMaxBonsai = countBonsaiInPlacement(dbHelper, placementName);

        if (currentMaxBonsai <= newMaxBonsai) {
            return true;//change setting
        } else {
            return false;
        }
    }

    public static String getErrorText(Context context, FeedReaderDbHelper dbHelper, int newMaxBonsai) {
        ArrayList<PlacementItem> placementArrayList = new ArrayList<>();
        getAllDataPlacementTable(dbHelper, placementArrayList);

        ArrayList<String> placeError = new ArrayList<>();

        for (int i = 0; i < placementArrayList.size(); i++) {
            if (!newMaxBonsaiValid(dbHelper, newMaxBonsai, placementArrayList.get(i).getPlaccementName())) {
                placeError.add(placementArrayList.get(i).getPlaccementName());
            }
        }

//        String postReturn = "Move bonsai in ";
        String postReturn = context.getString(R.string.preErrorText);
        if (placeError.size() != 0) {
            for (int i = 0; i < placeError.size(); i++) {
                postReturn = postReturn.concat("'" + placeError.get(i) + "'");

                if (i != (placeError.size() - 1)) {
                    postReturn = postReturn.concat(", ");
                } else {
                    postReturn = postReturn.concat(" ");
                }
            }
            postReturn = postReturn.concat(context.getString(R.string.postErrorText));
        } else {
            postReturn = null;
        }

        return postReturn;
    }

}
