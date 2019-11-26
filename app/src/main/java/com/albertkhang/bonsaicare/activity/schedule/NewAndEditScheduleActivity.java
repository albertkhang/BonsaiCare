package com.albertkhang.bonsaicare.activity.schedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.MainActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.database.SharedPreferencesSetting;
import com.albertkhang.bonsaicare.objectClass.ScheduleItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewAndEditScheduleActivity extends AppCompatActivity {
    ConstraintLayout layout;

    ImageView btnBack;
    TextView txtTitle;

    TextView txtBonsaiNameValue;
    TextView txtDayCreateValue;
    TextView txtNameSelect;

    TextView txtDayTakeCareValue;
    TextView txtDayTakeCareChange;

    TextView txtTimeTakeCareValue;
    TextView txtTimeTakeCareChange;
    TextView txtSupplySelect;

    TextView txtSupplyValue;
    EditText txtAmountValue;
    EditText txtNoteValue;

    Button btnSubmit;

    boolean isShowKeyboard = false;
    String regex = "^[a-zA-Z0-9]+( [a-zA-Z0-9_]+)*$";
    ScheduleItem scheduleItem;
    FeedReaderDbHelper dbHelper;

    private static final int SELECT_BONSAI_REQUEST_CODE = 1;
    private static final int SELECT_SUPPLY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_and_edit_schedule);

        addControl();
        addEvent();
    }

    private void addControl() {
        layout = findViewById(R.id.layout);

        btnBack = findViewById(R.id.btnBack);
        txtTitle = findViewById(R.id.txtTitle);

        txtBonsaiNameValue = findViewById(R.id.txtBonsaiNameValue);
        txtDayTakeCareValue = findViewById(R.id.txtDayTakeCareValue);
        txtNameSelect = findViewById(R.id.txtNameSelect);

        txtDayCreateValue = findViewById(R.id.txtDayCreateValue);
        txtDayTakeCareChange = findViewById(R.id.txtDayTakeCareChange);
        txtTimeTakeCareValue = findViewById(R.id.txtTimeTakeCareValue);
        txtTimeTakeCareChange = findViewById(R.id.txtTimeTakeCareChange);
        txtSupplySelect = findViewById(R.id.txtSupplySelect);

        txtSupplyValue = findViewById(R.id.txtSupplyValue);
        txtAmountValue = findViewById(R.id.txtAmountValue);
        txtNoteValue = findViewById(R.id.txtNoteValue);

        btnSubmit = findViewById(R.id.btnSubmit);

        scheduleItem = new ScheduleItem();

        dbHelper = new FeedReaderDbHelper(this);

        setCurrentDate(txtDayTakeCareValue);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addEvent() {
        txtSupplySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScheduleSelectSupplyActivity();
            }
        });

        txtNameSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScheduleSelectBonsaiActivity();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputValid()) {
                    //name
                    scheduleItem.setBonsaiName(txtBonsaiNameValue.getText().toString());
                    //dayCreate
                    scheduleItem.setDayCreated(txtDayCreateValue.getText().toString());
                    //dayTakeCare
                    scheduleItem.setDayTakeCare(txtDayTakeCareValue.getText().toString());
                    //timeTakeCare
                    scheduleItem.setTimeTakeCare(txtTimeTakeCareValue.getText().toString());
                    //added bonsaiPlace in onResult
                    //supplyName
                    scheduleItem.setSupplyName(txtSupplyValue.getText().toString());
                    //amount
                    scheduleItem.setAmount(Integer.parseInt(txtAmountValue.getText().toString()));
                    //note
                    scheduleItem.setNote(txtNoteValue.getText().toString());

                    //add into DB
                    ManipulationDb.addNewSchedule(dbHelper, scheduleItem);
                    //pullDataBack
                    putDataBack();
                }
            }
        });

        txtDayTakeCareChange.setOnClickListener(new View.OnClickListener() {
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
                            Date c = df.parse(txtDayTakeCareValue.getText().toString());

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

        txtTimeTakeCareChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewAndEditScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTimeTakeCareValue.setText(getTimeFormatter(selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isShowKeyboard) {
                    hideKeyboard();
                }

                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void putDataBack() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("needRefresh", true);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void startScheduleSelectSupplyActivity() {
        Intent intent = new Intent(NewAndEditScheduleActivity.this, ScheduleSelectActivity.class);
        intent.putExtra("title", "Select Supply");
        intent.putExtra("type", "supply");

        startActivityForResult(intent, SELECT_SUPPLY_REQUEST_CODE);
    }

    private void startScheduleSelectBonsaiActivity() {
        Intent intent = new Intent(NewAndEditScheduleActivity.this, ScheduleSelectActivity.class);
        intent.putExtra("title", "Select Bonsai");
        intent.putExtra("type", "bonsai");

        startActivityForResult(intent, SELECT_BONSAI_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_BONSAI_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    scheduleItem.setBonsaiPlace(data.getStringExtra("bonsaiPlace"));

                    Log.d("_onActivityResult", "bonsaiName: " + scheduleItem.getBonsaiName());
                    Log.d("_onActivityResult", "bonsaiPlace: " + scheduleItem.getBonsaiPlace());

                    txtBonsaiNameValue.setText(scheduleItem.getBonsaiName());
                }
                break;

            case SELECT_SUPPLY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    scheduleItem.setSupplyName(data.getStringExtra("supplyName"));
                    txtSupplyValue.setText(scheduleItem.getSupplyName());

                    Log.d("_onActivityResult", "supplyName: " + scheduleItem.getSupplyName());
                }

                break;
        }
    }

    private boolean isInputValid() {
        if (txtAmountValue.getText().toString().isEmpty()) {
            txtAmountValue.setError("Amount need larger than 0.");
            return false;
        }

        if (!isSupplyAmountValid(Integer.parseInt(txtAmountValue.getText().toString()))) {
            return false;
        }

        return true;
    }

    private boolean isSupplyAmountValid(int amount) {
        if (amount > 0) {
            return true;
        } else {
            txtAmountValue.setError(getString(R.string.notifyErrorSupplyBought));
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (isShowKeyboard) {
            hideKeyboard();
        } else {
            super.onBackPressed();
        }
    }

    private String getTimeFormatter(int hour, int minute) {
        String h = "";
        String m = "";

        if (hour < 10) {
            h += "0" + hour;
        } else {
            h += "" + hour;
        }

        if (minute < 10) {
            m += "0" + minute;
        } else {
            m += "" + minute;
        }

        return h + ":" + m;
    }

    private boolean isNameValid(String text) {
        if (text.length() < 1) {
            return false;
        } else {
            if (text.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    private String getShowedDate() {
        try {
            String date = txtDayTakeCareValue.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
            Date c = df.parse(date);

            return df.format(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void showDatePicker(int month, int date, int year) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewAndEditScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                Log.d("_showDatePicker", "date: " + date);

                try {
                    Date c = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                    SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                    String formattedDate = df.format(c);
                    Log.d("_showDatePicker", "formattedDate: " + formattedDate);

                    txtDayTakeCareValue.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private boolean isCurrentDate() {
        String date = txtDayTakeCareValue.getText().toString();
        Log.d("_isCurrentDate", "date: " + date);

        String currentDate = getCurrentDate();
        Log.d("_isCurrentDate", "currentDate: " + currentDate);

        if (date.equals(currentDate)) {
            return true;
        }

        return false;
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        return df.format(c);
    }

    private void setCurrentDate(TextView textView) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        String formattedDate = df.format(c);
        txtDayCreateValue.setText(formattedDate);

        textView.setText(formattedDate);
    }

    private void hideKeyboard() {
        isShowKeyboard = false;
        txtAmountValue.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtAmountValue.getWindowToken(), 0);
    }
}
