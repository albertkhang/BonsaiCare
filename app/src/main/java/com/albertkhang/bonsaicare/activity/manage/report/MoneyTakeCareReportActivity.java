package com.albertkhang.bonsaicare.activity.manage.report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.adapter.MoneyTakeCareReportRecyclerAdapter;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.MoneyTakeCareReportItem;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoneyTakeCareReportActivity extends AppCompatActivity {
    TextView txtMonthValue;
    TextView txtMonthYearValue;

    TextView txtTotalMoneyTakeCareValue;

    RecyclerView recyclerView;
    MoneyTakeCareReportRecyclerAdapter moneyTakeCareReportRecyclerAdapter;
    ArrayList<MoneyTakeCareReportItem> moneyTakeCareReportArrayList;

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

        txtTotalMoneyTakeCareValue = findViewById(R.id.txtTotalMoneyTakeCareValue);

        recyclerView = findViewById(R.id.recyclerView);
        moneyTakeCareReportRecyclerAdapter = new MoneyTakeCareReportRecyclerAdapter(this, txtTotalMoneyTakeCareValue);
        moneyTakeCareReportArrayList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(moneyTakeCareReportRecyclerAdapter);

        setDateDefault();
    }

    private void addEvent() {
        txtMonthValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RackMonthPicker rackMonthPicker = new RackMonthPicker(MoneyTakeCareReportActivity.this);
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

        moneyTakeCareReportRecyclerAdapter.update(month, year);
    }
}
