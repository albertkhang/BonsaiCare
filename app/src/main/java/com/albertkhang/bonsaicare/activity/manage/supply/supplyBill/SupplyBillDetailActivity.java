package com.albertkhang.bonsaicare.activity.manage.supply.supplyBill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    }

    private void setDataFromIntent() {
        txtIdValue.setText(String.valueOf(getIntent().getIntExtra("id", -1)));
        txtNameValue.setText(getIntent().getStringExtra("name"));
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

    @Override
    public void onBackPressed() {
        if (needRefresh){
            putDataBack();
        }

        super.onBackPressed();
    }
}
