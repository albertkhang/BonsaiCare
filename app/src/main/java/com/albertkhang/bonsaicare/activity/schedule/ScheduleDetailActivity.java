package com.albertkhang.bonsaicare.activity.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.albertkhang.bonsaicare.fragment.FragmentSchedule;
import com.albertkhang.bonsaicare.objectClass.ScheduleItem;

public class ScheduleDetailActivity extends AppCompatActivity {
    ScheduleItem scheduleItem;

    ImageView btnBack;

    TextView txtIdValue;
    TextView txtBonsaiNameValue;
    TextView txtDayCreatedValue;
    TextView txtDayTakeCareValue;
    TextView txtTimeTakeCareValue;
    TextView txtBonsaiPlaceValue;
    TextView txtSupplyNameValue;
    TextView txtAmountValue;

    ConstraintLayout frame_Note;
    TextView txtNoteValue;

    ImageView imgIconBonsaiPlace;
    ImageView imgIconSupplyName;

    ImageView imgIconTicked;

    Button btnDelete;

    FeedReaderDbHelper dbHelper;

    boolean needRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        addControl();
        addEvent();
    }

    private void addControl() {
        scheduleItem = new ScheduleItem();

        btnBack = findViewById(R.id.btnBack);

        txtIdValue = findViewById(R.id.txtIdValue);
        txtBonsaiNameValue = findViewById(R.id.txtBonsaiNameValue);
        txtDayCreatedValue = findViewById(R.id.txtDayCreatedValue);
        txtDayTakeCareValue = findViewById(R.id.txtDayTakeCareValue);
        txtTimeTakeCareValue = findViewById(R.id.txtTimeTakeCareValue);
        txtBonsaiPlaceValue = findViewById(R.id.txtBonsaiPlaceValue);
        txtSupplyNameValue = findViewById(R.id.txtSupplyNameValue);
        txtAmountValue = findViewById(R.id.txtAmountValue);

        frame_Note = findViewById(R.id.frame_Note);
        txtNoteValue = findViewById(R.id.txtNoteValue);

        imgIconTicked = findViewById(R.id.imgIconTicked);

        imgIconBonsaiPlace = findViewById(R.id.imgIconBonsaiPlace);
        imgIconSupplyName = findViewById(R.id.imgIconSupplyName);

        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new FeedReaderDbHelper(this);

        getDataFromIntent();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleDetailActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "Delete", Toast.LENGTH_SHORT).show();
                        //Delete
                        ManipulationDb.deleteSchedule(dbHelper, scheduleItem);

                        needRefresh = true;
                        onBackPressed();
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
    }

    private void putDataBack() {
        if (needRefresh) {
            Log.d("_ScheduleDetailActivity", "PutDataBack");
            Intent intent = new Intent(this, FragmentSchedule.class);
            setResult(Activity.RESULT_OK, intent);
        }
    }

    @Override
    public void onBackPressed() {
        putDataBack();

        super.onBackPressed();
    }

    private void getDataFromIntent() {
        //get
        scheduleItem.setId(getIntent().getIntExtra("id", -1));

        scheduleItem.setBonsaiName(getIntent().getStringExtra("bonsaiName"));

        scheduleItem.setDayCreated(getIntent().getStringExtra("dayCreated"));
        scheduleItem.setDayTakeCare(getIntent().getStringExtra("dayTakeCare"));

        scheduleItem.setTimeTakeCare(getIntent().getStringExtra("timeTakeCare"));
        scheduleItem.setBonsaiPlace(getIntent().getStringExtra("bonsaiPlace"));

        scheduleItem.setSupplyName(getIntent().getStringExtra("supplyName"));
        scheduleItem.setAmount(getIntent().getIntExtra("amount", 0));

        scheduleItem.setNote(getIntent().getStringExtra("note"));
        if (scheduleItem.getNote().length() > 0) {
            scheduleItem.setHaveNote(true);
        } else {
            scheduleItem.setHaveNote(false);
        }

        scheduleItem.setTicked(getIntent().getBooleanExtra("isTicked", false));

        //set
        txtIdValue.setText(String.valueOf(scheduleItem.getId()));

        txtBonsaiNameValue.setText(scheduleItem.getBonsaiName());

        txtDayCreatedValue.setText(scheduleItem.getDayCreated());
        txtDayTakeCareValue.setText(scheduleItem.getDayTakeCare());

        txtTimeTakeCareValue.setText(scheduleItem.getTimeTakeCare());
        txtBonsaiPlaceValue.setText(scheduleItem.getBonsaiPlace());

        txtSupplyNameValue.setText(scheduleItem.getSupplyName());
        txtAmountValue.setText(String.valueOf(scheduleItem.getAmount()));

        //note frame
        if (scheduleItem.isHaveNote()) {
            txtNoteValue.setText(scheduleItem.getNote());
            frame_Note.setVisibility(View.VISIBLE);
        } else {
            frame_Note.setVisibility(View.GONE);
        }

        //complete icon
        if (scheduleItem.isTicked()) {
            imgIconTicked.setVisibility(View.VISIBLE);
        } else {
            imgIconTicked.setVisibility(View.GONE);
        }

        //bonsaiPlace icon
        if (scheduleItem.getBonsaiPlace().equals("Balcony")) {
            imgIconBonsaiPlace.setImageResource(R.drawable.ic_balcony);
        } else {
            if (scheduleItem.getBonsaiPlace().equals("Window")) {
                imgIconBonsaiPlace.setImageResource(R.drawable.ic_window);
            } else {
                if (scheduleItem.getBonsaiPlace().equals("Gate")) {
                    imgIconBonsaiPlace.setImageResource(R.drawable.ic_gate);
                } else {
                    imgIconBonsaiPlace.setImageResource(R.drawable.ic_icon_placement);
                }
            }
        }

        //supplyName icon
        if (scheduleItem.getSupplyName().equals("Water")) {
            imgIconSupplyName.setImageResource(R.drawable.ic_water);
        } else {
            if (scheduleItem.getSupplyName().equals("Nitrogen fertilizer")) {
                imgIconSupplyName.setImageResource(R.drawable.ic_nitrogen_fertilizer);
            } else {
                imgIconSupplyName.setImageResource(R.drawable.ic_icon_supplies);
            }
        }
    }
}
