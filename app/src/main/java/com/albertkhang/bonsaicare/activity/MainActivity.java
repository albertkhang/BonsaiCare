package com.albertkhang.bonsaicare.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.schedule.NewAndEditScheduleActivity;
import com.albertkhang.bonsaicare.database.FeedReaderDbHelper;
import com.albertkhang.bonsaicare.adapter.ViewPagerAdapter;
import com.albertkhang.bonsaicare.animation.TopBarAnimation;
import com.albertkhang.bonsaicare.fragment.FragmentManage;
import com.albertkhang.bonsaicare.fragment.FragmentSchedule;
import com.albertkhang.bonsaicare.fragment.FragmentSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView txtTabTitle;
    BottomNavigationView bottomNavigationView;

    ImageView imgAddButton;
    ImageView imgSearch;
    ImageView btnBack;
    ImageView imgClearText;

    EditText txtSearchValue;

    ImageView searchFrame;

    ViewPager vpViewPager;
    ViewPagerAdapter viewPagerAdapter;

    FeedReaderDbHelper dbHelper;

    FragmentSchedule fragmentSchedule;
    FragmentManage fragmentManage;
    FragmentSetting fragmentSetting;

    boolean isShowKeyboard = false;
    boolean isShowSearchFrame = false;

    private static final int ADD_NEW_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();
    }

    private void addControl() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txtTabTitle = findViewById(R.id.txtTabTitle);

        imgAddButton = findViewById(R.id.imgManageListAddButton);
        imgSearch = findViewById(R.id.imgSearch);
        btnBack = findViewById(R.id.btnBack);
        searchFrame = findViewById(R.id.searchFrame);
        imgClearText = findViewById(R.id.imgClearText);

        txtSearchValue = findViewById(R.id.txtSearchValue);

        vpViewPager = findViewById(R.id.vpViewPager);
        vpViewPager.setOffscreenPageLimit(2);//load 2 fragment per time

        fragmentSchedule = new FragmentSchedule();
        fragmentManage = new FragmentManage();
        fragmentSetting = new FragmentSetting();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentSchedule, fragmentManage, fragmentSetting);
        vpViewPager.setAdapter(viewPagerAdapter);

        dbHelper = new FeedReaderDbHelper(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_schedule:
                        handleUIFragmentChange(0, false);
                        return true;

                    case R.id.navigation_management:
                        handleUIFragmentChange(1, false);
                        return true;

                    case R.id.navigation_setting:
                        handleUIFragmentChange(2, false);
                        return true;
                }
                return false;
            }
        });

        vpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("_onPageScrolled_o", "positionOffset: " + positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("_ViewPager", "onPageSelected_" + position);
                handleUIFragmentChange(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("_onPageScrollState", "onPageSelected_" + state);
            }
        });

        vpViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txtSearchValue.setText("");
                if (isShowKeyboard) {
                    hideKeyboard(txtSearchValue);
                }

                if (isShowSearchFrame) {
                    hideSearchFrame();
                }
                return false;
            }
        });

        imgAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewAndEditScheduleActivity.class);
                startActivityForResult(intent, ADD_NEW_REQUEST_CODE);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchFrame();
                showKeyboard(txtSearchValue);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSearchValue.setText("");
                hideSearchFrame();
                hideKeyboard(txtSearchValue);
            }
        });

        fragmentSchedule.setOnTouchListener(new FragmentSchedule.OnTouchListener() {
            @Override
            public void onTouch(View view, MotionEvent motionEvent) {
                if (txtSearchValue.getText().toString().equals("")) {
                    if (isShowKeyboard) {
                        hideKeyboard(txtSearchValue);
                    }

                    if (isShowSearchFrame) {
                        hideSearchFrame();
                    }
                } else {
                    if (isShowKeyboard) {
                        hideKeyboard(txtSearchValue);
                    }
                }
            }
        });

        imgClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSearchValue.setText("");
                showKeyboard(txtSearchValue);
            }
        });

        txtSearchValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                handleClearButton(editable.toString());

                fragmentSchedule.filterAdapter(editable.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_NEW_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getBooleanExtra("needRefresh", false)) {
                        fragmentSchedule.updateAdapter();
                    }
                }

                break;
        }
    }

    private void handleUIFragmentChange(int position, boolean isViewPager) {
        switch (position) {
            case 0:
                TopBarAnimation.handleAddSearchIcon(imgSearch, true, imgAddButton, true);
                if (isViewPager) {
                    /* isViewPager */
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                } else {
                    /* isBottomNavigationBar */
                    vpViewPager.setCurrentItem(0);
                }
                TopBarAnimation.showTitle(txtTabTitle, R.string.schedule);
                break;

            case 1:
                TopBarAnimation.handleAddSearchIcon(imgSearch, false, imgAddButton, false);
                if (isViewPager) {
                    /* isViewPager */
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                } else {
                    /* isBottomNavigationBar */
                    vpViewPager.setCurrentItem(1);
                }
                TopBarAnimation.showTitle(txtTabTitle, R.string.manage);
                txtSearchValue.setText("");
                hideKeyboard(txtSearchValue);
                break;

            case 2:
                TopBarAnimation.handleAddSearchIcon(imgSearch, false, imgAddButton, false);
                if (isViewPager) {
                    /* isViewPager */
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                } else {
                    /* isBottomNavigationBar */
                    vpViewPager.setCurrentItem(2);
                }
                TopBarAnimation.showTitle(txtTabTitle, R.string.setting);
                txtSearchValue.setText("");
                hideKeyboard(txtSearchValue);
                break;
        }
    }

    private void showKeyboard(EditText editText) {
        isShowKeyboard = true;
        btnBack.setImageResource(R.drawable.ic_left_arrow);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void hideKeyboard(EditText editText) {
        isShowKeyboard = false;
        editText.clearFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void showSearchFrame() {
        isShowSearchFrame = true;
        btnBack.setVisibility(View.VISIBLE);
        TopBarAnimation.showSearchFrame(searchFrame, txtSearchValue);
    }

    private void hideSearchFrame() {
        isShowSearchFrame = false;
        btnBack.setVisibility(View.GONE);
//        TopBarAnimation.hideSearchFrame(searchFrame, txtSearchValue);
        searchFrame.setVisibility(View.GONE);
        txtSearchValue.setVisibility(View.GONE);
    }

    private void handleClearButton(String text) {
        if (text.equals("")) {
            imgClearText.setVisibility(View.GONE);
        } else {
            imgClearText.setVisibility(View.VISIBLE);
        }
    }
}
