package com.albertkhang.bonsaicare.activity.manage.supply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.ManageList;
import com.albertkhang.bonsaicare.activity.manage.place.NewAndEditPlaceActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

public class SupplyDetailActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView txtDetailTitle;
    ImageView imgEditButton;

    TextView txtIdValue;
    TextView txtNameValue;
    ImageView imgSupplyIcon;
    TextView txtUnitValue;
    TextView txtTotalValue;

    Button btnDelete;

    FeedReaderDbHelper dbHelper;
    SupplyItem supplyItem;
    boolean needRefresh = false;
    private static final int EDIT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_detail);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        txtDetailTitle = findViewById(R.id.txtDetailTitle);
        imgEditButton = findViewById(R.id.imgEditButton);

        txtIdValue = findViewById(R.id.txtIdValue);
        txtNameValue = findViewById(R.id.txtNameValue);
        imgSupplyIcon = findViewById(R.id.imgSupplyIcon);
        txtUnitValue = findViewById(R.id.txtUnitValue);
        txtTotalValue = findViewById(R.id.txtTotalValue);

        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new FeedReaderDbHelper(this);
        supplyItem = new SupplyItem();

        setDataFromIntent();
    }

    private void addEvent() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SupplyDetailActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SupplyDetailActivity.this, R.string.toastDeleteSuccess, Toast.LENGTH_LONG).show();
                        ManipulationDb.deleteSupply(dbHelper, supplyItem.getId());

                        putDataBack();
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        imgEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewAndEditSupplyActivity.class);
                intent.putExtra("title", getString(R.string.editSupplyTitle));
                intent.putExtra("id", supplyItem.getId());
                intent.putExtra("name", supplyItem.getSupplyName());
                intent.putExtra("unit", supplyItem.getSupplyUnit());
                intent.putExtra("total", supplyItem.getTotal());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (needRefresh) {
                    putDataBack();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EDIT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    needRefresh = true;
                    supplyItem.setSupplyName(data.getStringExtra("name"));
                    supplyItem.setSupplyUnit(data.getStringExtra("unit"));
                    supplyItem.setTotal(data.getIntExtra("total", 0));

                    txtNameValue.setText(supplyItem.getSupplyName());
                    txtUnitValue.setText(supplyItem.getSupplyUnit());
                    txtTotalValue.setText(String.valueOf(supplyItem.getTotal()));
                }
                break;
        }
    }

    private void putDataBack() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setDataFromIntent() {
        supplyItem.setId(getIntent().getIntExtra("id", 0));
        supplyItem.setSupplyName(getIntent().getStringExtra("name"));
        supplyItem.setSupplyUnit(getIntent().getStringExtra("unit"));
        supplyItem.setTotal(getIntent().getIntExtra("total", 0));

        txtIdValue.setText(String.valueOf(supplyItem.getId()));
        txtNameValue.setText(supplyItem.getSupplyName());
        txtUnitValue.setText(supplyItem.getSupplyUnit());
        txtTotalValue.setText(String.valueOf(supplyItem.getTotal()));

        handleIcon(supplyItem.getSupplyName());
    }

    private void handleIcon(String name){
        if (name.equals("Water")) {
            imgSupplyIcon.setImageResource(R.drawable.ic_water);
        } else {
            if (name.equals("Nitrogen fertilizer")) {
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
}
