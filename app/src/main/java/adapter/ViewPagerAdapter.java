package com.albertkhang.bonsaicare.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.albertkhang.bonsaicare.fragment.FragmentManage;
import com.albertkhang.bonsaicare.fragment.FragmentSchedule;
import com.albertkhang.bonsaicare.fragment.FragmentSetting;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    FragmentSchedule fragmentSchedule;
    FragmentManage fragmentManage;
    FragmentSetting fragmentSetting;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdapter(FragmentManager fm, FragmentSchedule fragmentSchedule, FragmentManage fragmentManage, FragmentSetting fragmentSetting) {
        super(fm);
        this.fragmentSchedule = fragmentSchedule;
        this.fragmentManage = fragmentManage;
        this.fragmentSetting = fragmentSetting;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://FragmentSchedule
                return fragmentSchedule;

            case 1://FragmentManage
                return fragmentManage;

            case 2://FragmentSetting
                return fragmentSetting;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public FragmentSchedule getFragmentSchedule() {
        return fragmentSchedule;
    }

    public FragmentManage getFragmentManage() {
        return fragmentManage;
    }

    public FragmentSetting getFragmentSetting() {
        return fragmentSetting;
    }
}
