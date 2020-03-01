package com.offgrid.coupler.ui;

import android.annotation.SuppressLint;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentProvider {

    @SuppressLint("UseSparseArrays")
    private Map<Integer, Fragment> map = new HashMap<>();

    public FragmentProvider() {
    }

    public void addFragment(@IdRes int id, @NonNull Fragment fragment) {
        map.put(id, fragment);
    }

    public Fragment findViewById(@IdRes int id) {
        return map.get(id);
    }

}
