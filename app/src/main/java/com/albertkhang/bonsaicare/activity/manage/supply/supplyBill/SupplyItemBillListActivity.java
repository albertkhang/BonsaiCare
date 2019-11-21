package com.albertkhang.bonsaicare.activity.manage.supply.supplyBill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.ManageList;
import com.albertkhang.bonsaicare.activity.manage.supply.supply.NewAndEditSupplyActivity;
import com.albertkhang.bonsaicare.activity.manage.supply.supply.SupplyDetailActivity;
import com.albertkhang.bonsaicare.adapter.SupplyBilRecyclerViewAdapter;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyBillItem;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

import java.util.ArrayList;

public class SupplyItemBillListActivity extends AppCompatActivity {
    ImageView btnBack;

    TextView txtTitle;

    ImageView imgDetail;
    ImageView imgAdd;

    RecyclerView recyclerView;
    ArrayList<SupplyBillItem> supplyBillArrayList;
    SupplyBilRecyclerViewAdapter supplyBillAdapter;

    SupplyItem supplyItem;
    SupplyBillItem supplyBillItem;

    boolean isShowKeyboard = false;
    boolean needRefresh = false;

    FeedReaderDbHelper dbHelper;

    private static final int DETAIL_SUPPLY_REQUEST_CODE = 9;
    private static final int ADD_SUPPLY_BILL_REQUEST_CODE = 11;
    private static final int DETAIL_SUPPLY_BILL_REQUEST_CODE = 12;

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

        supplyBillItem = new SupplyBillItem();

        setDataFromIntent();

        dbHelper = new FeedReaderDbHelper(this);
        supplyBillArrayList = new ArrayList<>();
        ManipulationDb.getAllDataSupplyBillTable(dbHelper, supplyBillArrayList, supplyItem.getSupplyName());
        supplyBillAdapter = new SupplyBilRecyclerViewAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(supplyBillAdapter);

        supplyBillAdapter.update(supplyBillArrayList);
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

        supplyBillAdapter.setOnItemClickListener(new SupplyBilRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                startSupplyBillDetailActivity(position);
            }
        });

        supplyBillAdapter.setOnItemLongClickListener(new SupplyBilRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SupplyItemBillListActivity.this, R.style.AlertDialog);
                builder.setItems(R.array.item_long_click, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0://Edit
                                startEditSupplyBillActivity(position);
                                break;
                            case 1://Delete
                                AlertDialog.Builder builder = new AlertDialog.Builder(SupplyItemBillListActivity.this);
                                builder.setTitle("Confirm");
                                builder.setMessage("Are you sure?");
                                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(SupplyItemBillListActivity.this, R.string.toastDeleteSuccess, Toast.LENGTH_LONG).show();
                                        ManipulationDb.deleteSupplyBill(dbHelper, supplyBillArrayList.get(position));

                                        supplyBillArrayList.remove(position);
                                        supplyBillAdapter.update(supplyBillArrayList);
                                        needRefresh = true;
                                    }
                                });
                                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                builder.show();

                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void startSupplyBillDetailActivity(int position) {
        Intent intent = new Intent(SupplyItemBillListActivity.this, SupplyBillDetailActivity.class);

        intent.putExtra("id", supplyBillArrayList.get(position).getId());

        intent.putExtra("name", supplyBillArrayList.get(position).getSupplyName());
        supplyBillItem.setSupplyName(supplyBillArrayList.get(position).getSupplyName());

        intent.putExtra("address", supplyBillArrayList.get(position).getAddressBought());
        supplyBillItem.setAddressBought(supplyBillArrayList.get(position).getAddressBought());

        intent.putExtra("dayBought", supplyBillArrayList.get(position).getDayBought());
        supplyBillItem.setDayBought(supplyBillArrayList.get(position).getDayBought());

        intent.putExtra("supplyBought", supplyBillArrayList.get(position).getTotalSupplies());
        supplyBillItem.setTotalSupplies(supplyBillArrayList.get(position).getTotalSupplies());

        intent.putExtra("moneyBought", supplyBillArrayList.get(position).getTotalMoney());
        supplyBillItem.setTotalMoney(supplyBillArrayList.get(position).getTotalMoney());

        startActivityForResult(intent, DETAIL_SUPPLY_BILL_REQUEST_CODE);
    }

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
        intent.putExtra("name", supplyItem.getSupplyName());

        startActivityForResult(intent, ADD_SUPPLY_BILL_REQUEST_CODE);
    }

    private void startEditSupplyBillActivity(int position) {
        Intent intent = new Intent(SupplyItemBillListActivity.this, NewAndEditSupplyBillActivity.class);

        intent.putExtra("type", "edit");

        supplyBillItem.setId(supplyBillArrayList.get(position).getId());
        supplyBillItem.setSupplyName(supplyBillArrayList.get(position).getSupplyName());
        supplyBillItem.setAddressBought(supplyBillArrayList.get(position).getAddressBought());
        supplyBillItem.setDayBought(supplyBillArrayList.get(position).getDayBought());
        supplyBillItem.setTotalSupplies(supplyBillArrayList.get(position).getTotalSupplies());
        supplyBillItem.setTotalMoney(supplyBillArrayList.get(position).getTotalMoney());

        intent.putExtra("id", supplyBillItem.getId());

        intent.putExtra("name", supplyBillItem.getSupplyName());
        intent.putExtra("address", supplyBillItem.getAddressBought());
        intent.putExtra("dayBought", supplyBillItem.getDayBought());
        intent.putExtra("supplyBought", supplyBillItem.getTotalSupplies());
        intent.putExtra("moneyBought", supplyBillItem.getTotalMoney());

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
            case DETAIL_SUPPLY_BILL_REQUEST_CODE:
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

                    ManipulationDb.getAllDataSupplyBillTable(dbHelper, supplyBillArrayList, supplyItem.getSupplyName());
                    supplyBillAdapter.update(supplyBillArrayList);
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
