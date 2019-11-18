package com.albertkhang.bonsaicare.activity.manage.place;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;

public class PlaceDetailActivity extends AppCompatActivity {
    TextView txtIdValue;
    TextView txtNameValue;
    TextView txtTotalValue;
    ImageView imgPlacementIcon;
    Button btnDelete;
    ImageView btnBack;

    ImageView imgEditButton;

    boolean needRefresh = false;

    FeedReaderDbHelper dbHelper;

    private static final int EDIT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtIdValue = findViewById(R.id.txtIdValue);
        txtNameValue = findViewById(R.id.txtNameValue);
        txtTotalValue = findViewById(R.id.txtTotalValue);
        imgPlacementIcon = findViewById(R.id.imgPlacementIcon);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        imgEditButton = findViewById(R.id.imgEditButton);

        dbHelper = new FeedReaderDbHelper(this);

        setData();
    }

    private void addEvent() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDetailActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String placeName = txtNameValue.getText().toString();
                        if (ManipulationDb.countBonsaiInPlacement(dbHelper, placeName) > 0) {
                            Toast.makeText(PlaceDetailActivity.this, getString(R.string.notEmptyPlaceError), Toast.LENGTH_LONG).show();
                        } else {
                            if (ManipulationDb.deletePlace(dbHelper, Integer.valueOf(txtIdValue.getText().toString()))) {
                                putDataBack();
                                Toast.makeText(PlaceDetailActivity.this, R.string.toastDeleteSuccess, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PlaceDetailActivity.this, R.string.toastDeleteFail, Toast.LENGTH_LONG).show();
                            }
                        }
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

        imgEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewAndEditPlaceActivity.class);
                intent.putExtra("title", getString(R.string.editPlaceTitle));
                intent.putExtra("id", Integer.valueOf(txtIdValue.getText().toString()));
                intent.putExtra("name", txtNameValue.getText());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    private void setData() {
        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        int total = getIntent().getIntExtra("total", 0);

        txtIdValue.setText(String.valueOf(id));
        txtNameValue.setText(name);
        txtTotalValue.setText(String.valueOf(total));

        handleIcon(name);
    }

    private void handleIcon(String name) {
        if (name.equals("Balcony")) {
            imgPlacementIcon.setImageResource(R.drawable.ic_balcony);
        } else {
            if (name.equals("Window")) {
                imgPlacementIcon.setImageResource(R.drawable.ic_window);
            } else {
                if (name.equals("Gate")) {
                    imgPlacementIcon.setImageResource(R.drawable.ic_gate);
                } else {
                    imgPlacementIcon.setImageResource(R.drawable.ic_location_filled);
                }
            }
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
            case EDIT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    needRefresh = true;
                    String placeName = data.getStringExtra("name");
                    txtNameValue.setText(placeName);
                    handleIcon(placeName);
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
