package com.offgrid.coupler.util;

import androidx.fragment.app.Fragment;

import com.offgrid.coupler.R;
import com.offgrid.coupler.ui.chat.ChatListFragment;
import com.offgrid.coupler.ui.chat.contact.ContactFragment;
import com.offgrid.coupler.ui.chat.group.GroupFragment;
import com.offgrid.coupler.ui.info.InfoFragment;
import com.offgrid.coupler.ui.map.MapFragment;
import com.offgrid.coupler.ui.setting.SettingsFragment;

public class FragmentHelper {

    public static Fragment createFragment(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_group:
                fragment = new GroupFragment();
                break;
            case R.id.nav_contact:
                fragment = new ContactFragment();
                break;
            case R.id.nav_chat_list:
                fragment = new ChatListFragment();
                break;
            case R.id.nav_map:
                fragment = new MapFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_info:
                fragment = new InfoFragment();
                break;
        }

        return fragment;
    }

}
