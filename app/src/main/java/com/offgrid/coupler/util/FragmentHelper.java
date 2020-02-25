package com.offgrid.coupler.util;

import androidx.fragment.app.Fragment;

import com.offgrid.coupler.R;
import com.offgrid.coupler.ui.gallery.GalleryFragment;
import com.offgrid.coupler.ui.home.HomeFragment;
import com.offgrid.coupler.ui.send.SendFragment;
import com.offgrid.coupler.ui.share.ShareFragment;
import com.offgrid.coupler.ui.slideshow.SlideshowFragment;
import com.offgrid.coupler.ui.tools.ToolsFragment;

public class FragmentHelper {

    public static Fragment createFragment(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_gallery:
                fragment = new GalleryFragment();
                break;
            case R.id.nav_send:
                fragment = new SendFragment();
                break;
            case R.id.nav_share:
                fragment = new ShareFragment();
                break;
            case R.id.nav_slideshow:
                fragment = new SlideshowFragment();
                break;
            case R.id.nav_tools:
                fragment = new ToolsFragment();
                break;
        }

        return fragment;
    }

}
