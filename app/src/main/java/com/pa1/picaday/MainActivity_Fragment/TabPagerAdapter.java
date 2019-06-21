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
    private boolean listmode = false;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    public void setListmode(boolean listmode) { this.listmode = listmode; }
    public boolean isListmode() { return listmode; }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                if (!listmode)
                    return new MainActivity_weekly();
                else
                    return new MainActivity_weekly_list();
            case 1:
                if (!listmode)
                    return new MainActivity_daily();
                else
                    return new MainActivity_daily_list();
            case 2:
                if (!listmode)
                    return new MainActivity_monthly();
                else
                    return new MainActivity_monthly_list();

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
