package com.offgrid.coupler.core.callback;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.IdRes;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetBehavior.State;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener;
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapLongClickListener;
import com.offgrid.coupler.R;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

abstract class AbstractLocationListener
        implements OnClickListener, OnMapClickListener, OnMapLongClickListener {

    View rootView;

    private BottomSheetBehavior bottomSheet;
    private BottomSheetCallback bottomSheetCallback;
    private @IdRes int bottom_sheet_resource;

    private FloatingActionButton fb;


    AbstractLocationListener(@IdRes int resId) {
        this.bottom_sheet_resource = resId;
    }

    void bottomSheet(@State int state) {
        if (state == STATE_HIDDEN && bottomSheet != null) {
            bottomSheet.setState(state);
        } else if (state == STATE_EXPANDED) {
            if (bottomSheet == null) {
                bottomSheet = BottomSheetBehavior.from(rootView.findViewById(bottom_sheet_resource));
                if (bottomSheetCallback != null) {
                    bottomSheet.addBottomSheetCallback(bottomSheetCallback);
                }
            }
            bottomSheet.setState(state);
        }
    }

    void bottomSheet(BottomSheetCallback bottomSheetCallback) {
        this.bottomSheetCallback = bottomSheetCallback;
    }

    void floatingButton(@State int state) {
        if (fb == null) {
            fb = rootView.findViewById(R.id.back_to_camera_tracking_mode);
        }

        if (state == STATE_HIDDEN) {
            fb.show();
        } else {
            fb.hide();
        }
    }


    void hideFloatingButton() {
        floatingButton(STATE_EXPANDED);
    }

    void showFloatingButton() {
        floatingButton(STATE_HIDDEN);
    }



}
