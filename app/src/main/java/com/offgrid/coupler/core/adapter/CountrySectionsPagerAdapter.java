package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.offgrid.coupler.controller.offline.CountryRegionsFragment;
import com.offgrid.coupler.data.entity.Country;

import java.util.ArrayList;
import java.util.List;


public class CountrySectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    private List<Country> list = new ArrayList<>();


    public CountrySectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    public void setCountryList(List<Country> countries) {
        list = countries;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return CountryRegionsFragment.newInstance(list.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}