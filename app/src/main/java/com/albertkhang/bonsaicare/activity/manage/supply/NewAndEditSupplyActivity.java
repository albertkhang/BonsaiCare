package com.albertkhang.bonsaicare.activity.manage.supply;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.place.NewAndEditPlaceActivity;
import com.albertkhang.bonsaicare.activity.manage.place.PlaceDetailActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

public class NewAndEditSupplyActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView txtDetailTitle;

    EditText txtNameValue;
    EditText txtUnitValue;

    Button btnSubmit;

    String regex = "^[a-zA-Z0-9]+( [a-zA-Z0-9_]+)*$";
    SupplyItem supplyItem;
    FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_and_edit_supply);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        txtDetailTitle = findViewById(R.id.txtDetailTitle);

        txtNameValue = findViewById(R.id.txtNameValue);
        txtUnitValue = findViewById(R.id.txtUnitValue);

        btnSubmit = findViewById(R.id.btnSubmit);

        supplyItem = new SupplyItem();
        dbHelper = new FeedReaderDbHelper(this);

        setDataFromIntent();
    }

    private void setDataFromIntent() {
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            txtDetailTitle.setText(title);

            supplyItem.setId(getIntent().getIntExtra("id", -1));
            supplyItem.setSupplyName(getIntent().getStringExtra("name"));
            supplyItem.setSupplyUnit(getIntent().getStringExtra("unit"));
            supplyItem.setTotal(getIntent().getIntExtra("total", 0));
        }
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
                if (isInputValid(txtNameValue.getText().toString())) {
                    if (isInputValid(txtUnitValue.getText().toString())) {
                        supplyItem.setSupplyName(txtNameValue.getText().toString());
                        supplyItem.setSupplyUnit(txtUnitValue.getText().toString());

                        if (txtDetailTitle.getText().toString().equals(getIntent().getStringExtra("title"))) {
                            ManipulationDb.updateSupply(dbHelper, supplyItem);
                            Toast.makeText(NewAndEditSupplyActivity.this, "Edit success!", Toast.LENGTH_LONG).show();
                        } else {
                            ManipulationDb.addNewSupply(dbHelper, supplyItem);
                            Toast.makeText(NewAndEditSupplyActivity.this, "Add success!", Toast.LENGTH_LONG).show();
                        }

                        putDataBack(supplyItem);
                    } else {
                        txtUnitValue.setError(getString(R.string.nameError));
                    }
                } else {
                    txtNameValue.setError(getString(R.string.nameError));
                }
            }
        });
    }

    private void putDataBack(SupplyItem item) {
        Intent intent = new Intent(this, SupplyDetailActivity.class);

        intent.putExtra("name", item.getSupplyName());
        intent.putExtra("unit", item.getSupplyUnit());

        setResult(Activity.RESULT_OK, intent);

        finish();
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
}
