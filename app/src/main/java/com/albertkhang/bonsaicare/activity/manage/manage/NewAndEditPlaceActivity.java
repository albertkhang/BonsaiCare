package com.albertkhang.bonsaicare.activity.manage.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.PlacementItem;

public class NewAndEditPlaceActivity extends AppCompatActivity {
    String regex = "^[a-zA-Z0-9]+( [a-zA-Z0-9_]+)*$";
    boolean isShowKeyboard = false;

    TextView txtDetailTitle;
    EditText txtPlaceName;
    Button btnSubmit;
    ImageView btnBack;

    PlacementItem placementItem;

    FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_and_edit_place);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtDetailTitle = findViewById(R.id.txtDetailTitle);
        txtPlaceName = findViewById(R.id.txtPlaceName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        dbHelper = new FeedReaderDbHelper(this);

        placementItem = new PlacementItem();

        setDataIntent();
    }

    private void setDataIntent() {
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            txtDetailTitle.setText(title);

            placementItem.setId(getIntent().getIntExtra("id", -1));

            txtPlaceName.setText(getIntent().getStringExtra("name"));
            txtPlaceName.setSelection(txtPlaceName.getText().length());
            placementItem.setPlacementName(txtPlaceName.getText().toString());
        }
    }

    private void addEvent() {
        txtPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowKeyboard = true;
//                showKeyboard();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaceNameValid(txtPlaceName.getText().toString())) {
                    placementItem.setPlacementName(txtPlaceName.getText().toString());

                    if (txtDetailTitle.getText().toString().equals(getIntent().getStringExtra("title"))) {
                        ManipulationDb.updatePlace(dbHelper, placementItem);
                        Toast.makeText(NewAndEditPlaceActivity.this, "Edit success!", Toast.LENGTH_LONG).show();
                    } else {
                        ManipulationDb.addNewPlace(dbHelper, placementItem);
                        Toast.makeText(NewAndEditPlaceActivity.this, "Add success!", Toast.LENGTH_LONG).show();
                    }

                    putDataToPreActivity(placementItem);
                } else {
                    txtPlaceName.setError(getString(R.string.nameError));
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowKeyboard) {
                    hideKeyboard();
                } else {
                    finish();
                }
            }
        });
    }

    private void putDataToPreActivity(PlacementItem item) {
        Intent intent = new Intent(this, PlaceDetailActivity.class);

        intent.putExtra("name", item.getPlacementName());

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    private boolean isPlaceNameValid(String text) {
        if (text.length() < 1) {
            return false;
        } else {
            if (text.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    private void showKeyboard() {
        isShowKeyboard = true;
        txtPlaceName.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void hideKeyboard() {
        isShowKeyboard = false;
        txtPlaceName.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtPlaceName.getWindowToken(), 0);
    }
}
