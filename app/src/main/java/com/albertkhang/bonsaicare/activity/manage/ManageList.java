package com.albertkhang.bonsaicare.activity.manage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.albertkhang.bonsaicare.animation.TopBarAnimation;
import com.albertkhang.bonsaicare.objectClass.BonsaiItem;
import com.albertkhang.bonsaicare.objectClass.PlacementItem;
import com.albertkhang.bonsaicare.objectClass.SupplyItem;
import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.manage.bonsai.BonsaiDetailActivity;
import com.albertkhang.bonsaicare.activity.manage.bonsai.NewAndEditBonsaiActivity;
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

    EditText txt_search_frame;
    boolean isShowKeyboard = false;

    private static final int ADD_REQUEST_CODE = 1;
    private static final int EDIT_REQUEST_CODE = 2;
    private static final int EDIT_ACTIVITY_REQUEST_CODE = 3;

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

        txt_search_frame = findViewById(R.id.txt_search_frame);
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
            bonsaiAdapter.update(bonsaiArrayList);

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
                if (isShowKeyboard) {
                    TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout_detail), false);
                    hideKeyboard();
                    txt_search_frame.setText("");
                    getFilter(txt_search_frame.getText().toString());
                } else {
                    finish();
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addBonsaiManageEvent() {
        bonsaiAdapter.setOnItemClickListener(new BonsaiRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                startEditActivity(position);

                TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout_detail), false);
                hideKeyboard();
                txt_search_frame.setText(null);
                bonsaiAdapter.update(bonsaiArrayList);
            }
        });

        imgManageListAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewAndEditBonsaiActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });

        imgManageListSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowKeyboard) {
                    if (txt_search_frame.getText().toString().equals("")) {
                        TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout_detail), false);
                    }
                    hideKeyboard();
                } else {
                    TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout_detail), true);
                    showKeyboard();
                }
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (txt_search_frame.getText().toString().equals("")) {
                    if (isShowKeyboard) {
                        Log.d("_TopBarAnimation", "onTouch");

                        TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout_detail), false);
                        txt_search_frame.setText(null);
                        hideKeyboard();
                    }
                } else {
                    hideKeyboard();
                }

                return false;
            }
        });

        txt_search_frame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getFilter(editable.toString());
            }
        });

        txt_search_frame.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (txt_search_frame.getText().toString().equals("")) {
                        TopBarAnimation.handleSearchFrame(findViewById(R.id.top_layout_detail), false);
                    } else {
                        getFilter(txt_search_frame.getText().toString());
                    }
                    hideKeyboard();
                    return true;
                }

                return false;
            }
        });

        bonsaiAdapter.setOnItemLongClickListener(new BonsaiRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageList.this, R.style.AlertDialog);
                builder.setItems(R.array.bonsai_item_long_click, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0://Edit
                                Intent intent = new Intent(getApplicationContext(), NewAndEditBonsaiActivity.class);
                                intent.putExtra("title", getString(R.string.editBonsaiTitle));
                                intent.putExtra("id", bonsaiArrayList.get(position).getId());
                                intent.putExtra("name", bonsaiArrayList.get(position).getBonsaiName());
                                intent.putExtra("type", bonsaiArrayList.get(position).getBonsaiType());
                                intent.putExtra("place", bonsaiArrayList.get(position).getBonsaiPlacementName());
                                intent.putExtra("dayPlanted", bonsaiArrayList.get(position).getBonsaiDayPlanted());
                                startActivityForResult(intent, EDIT_REQUEST_CODE);
                                break;
                            case 1://Delete
                                if (ManipulationDb.deleteBonsai(dbHelper, bonsaiArrayList.get(position).getId())) {
                                    Toast.makeText(ManageList.this, "Delete success!", Toast.LENGTH_LONG).show();

                                    bonsaiArrayList.remove(position);
                                    bonsaiAdapter.update(bonsaiArrayList);
                                } else {
                                    Toast.makeText(ManageList.this, "Delete failed!", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void startEditActivity(int position) {
        Intent intent = new Intent(ManageList.this, BonsaiDetailActivity.class);
        intent.putExtra("id", String.valueOf(bonsaiArrayList.get(position).getId()));
        intent.putExtra("name", bonsaiArrayList.get(position).getBonsaiName());
        intent.putExtra("type", bonsaiArrayList.get(position).getBonsaiType());
        intent.putExtra("place", bonsaiArrayList.get(position).getBonsaiPlacementName());
        intent.putExtra("dayPlanted", bonsaiArrayList.get(position).getBonsaiDayPlanted());

        startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE);
    }

    private void showKeyboard() {
        isShowKeyboard = true;
        btnBack.setImageResource(R.drawable.ic_left_arrow);
        txt_search_frame.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void hideKeyboard() {
        isShowKeyboard = false;
        btnBack.setImageResource(R.drawable.ic_left);
        txt_search_frame.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt_search_frame.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case ADD_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(ManageList.this, R.string.toastAddSuccess, Toast.LENGTH_LONG).show();

                    ManipulationDb.getAllDataBonsaiTable(dbHelper, bonsaiArrayList);
                    bonsaiAdapter.update(bonsaiArrayList);
                }
                break;
            case EDIT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(ManageList.this, R.string.toastEditSuccess, Toast.LENGTH_LONG).show();

                    ManipulationDb.getAllDataBonsaiTable(dbHelper, bonsaiArrayList);
                    bonsaiAdapter.update(bonsaiArrayList);
                }
                break;

            case EDIT_ACTIVITY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getBooleanExtra("isChange", false)) {
                        Log.d("_onActivityResult", "EDIT_ACTIVITY_REQUEST_CODE");
                        ManipulationDb.getAllDataBonsaiTable(dbHelper, bonsaiArrayList);
                        bonsaiAdapter.update(bonsaiArrayList);
                    }
                }
                break;
        }
    }

    private void getFilter(String text) {
        if (!text.equals("")) {
            ArrayList<BonsaiItem> filterArrayList = new ArrayList<>();
            for (BonsaiItem item :
                    bonsaiArrayList) {
                if (item.getBonsaiName().toLowerCase().contains(text.toLowerCase())) {
                    filterArrayList.add(item);
                }
            }

            bonsaiAdapter.update(filterArrayList);
        } else {
            bonsaiAdapter.update(bonsaiArrayList);
        }
    }
}
