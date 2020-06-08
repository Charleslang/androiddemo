package com.qinjie.demo.personal.order;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class TextAdapter extends FragmentPagerAdapter {
    String[] titles;
    List<Fragment> lists = new ArrayList<>();

    public TextAdapter(FragmentManager fm, String[] titles, List<Fragment> list) {
        super(fm);
        this.titles = titles;
        this.lists = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return lists.size();
    }
}