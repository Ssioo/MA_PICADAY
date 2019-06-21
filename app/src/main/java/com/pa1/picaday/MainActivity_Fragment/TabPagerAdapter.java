package com.pa1.picaday.MainActivity_Fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pa1.picaday.MainActivity_Fragment.MainActivity_daily;
import com.pa1.picaday.MainActivity_Fragment.MainActivity_monthly;
import com.pa1.picaday.MainActivity_Fragment.MainActivity_weekly;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MainActivity_weekly();
            case 1:
                //return new MainActivity_daily();
                return new MainActivity_daily_list();
            case 2:
                return new MainActivity_monthly();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
