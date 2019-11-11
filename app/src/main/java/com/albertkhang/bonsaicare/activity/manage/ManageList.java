package com.albertkhang.bonsaicare.activity.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.ObjectClass.PlacementItem;
import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.adapter.PlacementRecyclerViewAdapter;
import com.albertkhang.bonsaicare.animation.TopBarAnimation;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;

import java.util.ArrayList;

public class ManageList extends AppCompatActivity {
    TextView txtDetailTitle;
    RecyclerView placementRecyclerView;
    PlacementRecyclerViewAdapter adapter;
    ArrayList<PlacementItem> placementArrayList;
    FeedReaderDbHelper dbHelper;

    ImageView btnBack;
    ImageView imgManageListAddButton;
    ImageView imgManageListSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtDetailTitle = findViewById(R.id.txtDetailSettingTitle);
        placementRecyclerView = findViewById(R.id.placementRecyclerView);
        placementArrayList = new ArrayList<>();
        btnBack = findViewById(R.id.btnBack);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        placementRecyclerView.setLayoutManager(manager);
        adapter = new PlacementRecyclerViewAdapter(this);
        placementRecyclerView.setAdapter(adapter);

        dbHelper = new FeedReaderDbHelper(this);

        imgManageListAddButton = findViewById(R.id.imgManageListAddButton);
        imgManageListSearchButton = findViewById(R.id.imgManageListSearchButton);
    }

    private void addEvent() {
        /* set title */
        String title = getIntent().getStringExtra(getString(R.string.putExtraManageTitle));
        txtDetailTitle.setText(title);

        /* load recycler view */
        String type = getIntent().getStringExtra(getString(R.string.putExtraManageLoadList));
        if (type.equals(getString(R.string.titlePlacement))) {
            Log.d("_ManageList", "loaded Placemanagement");

            ManipulationDb.getAllDataPlacementTable(dbHelper, placementArrayList);
            adapter.uppdate(placementArrayList);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgManageListAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewBonsaiActivity.class);
                startActivity(intent);
            }
        });

        imgManageListSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
