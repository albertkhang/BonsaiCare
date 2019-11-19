package com.albertkhang.bonsaicare.activity.manage.supply.supply_bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.supply.SupplyDetailActivity;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

public class SupplyItemBillListActivity extends AppCompatActivity {
    ImageView btnBack;

    TextView txtTitle;

    ImageView imgDetail;
    ImageView imgAdd;

    RecyclerView recyclerView;

    TextView txtTotalSupplyValue;

    SupplyItem supplyItem;

    boolean isShowKeyboard = false;

    private static final int DETAIL_SUPPLY_REQUEST_CODE = 1;
    private static final int ADD_SUPPLY_BILL_REQUEST_CODE = 2;

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

        txtTotalSupplyValue = findViewById(R.id.txtTotalSupplyValue);

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
//                if (isShowKeyboard) {
//                    TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout), false);
////                    hideKeyboard();
////                    txt_search_frame.setText("");
//                } else {
//                    finish();
//                }
                finish();
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
        Intent intent = new Intent(this, AddAndEditSupplyBillActivity.class);
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
        }
    }
}
