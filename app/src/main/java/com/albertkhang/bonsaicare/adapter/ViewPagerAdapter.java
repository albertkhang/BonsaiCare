package com.albertkhang.bonsaicare.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.albertkhang.bonsaicare.R;
import com.albertkhang.bonsaicare.fragment.fragment_manage;
import com.albertkhang.bonsaicare.fragment.fragment_schedule;
import com.albertkhang.bonsaicare.fragment.fragment_setting;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Fragment fragment_schedule;
    Fragment fragment_manage;
    Fragment fragment_setting;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://fragment_schedule
                return new fragment_schedule();

            case 1://fragment_manage
                return new fragment_manage();

            case 2://fragment_setting
                return new fragment_setting();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
