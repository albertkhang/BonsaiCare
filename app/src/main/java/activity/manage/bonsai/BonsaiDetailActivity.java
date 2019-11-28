package com.albertkhang.bonsaicare.activity.manage.bonsai;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;

public class BonsaiDetailActivity extends AppCompatActivity {
    TextView txtBonsaiIdValue;
    TextView txtBonsaiNameValue;
    TextView txtBonsaiTypeValue;
    TextView txtBonsaiPlacementValue;
    TextView txtBonsaiDayPlantedValue;

    ImageView imgBonsaiTypeIcon;
    ImageView imgBonsaiPlacementIcon;

    ImageView btnBack;

    Button btnDeleteBonsaiSubmit;
    ImageView imgEditDetailButton;

    FeedReaderDbHelper dbHelper;

    boolean needRefresh = false;

    private static final int EDIT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonsai_detail);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtBonsaiIdValue = findViewById(R.id.txtIdValue);
        txtBonsaiNameValue = findViewById(R.id.txtNameValue);
        txtBonsaiTypeValue = findViewById(R.id.txtBonsaiTypeValue);
        txtBonsaiPlacementValue = findViewById(R.id.txtBonsaiPlacementValue);
        txtBonsaiDayPlantedValue = findViewById(R.id.txtBonsaiDayPlantedValue);

        imgBonsaiTypeIcon = findViewById(R.id.imgBonsaiTypeIcon);
        imgBonsaiPlacementIcon = findViewById(R.id.imgPlacementIcon);

        btnBack = findViewById(R.id.btnBack);

        btnDeleteBonsaiSubmit = findViewById(R.id.btnSubmit);
        imgEditDetailButton = findViewById(R.id.imgEditButton);

        dbHelper = new FeedReaderDbHelper(this);

        setDataPassed();
    }

    private void addEvent() {
        btnDeleteBonsaiSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alertDialog to confirm
                //confirmed -> delete
                AlertDialog.Builder builder = new AlertDialog.Builder(BonsaiDetailActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ManipulationDb.deleteBonsai(dbHelper, Integer.parseInt(txtBonsaiIdValue.getText().toString()))) {
                            Toast.makeText(BonsaiDetailActivity.this, "Delete success!", Toast.LENGTH_LONG).show();
                            putDataBack();
                        } else {
                            Toast.makeText(BonsaiDetailActivity.this, "Delete failed!", Toast.LENGTH_LONG).show();
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

        imgEditDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult
                Intent intent = new Intent(getApplicationContext(), NewAndEditBonsaiActivity.class);
                intent.putExtra("title", getString(R.string.editBonsaiTitle));
                intent.putExtra("id", Integer.parseInt(txtBonsaiIdValue.getText().toString()));
                intent.putExtra("name", txtBonsaiNameValue.getText());
                intent.putExtra("type", txtBonsaiTypeValue.getText());
                intent.putExtra("place", txtBonsaiPlacementValue.getText());
                intent.putExtra("dayPlanted", txtBonsaiDayPlantedValue.getText());

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

    private void putDataBack() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case EDIT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(BonsaiDetailActivity.this, R.string.toastEditSuccess, Toast.LENGTH_LONG).show();

                    String name = data.getStringExtra("name");
                    String type = data.getStringExtra("type");
                    String place = data.getStringExtra("place");
                    String dayPlanted = data.getStringExtra("dayPlanted");

                    Log.d("_onActivityResult", name);

                    txtBonsaiNameValue.setText(name);
                    txtBonsaiTypeValue.setText(type);
                    txtBonsaiPlacementValue.setText(place);
                    txtBonsaiDayPlantedValue.setText(dayPlanted);
                    handleIcon();

                    needRefresh = true;
                }
                break;
        }
    }

    private void setDataPassed() {
        txtBonsaiIdValue.setText(getIntent().getStringExtra("id"));
        txtBonsaiNameValue.setText(getIntent().getStringExtra("name"));
        txtBonsaiTypeValue.setText(getIntent().getStringExtra("type"));
        txtBonsaiPlacementValue.setText(getIntent().getStringExtra("place"));
        txtBonsaiDayPlantedValue.setText(getIntent().getStringExtra("dayPlanted"));

        handleIcon();
    }

    private void handleIcon() {
        //handle BonsaiTypeIcon
        if (txtBonsaiTypeValue.getText().equals("Need Light")) {
            imgBonsaiTypeIcon.setImageResource(R.drawable.ic_light);
        } else {
            if (txtBonsaiTypeValue.getText().equals("Need Shade")) {
                imgBonsaiTypeIcon.setImageResource(R.drawable.ic_shade);
            }
        }

        //handle BonsaiPlacement
        if (txtBonsaiPlacementValue.getText().equals("Balcony")) {
            imgBonsaiPlacementIcon.setImageResource(R.drawable.ic_balcony);
        } else {
            if (txtBonsaiPlacementValue.getText().equals("Window")) {
                imgBonsaiPlacementIcon.setImageResource(R.drawable.ic_window);
            } else {
                if (txtBonsaiPlacementValue.getText().equals("Gate")) {
                    imgBonsaiPlacementIcon.setImageResource(R.drawable.ic_gate);
                } else {
                    imgBonsaiPlacementIcon.setImageResource(R.drawable.ic_location_filled);
                }
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
