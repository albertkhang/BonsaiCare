package com.albertkhang.bonsaicare.activity.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.adapter.BonsaiRecyclerViewAdapter;
import com.albertkhang.bonsaicare.adapter.SupplyRecyclerViewAdapter;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;
import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;

import java.util.ArrayList;

public class ScheduleSelectActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView txtTitle;
    ImageView imgSearch;

    ImageView search_frame;
    EditText txtSearchValue;
    ImageView imgClearText;

    RecyclerView recyclerView;

    ArrayList<BonsaiItem> bonsaiArrayList;
    ArrayList<SupplyItem> supplyArrayList;

    BonsaiRecyclerViewAdapter bonsaiAdapter;
    SupplyRecyclerViewAdapter supplyAdapter;

    FeedReaderDbHelper dbHelper;

    String type = "";
    boolean isShowKeyboard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_select);

        addControl();
        addEvent();
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        txtTitle = findViewById(R.id.txtTitle);
        imgSearch = findViewById(R.id.imgSearch);

        search_frame = findViewById(R.id.search_frame);
        txtSearchValue = findViewById(R.id.txtSearchValue);
        imgClearText = findViewById(R.id.imgClearText);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        bonsaiArrayList = new ArrayList<>();
        supplyArrayList = new ArrayList<>();

        bonsaiAdapter = new BonsaiRecyclerViewAdapter(this);
        supplyAdapter = new SupplyRecyclerViewAdapter(this);

        dbHelper = new FeedReaderDbHelper(this);

        setDataFromIntent();
    }

    private void addEvent() {
        if (type.equals("bonsai")) {
            recyclerView.setAdapter(bonsaiAdapter);
            ManipulationDb.getAllDataBonsaiTable(dbHelper, bonsaiArrayList);
            bonsaiAdapter.update(bonsaiArrayList);

            addBonsaiEvent();
        } else {
            if (type.equals("supply")) {
                recyclerView.setAdapter(supplyAdapter);
                ManipulationDb.getAllDataSupplyTable(dbHelper, supplyArrayList);
                supplyAdapter.update(supplyArrayList);

                addSupplyEvent();
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addBonsaiEvent() {
        bonsaiAdapter.setOnItemClickListener(new BonsaiRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                putBonsaiDataBack(position);
            }
        });
    }

    private void addSupplyEvent() {
        supplyAdapter.setOnItemClickListener(new SupplyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                putSupplyDataBack(position);
            }
        });
    }

    private void putSupplyDataBack(int position) {
        Intent intent = new Intent(this, NewAndEditScheduleActivity.class);

        intent.putExtra("supplyName", supplyArrayList.get(position).getSupplyName());

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void putBonsaiDataBack(int position) {
        Intent intent = new Intent(this, NewAndEditScheduleActivity.class);

        intent.putExtra("bonsaiName", bonsaiArrayList.get(position).getBonsaiName());
        intent.putExtra("bonsaiPlace", bonsaiArrayList.get(position).getBonsaiPlacementName());

        Log.d("_putBonsaiDataBack", "bonsaiName:" +bonsaiArrayList.get(position).getBonsaiName());

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setDataFromIntent() {
        txtTitle.setText(getIntent().getStringExtra("title"));

        type = getIntent().getStringExtra("type");
    }

    @Override
    public void onBackPressed() {
        if (isShowKeyboard) {
            hideKeyboard();
        } else {
            super.onBackPressed();
        }
    }

    private void hideKeyboard() {
        isShowKeyboard = false;
        btnBack.setImageResource(R.drawable.ic_left);
        txtSearchValue.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtSearchValue.getWindowToken(), 0);
    }
}
