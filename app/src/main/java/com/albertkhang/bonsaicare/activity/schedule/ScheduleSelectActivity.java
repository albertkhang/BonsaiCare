package com.albertkhang.bonsaicare.activity.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.adapter.BonsaiRecyclerViewAdapter;
import com.albertkhang.bonsaicare.adapter.SupplyRecyclerViewAdapter;
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

    ArrayList<BonsaiItem> bonsaiItem;
    ArrayList<SupplyItem> supplyItem;

    BonsaiRecyclerViewAdapter bonsaiAdapter;
    SupplyRecyclerViewAdapter supplyAdapter;

    String type = "";

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

        bonsaiItem = new ArrayList<>();
        supplyItem = new ArrayList<>();

        bonsaiAdapter = new BonsaiRecyclerViewAdapter(this);
        supplyAdapter = new SupplyRecyclerViewAdapter(this);

        setDataFromIntent();
    }

    private void addEvent() {

    }

    private void setDataFromIntent() {
        txtTitle.setText(getIntent().getStringExtra("title"));

        type = getIntent().getStringExtra("type");
    }
}
