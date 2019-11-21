package com.albertkhang.bonsaicare.activity.manage.supply.supplyBill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.MainActivity;
import com.albertkhang.bonsaicare.activity.manage.bonsai.NewAndEditBonsaiActivity;
import com.albertkhang.bonsaicare.activity.manage.supply.supply.NewAndEditSupplyActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.database.SharedPreferencesSetting;
import com.albertkhang.bonsaicare.objectClass.SupplyBillItem;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewAndEditSupplyBillActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView txtTitle;

    TextView txtSupplyNameValue;
    EditText txtAddressValue;
    TextView txtDayBoughtValue;
    EditText txtSupplyBoughtValue;
    EditText txtMoneyBoughtValue;

    Button btnSubmit;

    String regex = "^[a-zA-Z0-9.,/]+( [a-zA-Z0-9.,/]+)*$";
    FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_and_edit_supply_bill);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        txtTitle = findViewById(R.id.txtTitle);

        txtSupplyNameValue = findViewById(R.id.txtSupplyNameValue);
        txtAddressValue = findViewById(R.id.txtAddressValue);
        txtDayBoughtValue = findViewById(R.id.txtDayBoughtValue);
        txtSupplyBoughtValue = findViewById(R.id.txtSupplyBoughtValue);
        txtMoneyBoughtValue = findViewById(R.id.txtMoneyBoughtValue);

        btnSubmit = findViewById(R.id.btnSubmit);
        dbHelper = new FeedReaderDbHelper(this);

        setCurrentDate(txtDayBoughtValue);
        setDataFromIntent();
    }

    private void addEvent() {
        txtDayBoughtValue.setOnClickListener(new View.OnClickListener() {
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
                            Date c = df.parse(txtDayBoughtValue.getText().toString());

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (handleInput()) {
                    SupplyBillItem item = new SupplyBillItem();
                    item.setSupplyName(txtSupplyNameValue.getText().toString());
                    item.setAddressBought(txtAddressValue.getText().toString());
                    item.setDayBought(txtDayBoughtValue.getText().toString());
                    item.setTotalSupplies(Integer.parseInt(txtSupplyBoughtValue.getText().toString()));
                    item.setTotalMoney(Integer.parseInt(txtMoneyBoughtValue.getText().toString()));

                    ManipulationDb.addNewSupplyBill(dbHelper, item);

                    putDataBack();
                    Toast.makeText(NewAndEditSupplyBillActivity.this, "Add success!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getShowedDate() {
        try {
            String date = txtDayBoughtValue.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
            Date c = df.parse(date);

            return df.format(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void showDatePicker(int month, int date, int year) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewAndEditSupplyBillActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                Log.d("_showDatePicker", "date: " + date);

                try {
                    Date c = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                    SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                    String formattedDate = df.format(c);
                    Log.d("_showDatePicker", "formattedDate: " + formattedDate);

                    txtDayBoughtValue.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private boolean isCurrentDate() {
        String date = txtDayBoughtValue.getText().toString();
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

    private boolean handleInput() {
        if (isInputValid(txtAddressValue.getText().toString())) {
//            Log.d("_NewAndEditSupplyBill", "btnSubmit_address_valid");
            if (isSupplyBought(Integer.parseInt(txtSupplyBoughtValue.getText().toString()))) {
//                Log.d("_NewAndEditSupplyBill", "btnSubmit_supply_valid");
                if (isMoneyBoughtValid(Integer.parseInt(txtMoneyBoughtValue.getText().toString()))) {
//                    Log.d("_NewAndEditSupplyBill", "btnSubmit_money_valid");
                    return true;
                }
            }
        }

        return false;
    }

    private void putDataBack() {
        Intent intent = new Intent();
        intent.putExtra("supplyName", txtSupplyNameValue.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setDataFromIntent() {
        String title = getIntent().getStringExtra("name");
        if (title != null) {
            txtSupplyNameValue.setText(getIntent().getStringExtra("name"));
        }
    }

    private void setCurrentDate(TextView textView) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        String formattedDate = df.format(c);

        textView.setText(formattedDate);
    }

    private boolean isInputValid(String text) {
        if (text.length() < 1) {
            return false;
        } else {
            if (text.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSupplyBought(int suppplyBought) {
        if (suppplyBought > 0) {
            return true;
        } else {
            txtSupplyBoughtValue.setError(getString(R.string.notifyErrorSupplyBought));
        }

        return false;
    }

    private boolean isMoneyBoughtValid(int moneyBought) {
        if (moneyBought > 0) {
            SharedPreferencesSetting setting = new SharedPreferencesSetting(this);

            int maxMoney = setting.getMaxMoney();
            if (moneyBought <= maxMoney) {
                return true;
            } else {
                txtMoneyBoughtValue.setError("Money bought isn't bigger than max money.");
                return false;
            }
        } else {
            txtMoneyBoughtValue.setError(getString(R.string.notifyErrorMaxMoney));
        }

        return false;
    }
}
