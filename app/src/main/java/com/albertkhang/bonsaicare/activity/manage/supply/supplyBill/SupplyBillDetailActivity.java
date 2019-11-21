package com.albertkhang.bonsaicare.activity.manage.supply.supplyBill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyBillItem;

public class SupplyBillDetailActivity extends AppCompatActivity {
    ImageView btnBack;
    ImageView imgEditButton;

    TextView txtIdValue;
    TextView txtNameValue;
    ImageView imgSupplyIcon;
    TextView txtAddressValue;
    TextView txtDayBoughtValue;
    TextView txtSupplyBoughtValue;
    TextView txtMoneyBoughtValue;

    Button btnDelete;

    SupplyBillItem supplyBillItem;
    FeedReaderDbHelper dbHelper;

    boolean needRefresh = false;
    private static final int ADD_SUPPLY_BILL_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_bill_detail);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        imgEditButton = findViewById(R.id.imgEditButton);

        txtIdValue = findViewById(R.id.txtIdValue);
        txtNameValue = findViewById(R.id.txtNameValue);
        imgSupplyIcon = findViewById(R.id.imgSupplyIcon);
        txtAddressValue = findViewById(R.id.txtAddressValue);
        txtDayBoughtValue = findViewById(R.id.txtDayBoughtValue);
        txtSupplyBoughtValue = findViewById(R.id.txtSupplyBoughtValue);
        txtMoneyBoughtValue = findViewById(R.id.txtMoneyBoughtValue);

        btnDelete = findViewById(R.id.btnDelete);

        supplyBillItem = new SupplyBillItem();
        dbHelper = new FeedReaderDbHelper(this);

        setDataFromIntent();
    }

    private void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needRefresh = true;
                ManipulationDb.deleteSupplyBill(dbHelper, supplyBillItem);
                onBackPressed();
            }
        });

        imgEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditSupplyBillActivity();
            }
        });
    }

    private void startEditSupplyBillActivity() {
        Intent intent = new Intent(SupplyBillDetailActivity.this, NewAndEditSupplyBillActivity.class);

        intent.putExtra("type", "edit");

        intent.putExtra("id", supplyBillItem.getId());
        Log.d("_putExtra", "id: " + supplyBillItem.getId());
        intent.putExtra("name", supplyBillItem.getSupplyName());
        intent.putExtra("address", supplyBillItem.getAddressBought());
        intent.putExtra("dayBought", supplyBillItem.getDayBought());
        intent.putExtra("supplyBought", supplyBillItem.getTotalSupplies());
        intent.putExtra("moneyBought", supplyBillItem.getTotalMoney());

        startActivityForResult(intent, ADD_SUPPLY_BILL_REQUEST_CODE);
    }

    private void setDataFromIntent() {
        txtIdValue.setText(String.valueOf(getIntent().getIntExtra("id", -1)));
        txtNameValue.setText(getIntent().getStringExtra("name"));
        handleIcon();
        txtAddressValue.setText(getIntent().getStringExtra("address"));
        txtDayBoughtValue.setText(getIntent().getStringExtra("dayBought"));
        txtSupplyBoughtValue.setText(String.valueOf(getIntent().getIntExtra("supplyBought", 0)));
        txtMoneyBoughtValue.setText(String.valueOf(getIntent().getIntExtra("moneyBought", 0)));

        supplyBillItem.setId(getIntent().getIntExtra("id", -1));
        supplyBillItem.setSupplyName(getIntent().getStringExtra("name"));
        supplyBillItem.setAddressBought(getIntent().getStringExtra("address"));
        supplyBillItem.setDayBought(getIntent().getStringExtra("dayBought"));
        supplyBillItem.setTotalSupplies(getIntent().getIntExtra("supplyBought", 0));
        supplyBillItem.setTotalMoney(getIntent().getIntExtra("moneyBought", 0));
    }

    private void putDataBack() {
        Intent intent = new Intent();

        setResult(Activity.RESULT_OK, intent);
    }

    private void handleIcon() {
        if (txtNameValue.getText().toString().equals("Water")) {
            imgSupplyIcon.setImageResource(R.drawable.ic_water);
        } else {
            if (txtNameValue.getText().toString().equals("Nitrogen fertilizer")) {
                imgSupplyIcon.setImageResource(R.drawable.ic_nitrogen_fertilizer);
            } else {
                imgSupplyIcon.setImageResource(R.drawable.ic_supplies_filled);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (needRefresh) {
            putDataBack();
        }

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_SUPPLY_BILL_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    supplyBillItem.setSupplyName(data.getStringExtra("name"));
                    supplyBillItem.setAddressBought(data.getStringExtra("address"));
                    supplyBillItem.setDayBought(data.getStringExtra("dayBought"));
                    supplyBillItem.setTotalSupplies(data.getIntExtra("supplyBought", 0));
                    supplyBillItem.setTotalMoney(data.getIntExtra("moneyBought", 0));

                    txtNameValue.setText(supplyBillItem.getSupplyName());
                    txtAddressValue.setText(supplyBillItem.getAddressBought());
                    txtDayBoughtValue.setText(supplyBillItem.getDayBought());
                    txtSupplyBoughtValue.setText(String.valueOf(supplyBillItem.getTotalSupplies()));
                    txtMoneyBoughtValue.setText(String.valueOf(supplyBillItem.getTotalMoney()));

                    needRefresh = true;
                }
                break;
        }
    }
}
