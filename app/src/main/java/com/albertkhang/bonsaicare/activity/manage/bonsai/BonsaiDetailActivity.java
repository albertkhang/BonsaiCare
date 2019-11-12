package com.albertkhang.bonsaicare.activity.manage.bonsai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;

public class BonsaiDetailActivity extends AppCompatActivity {
    TextView txtBonsaiIdValue;
    TextView txtBonsaiNameValue;
    TextView txtBonsaiTypeValue;
    TextView txtBonsaiPlacementValue;
    TextView txtBonsaiDayPlantedValue;

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonsai_detail);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtBonsaiIdValue = findViewById(R.id.txtBonsaiIdValue);
        txtBonsaiNameValue = findViewById(R.id.txtBonsaiNameValue);
        txtBonsaiTypeValue = findViewById(R.id.txtBonsaiTypeValue);
        txtBonsaiPlacementValue = findViewById(R.id.txtBonsaiPlacementValue);
        txtBonsaiDayPlantedValue = findViewById(R.id.txtBonsaiDayPlantedValue);

        btnBack = findViewById(R.id.btnBack);

        setDataPassed();
    }

    private void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setDataPassed() {
        txtBonsaiIdValue.setText(getIntent().getStringExtra("id"));
        txtBonsaiNameValue.setText(getIntent().getStringExtra("name"));
        txtBonsaiTypeValue.setText(getIntent().getStringExtra("type"));
        txtBonsaiPlacementValue.setText(getIntent().getStringExtra("place"));
        txtBonsaiDayPlantedValue.setText(getIntent().getStringExtra("dayPlanted"));
    }
}
