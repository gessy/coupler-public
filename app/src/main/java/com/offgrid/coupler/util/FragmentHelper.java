package com.offgrid.coupler.util;

import androidx.fragment.app.Fragment;

import com.offgrid.coupler.R;
import com.offgrid.coupler.ui.chat.ChatListFragment;
import com.offgrid.coupler.ui.map.MapFragment;

public class FragmentHelper {

    public static Fragment createFragment(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_chat_list:
                fragment = new ChatListFragment();
                break;
            case R.id.nav_map:
                fragment = new MapFragment();
                break;
        }

        return fragment;
    }

}
