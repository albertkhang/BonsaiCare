package com.albertkhang.bonsaicare.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.activity.schedule.ScheduleNewItemActivity;
import com.albertkhang.bonsaicare.adapter.ViewPagerAdapter;
import com.albertkhang.bonsaicare.animation.TopBarAnimation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView txtTabTitle;
    BottomNavigationView bottomNavigationView;

    ImageView imgAddButton;
    ImageView imgSearchButton;

    ViewPager vpViewPager;
    ViewPagerAdapter viewPagerAdapter;

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

        imgAddButton = findViewById(R.id.imgAddButton);
        imgSearchButton = findViewById(R.id.imgSearchButton);

        vpViewPager = findViewById(R.id.vpViewPager);
        vpViewPager.setOffscreenPageLimit(2);//load 2 fragment per time
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpViewPager.setAdapter(viewPagerAdapter);
    }

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

        imgAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddActivity();
            }
        });
    }

    private void startAddActivity() {
        Intent intent = new Intent(MainActivity.this, ScheduleNewItemActivity.class);
        startActivity(intent);
    }

    private void handleAddSearchIcon(boolean wantShow) {
        if (wantShow) {
//            imgAddButton.setVisibility(View.VISIBLE);
//            imgSearchButton.setVisibility(View.VISIBLE);
            TopBarAnimation.showIcon(imgAddButton);
            TopBarAnimation.showIcon(imgSearchButton);
        } else {
//            imgAddButton.setVisibility(View.INVISIBLE);
//            imgSearchButton.setVisibility(View.INVISIBLE);
            TopBarAnimation.hideIcon(imgAddButton);
            TopBarAnimation.hideIcon(imgSearchButton);
        }
    }

    private void handleUIFragmentChange(int position, boolean isViewPager) {
        switch (position) {
            case 0:
                handleAddSearchIcon(true);
                if (isViewPager) {
                    /* isViewPager */
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                } else {
                    /* isBottomNavigationBar */
                    vpViewPager.setCurrentItem(0);
                }
                TopBarAnimation.showTitle(txtTabTitle, R.string.schedule);
//                txtTabTitle.setText(R.string.schedule);
                break;

            case 1:
                handleAddSearchIcon(false);
                if (isViewPager) {
                    /* isViewPager */
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                } else {
                    /* isBottomNavigationBar */
                    vpViewPager.setCurrentItem(1);
                }
                TopBarAnimation.showTitle(txtTabTitle, R.string.manage);
//                txtTabTitle.setText(R.string.manage);
                break;

            case 2:
                handleAddSearchIcon(false);
                if (isViewPager) {
                    /* isViewPager */
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                } else {
                    /* isBottomNavigationBar */
                    vpViewPager.setCurrentItem(2);
                }
                TopBarAnimation.showTitle(txtTabTitle, R.string.setting);
//                txtTabTitle.setText(R.string.setting);
                break;
        }
    }
}
