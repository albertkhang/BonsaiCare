package com.albertkhang.bonsaicare.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.albertkhang.bonsaicare.R;

public class SharedPreferencesSetting {
    Context context;
    private int DEFAULT_MAX_BONSAI = 4;
    private int DEFAULT_MAX_MONEY = 100000;
    private int DEFAULT_TICKED = 1;

    public SharedPreferencesSetting(Context context) {
        this.context = context;
//        createDefaultShowAllCompleteSchedule();
    }

    public void addMaxBonsai(int maxBonsai) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.sharedPreferencesSettingMaxBonsai), maxBonsai);
        editor.apply();
    }

    public void addMaxMoney(int maxMoney) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.sharedPreferencesSettingMaxMoney), maxMoney);
        editor.apply();
    }

    private void addShowAllComplete(int status) {
        Log.d("_SharedPreferences", "update_showAll");

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.sharedPreferencesSettingShowAllCompleteSchedule), status);
        editor.apply();
    }

    public int getMaxBonsai() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        return sharedPreferences.getInt(context.getString(R.string.sharedPreferencesSettingMaxBonsai), DEFAULT_MAX_BONSAI);
    }

    public int getMaxMoney() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        return sharedPreferences.getInt(context.getString(R.string.sharedPreferencesSettingMaxMoney), DEFAULT_MAX_MONEY);
    }

    public int getShowAllComplete() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        return sharedPreferences.getInt(context.getString(R.string.sharedPreferencesSettingShowAllCompleteSchedule), 0);
    }

    public void deleteMaxBonsai() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(context.getString(R.string.sharedPreferencesSettingMaxBonsai));
    }

    public void deleteMaxMoney() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(context.getString(R.string.sharedPreferencesSettingMaxMoney));
    }

    public void deleteShowAllComplete() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.sharedPreferencesSetting),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(context.getString(R.string.sharedPreferencesSettingShowAllCompleteSchedule));
    }

    public void updateMaxBonsai(int newMaxBonsai) {
        deleteMaxBonsai();
        addMaxBonsai(newMaxBonsai);
    }

    public void updateMaxMoney(int newMaxMoney) {
        deleteMaxMoney();
        addMaxMoney(newMaxMoney);
    }

    public void updateShowAllComplete(int status) {
        Log.d("_SharedPreferences", "update_showAll");
        deleteShowAllComplete();
        addShowAllComplete(status);
    }
}
