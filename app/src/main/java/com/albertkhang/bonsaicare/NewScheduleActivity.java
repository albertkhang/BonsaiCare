package com.albertkhang.bonsaicare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class NewScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        Log.d("activity_newSchedule", "created");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity_newSchedule", "destroyed");
    }
}
