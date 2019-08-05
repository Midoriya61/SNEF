package com.tinlm.snef.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tinlm.snef.fragment.FoundFragment;
import com.tinlm.snef.fragment.SawFragment;

public class TabViewPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public TabViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SawFragment();
            case 1:
                return new FoundFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}