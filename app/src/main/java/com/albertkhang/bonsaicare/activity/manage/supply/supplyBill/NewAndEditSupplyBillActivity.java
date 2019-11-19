package com.albertkhang.bonsaicare.activity.manage.supply.supplyBill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.MainActivity;
import com.albertkhang.bonsaicare.database.SharedPreferencesSetting;

public class NewAndEditSupplyBillActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView txtTitle;

    TextView txtSupplyNameValue;
    EditText txtAddressValue;
    TextView txtBonsaiDayPlanted;
    EditText txtSupplyBoughtValue;
    EditText txtMoneyBoughtValue;

    Button btnSubmit;

    String regex = "^[a-zA-Z0-9.,/]+( [a-zA-Z0-9.,/]+)*$";

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
        txtBonsaiDayPlanted = findViewById(R.id.txtBonsaiDayPlanted);
        txtSupplyBoughtValue = findViewById(R.id.txtSupplyBoughtValue);
        txtMoneyBoughtValue = findViewById(R.id.txtMoneyBoughtValue);

        btnSubmit = findViewById(R.id.btnSubmit);

        setDataFromIntent();
    }

    private void addEvent() {
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
                    //add to Db
                    //put data back to refresh
                }
            }
        });
    }

    private boolean handleInput() {
        if (isInputValid(txtAddressValue.getText().toString())) {
            Log.d("_NewAndEditSupplyBill", "btnSubmit_address_valid");
            if (isMoneyBoughtValid(Integer.parseInt(txtMoneyBoughtValue.getText().toString()))) {
                Log.d("_NewAndEditSupplyBill", "btnSubmit_money_valid");
            } else {
                Log.d("_NewAndEditSupplyBill", "btnSubmit_money_invalid");
            }
        } else {
            Log.d("_NewAndEditSupplyBill", "btnSubmit_address_invalid");
        }

        return false;
    }

    private void setDataFromIntent() {

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

    private boolean isMoneyBoughtValid(int moneyBought) {
        if (moneyBought > 0) {
            SharedPreferencesSetting setting = new SharedPreferencesSetting(this);

            int maxMoney = setting.getMaxMoney();
            if (moneyBought > maxMoney) {
                return false;
            } else {
                txtMoneyBoughtValue.setError("Money bought isn't bigger than max money.");
                return true;
            }
        } else {
            txtMoneyBoughtValue.setError(getString(R.string.notifyErrorMaxMoney));
        }

        return false;
    }
}
