package com.albertkhang.bonsaicare.activity.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.objectClass.ScheduleItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewAndEditScheduleActivity extends AppCompatActivity {
    ConstraintLayout layout;

    ImageView btnBack;
    TextView txtTitle;

    EditText txtNameValue;
    TextView txtDayCreateValue;
    TextView txtDayTakeCareValue;
    TextView txtDayTakeCareChange;
    TextView txtTimeTakeCareValue;
    TextView txtSupplyValue;
    EditText txtAmountValue;
    EditText txtNoteValue;

    Button btnSubmit;

    boolean isShowKeyboard = false;
    String regex = "^[a-zA-Z0-9]+( [a-zA-Z0-9_]+)*$";
    ScheduleItem scheduleItem;

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

        txtNameValue = findViewById(R.id.txtNameValue);
        txtDayTakeCareValue = findViewById(R.id.txtDayTakeCareValue);
        txtDayCreateValue = findViewById(R.id.txtDayCreateValue);
        txtDayTakeCareChange = findViewById(R.id.txtDayTakeCareChange);
        txtTimeTakeCareValue = findViewById(R.id.txtTimeTakeCareValue);
        txtSupplyValue = findViewById(R.id.txtSupplyValue);
        txtAmountValue = findViewById(R.id.txtAmountValue);
        txtNoteValue = findViewById(R.id.txtNoteValue);

        btnSubmit = findViewById(R.id.btnSubmit);

        scheduleItem = new ScheduleItem();

        setCurrentDate(txtDayTakeCareValue);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addEvent() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameValid(txtNameValue.getText().toString())) {
                    //name
                    scheduleItem.setScheduleName(txtNameValue.getText().toString());
                    //dayCreate
                    scheduleItem.setDayCreated(txtDayCreateValue.getText().toString());
                    //dayTakeCare
                    scheduleItem.setDayTakeCare(txtDayTakeCareValue.getText().toString());
                    //timeTakeCare
                    scheduleItem.setTimeTakeCare(txtTimeTakeCareValue.getText().toString());
                    //supply
                    scheduleItem.setSupply(txtSupplyValue.getText().toString());
                    //amount
                    scheduleItem.setAmount(Integer.parseInt(txtAmountValue.getText().toString()));
                    //note
                    scheduleItem.setNote(txtNoteValue.getText().toString());

                    
                } else {
                    //notify error
                    txtNameValue.setError(getString(R.string.nameError));
                }
            }
        });

        txtNameValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowKeyboard = true;
            }
        });

        txtDayTakeCareValue.setOnClickListener(new View.OnClickListener() {
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

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isShowKeyboard) {
                    hideKeyboard();
                }

                return false;
            }
        });

        txtNameValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowKeyboard = true;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    private void showKeyboard() {
        isShowKeyboard = true;
        txtNameValue.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void hideKeyboard() {
        isShowKeyboard = false;
        txtNameValue.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtNameValue.getWindowToken(), 0);
    }
}
