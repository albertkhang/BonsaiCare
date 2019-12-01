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
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.MainActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.database.SharedPreferencesSetting;
import com.albertkhang.bonsaicare.fragment.FragmentSchedule;
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

    int totalSupply;
    boolean bonsaiNameSelected = false;
    boolean bonsaiSupplySelected = false;

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

        setDataFromIntent();
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
                if (txtTitle.getText().toString().equals("Edit Schedule")) {
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

                        //update into DB
                        ManipulationDb.updateSchedule(dbHelper, scheduleItem);
                        Toast.makeText(NewAndEditScheduleActivity.this, "Updated!", Toast.LENGTH_SHORT).show();

                        //update total supply
                        int totalAmountSupply = ManipulationDb.getTotalSupplyRemain(dbHelper, scheduleItem.getSupplyName());
                        int differenceAmount = Math.abs(totalAmountSupply - scheduleItem.getAmount());
                        Log.d("_ScheduleEdit", "differenceAmount: " + differenceAmount);
                        if (totalAmountSupply > scheduleItem.getAmount()) {
                            totalAmountSupply -= differenceAmount;
                        } else {
                            totalAmountSupply += differenceAmount;
                        }
                        Log.d("_ScheduleEdit", "totalAmountSupply: " + totalAmountSupply);
                        ManipulationDb.updateTotalSupplyRemain(dbHelper, scheduleItem.getSupplyName(), totalAmountSupply);

                        //pullDataBack
                        putDataBack();
                    }
                } else {
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
                        Toast.makeText(NewAndEditScheduleActivity.this, getString(R.string.toastAddSuccess), Toast.LENGTH_SHORT).show();
                        //update total supply
                        ManipulationDb.updateTotalSupplyRemain(dbHelper, scheduleItem.getSupplyName(), totalSupply - scheduleItem.getAmount());
                        //pullDataBack
                        putDataBack();
                    }
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

    private void setDataFromIntent() {
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            txtTitle.setText(title);

            int id = getIntent().getIntExtra("id", -1);
            scheduleItem.setId(id);

            String bonsaiName = getIntent().getStringExtra("bonsaiName");
            scheduleItem.setBonsaiName(bonsaiName);
            txtBonsaiNameValue.setText(bonsaiName);
            bonsaiNameSelected = true;

            String dayCreated = getIntent().getStringExtra("dayCreated");
            scheduleItem.setDayCreated(dayCreated);
            txtDayCreateValue.setText(dayCreated);

            String dayTakeCare = getIntent().getStringExtra("dayTakeCare");
            scheduleItem.setDayTakeCare(dayTakeCare);
            txtDayTakeCareValue.setText(dayTakeCare);

            String timeTakeCare = getIntent().getStringExtra("timeTakeCare");
            scheduleItem.setTimeTakeCare(timeTakeCare);
            txtTimeTakeCareValue.setText(timeTakeCare);

            String bonsaiPlace = getIntent().getStringExtra("bonsaiPlace");
            scheduleItem.setBonsaiPlace(bonsaiPlace);

            String supplyName = getIntent().getStringExtra("supplyName");
            scheduleItem.setSupplyName(supplyName);
            txtSupplyValue.setText("" + supplyName);
            bonsaiSupplySelected = true;

            int amount = getIntent().getIntExtra("amount", 0);
            scheduleItem.setAmount(amount);
            txtAmountValue.setText("" + amount);
            txtAmountValue.setSelection(txtAmountValue.getText().toString().length());

            String note = getIntent().getStringExtra("note");
            scheduleItem.setNote(note);
            txtNoteValue.setText(note);

            boolean ticked = getIntent().getBooleanExtra("ticked", false);
            scheduleItem.setTicked(ticked);
        }
    }

    private void putDataBack() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (txtTitle.getText().toString().equals("Edit Schedule")) {
            intent.putExtra("id", scheduleItem.getId());
            intent.putExtra("bonsaiName", scheduleItem.getBonsaiName());
            intent.putExtra("dayCreated", scheduleItem.getDayCreated());
            intent.putExtra("dayTakeCare", scheduleItem.getDayTakeCare());
            intent.putExtra("timeTakeCare", scheduleItem.getTimeTakeCare());
            intent.putExtra("bonsaiPlace", scheduleItem.getBonsaiPlace());
            intent.putExtra("supplyName", scheduleItem.getSupplyName());
            intent.putExtra("amount", scheduleItem.getAmount());
            intent.putExtra("note", scheduleItem.getNote());
        }
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
                    scheduleItem.setBonsaiName(data.getStringExtra("bonsaiName"));

                    Log.d("_onActivityResult", "bonsaiName: " + scheduleItem.getBonsaiName());
                    Log.d("_onActivityResult", "bonsaiPlace: " + scheduleItem.getBonsaiPlace());

                    txtBonsaiNameValue.setText(scheduleItem.getBonsaiName());

                    bonsaiNameSelected = true;
                }
                break;

            case SELECT_SUPPLY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    scheduleItem.setSupplyName(data.getStringExtra("supplyName"));
                    txtSupplyValue.setText(scheduleItem.getSupplyName());

                    totalSupply = ManipulationDb.getTotalSupplyRemain(dbHelper, scheduleItem.getSupplyName());

                    Log.d("_onActivityResult", "supplyName: " + scheduleItem.getSupplyName());
                    Log.d("_onActivityResult", "totalSupply: " + totalSupply);

                    bonsaiSupplySelected = true;
                }

                break;
        }
    }

    private boolean isInputValid() {
        //name
        //supply
        //amount

        if (!bonsaiNameSelected) {
            txtBonsaiNameValue.setError("Please select bonsai.");
            return false;
        } else {
            txtBonsaiNameValue.setError(null);
        }

        if (!bonsaiSupplySelected) {
            txtSupplyValue.setError("Please select supply.");
            return false;
        } else {
            txtSupplyValue.setError(null);
        }

        if (!isSupplyAmountValid(Integer.parseInt(txtAmountValue.getText().toString()))) {
            return false;
        }

        //dateTakeCare is after bonsaiDatePlanted ==> return false
        int bonsaiName = ManipulationDb.getBonsaiIdFromBonsaiName(dbHelper, scheduleItem.getBonsaiName());
        Date bonsaiDatePlanted = ManipulationDb.getBonsaiDatePlanted(dbHelper, bonsaiName);
        if (!isDayTakeCareValid(scheduleItem.getDateTakeCare(), bonsaiDatePlanted)) {
            return false;
        }

        return true;
    }

    private boolean isDayTakeCareValid(Date dayTakeCare, Date bonsaiDayPlanted) {
        if (dayTakeCare.compareTo(bonsaiDayPlanted) == 0) {
            //dayTakeCare == bonsaiDayPlanted
            return true;
        } else {
            if (dayTakeCare.compareTo(bonsaiDayPlanted) < 0) {
                //dayTakeCare is before bonsaiDayPlanted
                return true;
            }
        }

        return false;
    }

    private boolean isSupplyAmountValid(int amount) {
        if (txtAmountValue.getText().toString().isEmpty()) {
            txtAmountValue.setError("Amount need larger than 0.");
            return false;
        }

        if (amount < 1) {
            txtAmountValue.setError(getString(R.string.notifyErrorSupplyBought));
            return false;
        }

        totalSupply = ManipulationDb.getTotalSupplyRemain(dbHelper, txtSupplyValue.getText().toString());
        if (amount > totalSupply) {

            txtAmountValue.setError(getString(R.string.notifyErrorSupplyBiggerBought));
            return false;
        }

        return true;
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
