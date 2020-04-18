package com.offgrid.coupler.core.callback;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

class BaseBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
        switch (newState) {
            case STATE_HIDDEN:
                onStateHidden(bottomSheet);
                break;
            case STATE_EXPANDED:
                onStateExpanded(bottomSheet);
                break;
            case STATE_COLLAPSED:
                onStateCollapsed(bottomSheet);
                break;
            case BottomSheetBehavior.STATE_DRAGGING:
            case BottomSheetBehavior.STATE_HALF_EXPANDED:
            case BottomSheetBehavior.STATE_SETTLING:
                break;
        }

    }

    protected void onStateHidden(@NonNull View bottomSheet) {
    }

    protected void onStateExpanded(@NonNull View bottomSheet) {
    }

    protected void onStateCollapsed(@NonNull View bottomSheet) {
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

    }
}
