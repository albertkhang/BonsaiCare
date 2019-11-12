package com.albertkhang.bonsaicare.activity.manage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.objectClass.PlacementItem;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;
import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.bonsai.BonsaiDetailActivity;
import com.albertkhang.bonsaicare.activity.manage.bonsai.NewBonsaiActivity;
import com.albertkhang.bonsaicare.adapter.BonsaiRecyclerViewAdapter;
import com.albertkhang.bonsaicare.adapter.PlacementRecyclerViewAdapter;
import com.albertkhang.bonsaicare.adapter.SupplyRecyclerViewAdapter;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.database.ManipulationDb;

import java.util.ArrayList;

public class ManageList extends AppCompatActivity {
    TextView txtDetailTitle;
    RecyclerView recyclerView;

    BonsaiRecyclerViewAdapter bonsaiAdapter;
    PlacementRecyclerViewAdapter placementAdapter;
    SupplyRecyclerViewAdapter supplyAdapter;

    ArrayList<BonsaiItem> bonsaiArrayList;
    ArrayList<PlacementItem> placementArrayList;
    ArrayList<SupplyItem> supplyArrayList;

    FeedReaderDbHelper dbHelper;

    ImageView btnBack;
    ImageView imgManageListAddButton;
    ImageView imgManageListSearchButton;

    private static final int ADD_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        addControl();
        addEvent();
    }

    private void addControl() {
        txtDetailTitle = findViewById(R.id.txtDetailSettingTitle);
        recyclerView = findViewById(R.id.placementRecyclerView);

        bonsaiArrayList = new ArrayList<>();
        placementArrayList = new ArrayList<>();
        supplyArrayList = new ArrayList<>();

        btnBack = findViewById(R.id.btnBack);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        bonsaiAdapter = new BonsaiRecyclerViewAdapter(this);
        placementAdapter = new PlacementRecyclerViewAdapter(this);
        supplyAdapter = new SupplyRecyclerViewAdapter(this);

        dbHelper = new FeedReaderDbHelper(this);

        imgManageListAddButton = findViewById(R.id.imgManageListAddButton);
        imgManageListSearchButton = findViewById(R.id.imgManageListSearchButton);

        setIcon();
    }

    private void setIcon() {
        String s = getIntent().getStringExtra(getString(R.string.putExtraManageShowIcon));

        switch (s) {
            case "true":
                imgManageListAddButton.setVisibility(View.VISIBLE);
                imgManageListAddButton.setVisibility(View.VISIBLE);
                break;
            case "false":
                imgManageListAddButton.setVisibility(View.GONE);
                imgManageListSearchButton.setVisibility(View.GONE);
                break;
        }
    }

    private void addEvent() {
        /* set title */
        String title = getIntent().getStringExtra(getString(R.string.putExtraManageTitle));
        txtDetailTitle.setText(title);

        /* load recycler view */
        String type = getIntent().getStringExtra(getString(R.string.putExtraManageLoadList));

        if (type.equals(getString(R.string.titleBonsai))) {
            Log.d("_ManageList", "loaded Bonsai Manage");

            ManipulationDb.getAllDataBonsaiTable(dbHelper, bonsaiArrayList);

            recyclerView.setAdapter(bonsaiAdapter);
            bonsaiAdapter.uppdate(bonsaiArrayList);

            addBonsaiManageEvent();
        }

        if (type.equals(getString(R.string.titlePlacement))) {
            Log.d("_ManageList", "loaded Place Manage");

            ManipulationDb.getAllDataPlacementTable(dbHelper, placementArrayList);

            recyclerView.setAdapter(placementAdapter);
            placementAdapter.uppdate(placementArrayList);
        }

        if (type.equals(getString(R.string.titleSupplies))) {
            Log.d("_ManageList", "loaded Supply Manage");

            ManipulationDb.getAllDataSupplyTable(dbHelper, supplyArrayList);

            recyclerView.setAdapter(supplyAdapter);
            supplyAdapter.uppdate(supplyArrayList);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addBonsaiManageEvent() {
        bonsaiAdapter.setOnItemClickListener(new BonsaiRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent = new Intent(ManageList.this, BonsaiDetailActivity.class);
                intent.putExtra("id", String.valueOf(bonsaiArrayList.get(position).getId()));
                intent.putExtra("name", bonsaiArrayList.get(position).getBonsaiName());
                intent.putExtra("type", bonsaiArrayList.get(position).getBonsaiType());
                intent.putExtra("place", bonsaiArrayList.get(position).getBonsaiPlacementName());
                intent.putExtra("dayPlanted", bonsaiArrayList.get(position).getBonsaiDayPlanted());

                startActivity(intent);
            }
        });

        imgManageListAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewBonsaiActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(ManageList.this, R.string.toastAddSucess, Toast.LENGTH_LONG).show();

                ManipulationDb.getAllDataBonsaiTable(dbHelper, bonsaiArrayList);
                bonsaiAdapter.uppdate(bonsaiArrayList);
            }
        }
    }
}
