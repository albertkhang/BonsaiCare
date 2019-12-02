package com.albertkhang.bonsaicare.activity.manage.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;

public class ReportDetailList extends AppCompatActivity {
    ImageView btnBack;

    //    ImageView imgIconMoney;
//    TextView txtMoneyTakeCareReportTitle;
    ConstraintLayout moneyTakeCareReportFrame;

    //    ImageView imgIconBonsai;
//    TextView txtBonsaiStatusReportTitle;
    ConstraintLayout bonsaiStatusReportFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail_list);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);

//        imgIconMoney = findViewById(R.id.imgIconMoney);
//        txtMoneyTakeCareReportTitle = findViewById(R.id.txtMoneyTakeCareReportTitle);
        moneyTakeCareReportFrame = findViewById(R.id.moneyTakeCareReportFrame);

//        imgIconBonsai = findViewById(R.id.imgIconBonsai);
//        txtBonsaiStatusReportTitle = findViewById(R.id.txtBonsaiStatusReportTitle);
        bonsaiStatusReportFrame = findViewById(R.id.bonsaiStatusReportFrame);
    }

    private void addEvent() {
        moneyTakeCareReportFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMoneyTakeCareReportActivity();
            }
        });

        bonsaiStatusReportFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBonsaiStatusReportActivity();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void startMoneyTakeCareReportActivity() {
        Intent intent = new Intent(this, MoneyTakeCareReportActivity.class);
        startActivity(intent);
    }

    private void startBonsaiStatusReportActivity() {
        Intent intent = new Intent(this, BonsaiStatusReportActivity.class);
        startActivity(intent);
    }
}
