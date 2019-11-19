package com.albertkhang.bonsaicare.activity.manage.supply.supplyBill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.supply.supply.SupplyDetailActivity;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

public class SupplyItemBillListActivity extends AppCompatActivity {
    ImageView btnBack;

    TextView txtTitle;

    ImageView imgDetail;
    ImageView imgAdd;

    RecyclerView recyclerView;

    TextView txtTotalSupplyValue;
    TextView txtUnit;

    SupplyItem supplyItem;

    boolean isShowKeyboard = false;
    boolean needRefresh = false;

    private static final int DETAIL_SUPPLY_REQUEST_CODE = 9;
    private static final int ADD_SUPPLY_BILL_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_item_bill_list);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);

        txtTitle = findViewById(R.id.txtTitle);

        imgDetail = findViewById(R.id.imgDetail);
        imgAdd = findViewById(R.id.imgAdd);

        recyclerView = findViewById(R.id.recyclerView);

        txtTotalSupplyValue = findViewById(R.id.txtSupplyBoughtValue);
        txtUnit = findViewById(R.id.txtUnit);

        setDataFromIntent();
    }

    private void addEvent() {
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddSupplyBillActivity();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSupplyDetailActivity();
            }
        });
    }

//    private void hideKeyboard() {
//        isShowKeyboard = false;
//        btnBack.setImageResource(R.drawable.ic_left);
//        txt_search_frame.clearFocus();
//        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(txt_search_frame.getWindowToken(), 0);
//    }

    private void startSupplyDetailActivity() {
        Intent intent = new Intent(SupplyItemBillListActivity.this, SupplyDetailActivity.class);

        intent.putExtra("id", supplyItem.getId());
        intent.putExtra("name", supplyItem.getSupplyName());
        intent.putExtra("unit", supplyItem.getSupplyUnit());
        intent.putExtra("total", supplyItem.getTotal());

        startActivityForResult(intent, DETAIL_SUPPLY_REQUEST_CODE);
    }

    private void startAddSupplyBillActivity() {
        Intent intent = new Intent(this, NewAndEditSupplyBillActivity.class);
        startActivityForResult(intent, ADD_SUPPLY_BILL_REQUEST_CODE);
    }

    private void setDataFromIntent() {
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            txtTitle.setText(title);

            supplyItem = new SupplyItem();
            supplyItem.setId(getIntent().getIntExtra("id", -1));
            supplyItem.setSupplyName(getIntent().getStringExtra("name"));
            supplyItem.setSupplyUnit(getIntent().getStringExtra("unit"));
            supplyItem.setTotal(getIntent().getIntExtra("total", 0));

            txtTotalSupplyValue.setText(String.valueOf(supplyItem.getTotal()));
            txtUnit.setText(supplyItem.getSupplyUnit());
        }
    }

    private void putDataBack() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DETAIL_SUPPLY_REQUEST_CODE:
            case ADD_SUPPLY_BILL_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    needRefresh = true;
                    if (data.getIntExtra("close", -1) == 1) {
                        onBackPressed();
                    }

                    String title = data.getStringExtra("name");
                    if (title != null) {
                        txtTitle.setText(title);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (needRefresh) {
            putDataBack();
        }

        super.onBackPressed();
    }
}
