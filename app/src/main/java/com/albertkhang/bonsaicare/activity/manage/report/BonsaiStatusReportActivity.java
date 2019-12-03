package com.albertkhang.bonsaicare.activity.manage.report;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.adapter.BonsaiRecyclerViewAdapter;
import com.albertkhang.bonsaicare.adapter.BonsaiStatusReportAdapter;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BonsaiStatusReportActivity extends AppCompatActivity {
    ImageView btnBack;

    TextView txtMonthYearValue;
    TextView txtMonthValue;

    RecyclerView recyclerView;
    BonsaiStatusReportAdapter bonsaiStatusReportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonsai_status_report);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);

        txtMonthYearValue = findViewById(R.id.txtMonthYearValue);
        txtMonthValue = findViewById(R.id.txtMonthValue);

        recyclerView = findViewById(R.id.recyclerView);
        bonsaiStatusReportAdapter = new BonsaiStatusReportAdapter(this);
        recyclerView.setAdapter(bonsaiStatusReportAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        setDateDefault();
    }

    private void addEvent() {
        txtMonthValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RackMonthPicker rackMonthPicker = new RackMonthPicker(BonsaiStatusReportActivity.this);
                rackMonthPicker.setLocale(Locale.ENGLISH)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                Log.d("_txtMonthValue", monthLabel);
                                Log.d("_txtMonthValue", "month: " + month);
                                Log.d("_txtMonthValue", "year: " + year);
                                txtMonthYearValue.setText(monthLabel);

                                updateAdapter();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setColorTheme(R.color.color_primary)
                        .show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setDateDefault() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM, yyyy", Locale.US);
        txtMonthYearValue.setText(df.format(date));

        updateAdapter();
    }

    private void updateAdapter() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM, yyyy", Locale.US);
        try {
            date = df.parse(txtMonthYearValue.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        bonsaiStatusReportAdapter.update(month, year);
    }
}
