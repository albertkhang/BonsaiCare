package com.albertkhang.bonsaicare.activity.manage.report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.albertkhang.bonsaicare.R;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoneyTakeCareReportActivity extends AppCompatActivity {
    TextView txtMonthValue;
    TextView txtMonthYearValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_take_care_report);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtMonthValue = findViewById(R.id.txtMonthValue);
        txtMonthYearValue = findViewById(R.id.txtMonthYearValue);

        setDateDefault();
    }

    private void addEvent() {
        txtMonthValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RackMonthPicker(MoneyTakeCareReportActivity.this)
                        .setLocale(Locale.ENGLISH)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                Log.d("_txtMonthValue", monthLabel);
                                Log.d("_txtMonthValue", "month: " + month);
                                Log.d("_txtMonthValue", "year: " + year);

                                txtMonthYearValue.setText(monthLabel);
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
    }

    private void setDateDefault() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM, yyyy", Locale.US);
        txtMonthYearValue.setText(df.format(date));
    }
}
