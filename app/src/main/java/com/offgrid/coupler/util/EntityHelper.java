package com.offgrid.coupler.util;

import android.os.Bundle;


import com.offgrid.coupler.R;
import com.offgrid.coupler.model.Info;

public class EntityHelper {

    public static Bundle createBundle(int itemId) {
        switch (itemId) {
            case R.id.nav_group:
                return new Info.BundleBuilder()
                        .withTitle("Add Group")
                        .withText("This is new group activity")
                        .build();
            case R.id.nav_contact:
                return  new Info.BundleBuilder()
                        .withTitle("Add Contact")
                        .withText("This is new contact activity")
                        .build();
            case R.id.nav_settings:
                return  new Info.BundleBuilder()
                        .withTitle("Settings")
                        .withText("This is settings activity")
                        .build();
            case R.id.nav_info:
                return  new Info.BundleBuilder()
                        .withTitle("Info")
                        .withText("This is info activity")
                        .build();
        }

        return new Info.BundleBuilder().build();
    }

}
