package com.albertkhang.bonsaicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.albertkhang.bonsaicare.adapter.ViewPagerAdapter;
import com.albertkhang.bonsaicare.fragment.fragment_schedule;
import com.albertkhang.bonsaicare.fragment.fragment_setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TextView txtTabTitle;
    BottomNavigationView bottomNavigationView;

    ConstraintLayout frame_search;
    ConstraintLayout frame_add;

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

        frame_add = findViewById(R.id.frame_add);
        frame_search = findViewById(R.id.frame_search);

        vpViewPager = findViewById(R.id.vpViewPager);
        vpViewPager.setOffscreenPageLimit(2);//load 2 fragment per time
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpViewPager.setAdapter(viewPagerAdapter);
    }

    private void addEvent() {
        frame_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewScheduleActivity.class);
                startActivity(intent);
            }
        });

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
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("_ViewPager", "onPageSelected_" + position);
                handleUIFragmentChange(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void handleAddSearchIcon(boolean wantShow) {
        if (wantShow) {
            frame_add.setVisibility(View.VISIBLE);
            frame_search.setVisibility(View.VISIBLE);
        } else {
            frame_add.setVisibility(View.INVISIBLE);
            frame_search.setVisibility(View.INVISIBLE);
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
                txtTabTitle.setText(R.string.schedule);
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
                txtTabTitle.setText(R.string.manage);
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
                txtTabTitle.setText(R.string.setting);
                break;
        }
    }
}
