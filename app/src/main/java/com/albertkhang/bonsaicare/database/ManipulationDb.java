package com.albertkhang.bonsaicare.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.albertkhang.bonsaicare.ObjectClass.PlacementItem;

import java.util.ArrayList;

public class ManipulationDb {
    public static void createDefaultData(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_PLACEMENT_BALCONY_DEFAULT_DATA);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_PLACEMENT_WINDOW_DEFAULT_DATA);
        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_PLACEMENT_GATE_DEFAULT_DATA);
//        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_SUPPLY_WATER_DEFAULT_DATA);
//        db.execSQL(FeedReaderContract.FeedEntry.SQL_INSERT_SUPPLY_NITROGEN_FERTILIZER_DEFAULT_DATA);
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
        Log.d("_ManipulationDb", placementArrayList.toString());
    }
}
