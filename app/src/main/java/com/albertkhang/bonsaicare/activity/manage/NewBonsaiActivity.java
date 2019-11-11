package com.albertkhang.bonsaicare.activity.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.albertkhang.bonsaicare.ObjectClass.PlacementItem;
import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.MainActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewBonsaiActivity extends AppCompatActivity {
    EditText txtBonsaiName;
    Spinner spBonsaiType;
    ArrayList<PlacementItem> placementArrayList;
    Spinner spBonsaiPlace;
    FeedReaderDbHelper dbHelper;
    TextView txtBonsaiDayPlanted;
    Button btnAddNewBonsaiSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bonsai);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtBonsaiName = findViewById(R.id.txtBonsaiName);
        spBonsaiType = findViewById(R.id.spBonsaiType);
        placementArrayList = new ArrayList<>();
        spBonsaiPlace = findViewById(R.id.spBonsaiPlace);
        dbHelper = new FeedReaderDbHelper(this);
        txtBonsaiDayPlanted = findViewById(R.id.txtBonsaiDayPlanted);
        btnAddNewBonsaiSubmit = findViewById(R.id.btnAddNewBonsaiSubmit);
    }

    private void addEvent() {
        setDataForSpinner(spBonsaiType, R.array.dropdownBonsaiType);

        ManipulationDb.getAllDataPlacementTable(dbHelper, placementArrayList);
        setDataForSpinner(spBonsaiPlace, placementArrayList);

        setCurrentDate(txtBonsaiDayPlanted);
        txtBonsaiDayPlanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCurrentDate()) {
                    showDatePicker(Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DATE),
                            Calendar.getInstance().get(Calendar.YEAR));
                } else {
                    String showedDate = getShowedDate();
                    if (showedDate != null) {
                        try {
                            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                            Date c = df.parse(txtBonsaiDayPlanted.getText().toString());

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(c);

                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int date = calendar.get(Calendar.DATE);

                            showDatePicker(month, date, year);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnAddNewBonsaiSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        return df.format(c);
    }

    private String getShowedDate() {
        try {
            String date = txtBonsaiDayPlanted.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
            Date c = df.parse(date);

            return df.format(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isCurrentDate() {
        String date = txtBonsaiDayPlanted.getText().toString();
        Log.d("_isCurrentDate", "date: " + date);

        String currentDate = getCurrentDate();
        Log.d("_isCurrentDate", "currentDate: " + currentDate);

        if (date.equals(currentDate)) {
            return true;
        }

        return false;
    }

    private void showDatePicker(int month, int date, int year) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewBonsaiActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                Log.d("_showDatePicker", "date: " + date);

                try {
                    Date c = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                    SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                    String formattedDate = df.format(c);
                    Log.d("_showDatePicker", "formattedDate: " + formattedDate);

                    txtBonsaiDayPlanted.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void setCurrentDate(TextView textView) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        String formattedDate = df.format(c);

        textView.setText(formattedDate);
    }

    private void setDataForSpinner(Spinner spBonsaiType, int arrayId) {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, arrayId, R.layout.spinner_item);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spBonsaiType.setAdapter(arrayAdapter);
    }

    private void setDataForSpinner(Spinner spBonsaiPlace, ArrayList<PlacementItem> placementArrayList) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < placementArrayList.size(); i++) {
            stringList.add(placementArrayList.get(i).getPlaccementName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, stringList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spBonsaiPlace.setAdapter(arrayAdapter);
    }
}
