package com.albertkhang.bonsaicare.activity.manage.bonsai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.database.SharedPreferencesSetting;
import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.objectClass.PlacementItem;
import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewAndEditBonsaiActivity extends AppCompatActivity {
    EditText txtBonsaiName;
    Spinner spBonsaiType;
    ArrayList<PlacementItem> placementArrayList;//show placement in add new bonsai form
    Spinner spBonsaiPlace;
    FeedReaderDbHelper dbHelper;
    TextView txtBonsaiDayPlanted;
    Button btnAddNewBonsaiSubmit;
    ImageView btnBack;
    boolean isShowKeyboard = false;
    TextView txtDetailSettingTitle;
    ImageView imgPlaceErrorIcon;

    BonsaiItem editItem;

    String regex = "^[a-zA-Z0-9]+( [a-zA-Z0-9_]+)*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bonsai);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtBonsaiName = findViewById(R.id.txtBonsaiName);
        spBonsaiType = findViewById(R.id.spBonsaiType);
        placementArrayList = new ArrayList<>();
        spBonsaiPlace = findViewById(R.id.spBonsaiPlace);
        dbHelper = new FeedReaderDbHelper(this);
        txtBonsaiDayPlanted = findViewById(R.id.txtBonsaiDayPlanted);
        btnAddNewBonsaiSubmit = findViewById(R.id.btnAddNewBonsaiSubmit);
        btnBack = findViewById(R.id.btnBack);
        txtDetailSettingTitle = findViewById(R.id.txtDetailSettingTitle);
        imgPlaceErrorIcon = findViewById(R.id.imgPlaceErrorIcon);

        setDataForBonsaiTypeSpinner(spBonsaiType, R.array.dropdownBonsaiType);

        ManipulationDb.getAllDataPlacementTable(dbHelper, placementArrayList);
        setDataForBonsaiPlaceSpinner(spBonsaiPlace, placementArrayList);

        setCurrentDate(txtBonsaiDayPlanted);

        setEditData();
    }

    private void addEvent() {
        txtBonsaiDayPlanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCurrentDate()) {
                    showDatePicker(Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DATE),
                            Calendar.getInstance().get(Calendar.YEAR));
                } else {
                    String showedDate = getShowedDate();
                    if (showedDate != null) {
                        try {
                            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                            Date c = df.parse(txtBonsaiDayPlanted.getText().toString());

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(c);

                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int date = calendar.get(Calendar.DATE);

                            showDatePicker(month, date, year);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btnAddNewBonsaiSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBonsaiNameValid(txtBonsaiName.getText().toString())) {
                    //create item
                    BonsaiItem item = new BonsaiItem();
                    item.setBonsaiName(txtBonsaiName.getText().toString());
                    item.setBonsaiType(spBonsaiType.getSelectedItem().toString());
                    item.setBonsaiPlacementName(spBonsaiPlace.getSelectedItem().toString());
                    item.setBonsaiDayPlanted(txtBonsaiDayPlanted.getText().toString());

                    if (haveEmptyPlace(item.getBonsaiPlacementName())) {
                        if (txtDetailSettingTitle.getText().toString().equals(getIntent().getStringExtra("title"))) {
                            //update
                            item.setId(editItem.getId());
                            ManipulationDb.updateBonsai(dbHelper, item);
                            closeActivity(true);//return to preActivity and refresh Db
                            Toast.makeText(NewAndEditBonsaiActivity.this, "Edit success!", Toast.LENGTH_LONG).show();
                        } else {
                            //add
                            ManipulationDb.addNewBonsai(dbHelper, item);
                            Toast.makeText(NewAndEditBonsaiActivity.this, "Add success!", Toast.LENGTH_LONG).show();
                            closeActivity(true);//return to preActivity and refresh Db
                        }
                    } else {
                        String notify = "'" + item.getBonsaiPlacementName() + "' was full. Please choose other place.";
                        Toast.makeText(NewAndEditBonsaiActivity.this, notify, Toast.LENGTH_LONG).show();
                        imgPlaceErrorIcon.setVisibility(View.VISIBLE);
                    }

//                    if (txtDetailSettingTitle.getText().toString().equals(getIntent().getStringExtra("title"))) {
//                        //update
//                        item.setId(editItem.getId());
//                        ManipulationDb.updateBonsai(dbHelper, item);
//                        closeActivity(true);//return to preActivity and refresh Db
//                        Toast.makeText(NewAndEditBonsaiActivity.this, "Edit success!", Toast.LENGTH_LONG).show();
//                    } else {
//                        //add
//                        if (haveEmptyPlace(item.getBonsaiPlacementName())) {
//                            ManipulationDb.addNewBonsai(dbHelper, item);
//                            Toast.makeText(NewAndEditBonsaiActivity.this, "Add success!", Toast.LENGTH_LONG).show();
//                            closeActivity(true);//return to preActivity and refresh Db
//                        } else {
//                            String notify = "'" + item.getBonsaiPlacementName() + "' was full. Please choose other place.";
//                            Toast.makeText(NewAndEditBonsaiActivity.this, notify, Toast.LENGTH_LONG).show();
//                            imgPlaceErrorIcon.setVisibility(View.VISIBLE);
//                        }
//                    }
                } else {
                    //notify error
                    txtBonsaiName.setError(getString(R.string.bonsaiNameError));
                }
            }
        });

        spBonsaiPlace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imgPlaceErrorIcon.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        txtBonsaiName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowKeyboard = true;
            }
        });

        txtBonsaiName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (isBonsaiNameValid(txtBonsaiName.getText().toString())) {
                        hideKeyboard();
                    } else {
                        txtBonsaiName.setError(getString(R.string.bonsaiNameError));
                    }

                    return true;
                }

                return false;
            }
        });
//
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

    private boolean haveEmptyPlace(String bonsaiPlacementName) {
        int totalBonsai = ManipulationDb.countBonsaiInPlacement(dbHelper, bonsaiPlacementName);
        Log.d("_haveEmptyPlace", "bonsaiPlacementName: " + bonsaiPlacementName);
        Log.d("_haveEmptyPlace", "totalBonsai: " + totalBonsai);

        SharedPreferencesSetting setting = new SharedPreferencesSetting(this);
        int maxBonsaiAtPlace = setting.getMaxBonsai();
        Log.d("_haveEmptyPlace", "maxBonsaiAtPlace: " + maxBonsaiAtPlace);

        if (totalBonsai < maxBonsaiAtPlace) {
            return true;
        }

        return false;
    }

    private void setEditData() {
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            editItem = new BonsaiItem();
            txtDetailSettingTitle.setText(getString(R.string.editBonsaiTitle));

            //id
            editItem.setId(getIntent().getIntExtra("id", -1));

            //name
            txtBonsaiName.setText(getIntent().getStringExtra("name"));
            editItem.setBonsaiName(getIntent().getStringExtra("name"));
            txtBonsaiName.setSelection(txtBonsaiName.getText().length());

            //type
            String type = getIntent().getStringExtra("type");
            editItem.setBonsaiType(getIntent().getStringExtra("type"));
            String[] bonsaiTypeArray = getResources().getStringArray(R.array.dropdownBonsaiType);
            ArrayList<String> bonsaiTypeArrayList = new ArrayList<>(Arrays.asList(bonsaiTypeArray));

            for (int i = 0; i < bonsaiTypeArrayList.size(); i++) {
                if (bonsaiTypeArrayList.get(i).equals(type)) {
                    spBonsaiType.setSelection(i);
                    break;
                }
            }

            //place
            String place = getIntent().getStringExtra("place");
            editItem.setBonsaiPlacementName(getIntent().getStringExtra("place"));
            for (int i = 0; i < placementArrayList.size(); i++) {
                if (placementArrayList.get(i).getPlaccementName().equals(place)) {
                    spBonsaiPlace.setSelection(i);
                    break;
                }
            }

            txtBonsaiDayPlanted.setText(getIntent().getStringExtra("dayPlanted"));
            editItem.setBonsaiDayPlanted(getIntent().getStringExtra("dayPlanted"));
        }
    }

    private void showKeyboard() {
        isShowKeyboard = true;
        txtBonsaiName.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void hideKeyboard() {
        isShowKeyboard = false;
        txtBonsaiName.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtBonsaiName.getWindowToken(), 0);
    }

    private void closeActivity(boolean isSuccess) {
        Intent intent = new Intent();
        if (isSuccess) {
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    private boolean isBonsaiNameValid(String text) {
        if (text.length() < 1) {
            return false;
        } else {
            if (text.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        return df.format(c);
    }

    private String getShowedDate() {
        try {
            String date = txtBonsaiDayPlanted.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
            Date c = df.parse(date);

            return df.format(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isCurrentDate() {
        String date = txtBonsaiDayPlanted.getText().toString();
        Log.d("_isCurrentDate", "date: " + date);

        String currentDate = getCurrentDate();
        Log.d("_isCurrentDate", "currentDate: " + currentDate);

        if (date.equals(currentDate)) {
            return true;
        }

        return false;
    }

    private void showDatePicker(int month, int date, int year) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewAndEditBonsaiActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = month + 1 + "/" + day + "/" + year;
                Log.d("_showDatePicker", "date: " + date);

                try {
                    Date c = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                    SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
                    String formattedDate = df.format(c);
                    Log.d("_showDatePicker", "formattedDate: " + formattedDate);

                    txtBonsaiDayPlanted.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void setCurrentDate(TextView textView) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
        String formattedDate = df.format(c);

        textView.setText(formattedDate);
    }

    private void setDataForBonsaiTypeSpinner(Spinner spBonsaiType, int arrayId) {
        ArrayAdapter<CharSequence> bonsaiTypeAdapter = ArrayAdapter.createFromResource(this, arrayId, R.layout.spinner_item);
        bonsaiTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spBonsaiType.setAdapter(bonsaiTypeAdapter);
    }

    private void setDataForBonsaiPlaceSpinner(Spinner spBonsaiPlace, ArrayList<PlacementItem> placementArrayList) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < placementArrayList.size(); i++) {
            stringList.add(placementArrayList.get(i).getPlaccementName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, stringList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spBonsaiPlace.setAdapter(arrayAdapter);
    }
}