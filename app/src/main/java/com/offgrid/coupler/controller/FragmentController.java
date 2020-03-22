package com.offgrid.coupler.controller;

import android.annotation.SuppressLint;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import static com.offgrid.coupler.controller.FragmentController.Op.OP_ADD;
import static com.offgrid.coupler.controller.FragmentController.Op.OP_HIDE;
import static com.offgrid.coupler.controller.FragmentController.Op.OP_SHOW;

public class FragmentController {

    class Op {
        static final int OP_ADD = 1;
        static final int OP_HIDE = 4;
        static final int OP_SHOW = 5;

        int cmd;
        Fragment fragment;
        String tag;

        Op(int cmd, Fragment fragment) {
            this.cmd = cmd;
            this.fragment = fragment;
        }

        Op(int cmd, Fragment fragment, String tag) {
            this.cmd = cmd;
            this.fragment = fragment;
            this.tag = tag;
        }
    }


    private int contentResourceId;
    private FragmentManager fragmentManager;
    @SuppressLint("UseSparseArrays")
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();


    public void setFragmentManager(@NonNull FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void setLayoutResource(@IdRes int resId) {
        contentResourceId = resId;
    }

    public void registerFragment(@IdRes int resId, @NonNull Fragment fragment) {
        fragmentMap.put(resId, fragment);
    }


    public void displayScreen(@IdRes int resId) {
        if (!fragmentMap.containsKey(resId)) {
            return;
        }

        for (Integer key : fragmentMap.keySet()) {
            Fragment fragment = fragmentManager.findFragmentByTag(TAG(key));
            if (key.equals(resId)) {
                runOp(fragment == null
                        ? new Op(OP_ADD, fragmentMap.get(key), TAG(key))
                        : new Op(OP_SHOW, fragment));
            } else if (fragment != null) {
                runOp(new Op(OP_HIDE, fragment));
            }
        }
    }


    private void runOp(Op op) {
        switch (op.cmd) {
            case OP_ADD:
                fragmentManager
                        .beginTransaction()
                        .add(contentResourceId, op.fragment, op.tag)
                        .commit();
                break;
            case OP_HIDE:
                fragmentManager.beginTransaction().hide(op.fragment).commit();
                break;
            case OP_SHOW:
                fragmentManager.beginTransaction().show(op.fragment).commit();
                break;
        }
    }


    private String TAG(int id) {
        return "fragment_tag_" + id;
    }

}
