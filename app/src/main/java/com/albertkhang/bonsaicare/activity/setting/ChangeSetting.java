package com.albertkhang.bonsaicare.activity.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;

public class ChangeSetting extends AppCompatActivity {
    TextView txtDetailSettingTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_setting);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtDetailSettingTitle = findViewById(R.id.txtDetailSettingTitle);
    }

    private void addEvent() {
        String title = getIntent().getStringExtra(getString(R.string.putExtraSettingTitle));
        txtDetailSettingTitle.setText(title);
    }
}
