package com.offgrid.coupler.core.callback;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.State;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener;
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapLongClickListener;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

abstract class AbstractLocationListener
        implements OnClickListener, OnMapClickListener, OnMapLongClickListener {

    View rootView;

    private BottomSheetBehavior bottomSheet;
    private BaseBottomSheetCallback bottomSheetCallback;
    private @IdRes int bottom_sheet_resource;

    private Map<String, ValueAnimator> markerLayerAnimator = new HashMap<>();
    private Map<String, Boolean> markerState = new HashMap<>();


    AbstractLocationListener(@IdRes int resId) {
        this.bottom_sheet_resource = resId;
    }


    void showBottomSheet() {
        bottomSheet(STATE_EXPANDED);
    }

    void hideBottomSheet() {
        bottomSheet(STATE_HIDDEN);
    }

    private void bottomSheet(@State int state) {
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

    void bottomSheet(BaseBottomSheetCallback bottomSheetCallback) {
        this.bottomSheetCallback = bottomSheetCallback;
    }


    void selectMarkerAnimation(@NonNull String layerId) {
        if (!markerState.get(layerId)) {
            ValueAnimator animator = markerLayerAnimator.get(layerId);
            if (animator != null) {
                animator.setObjectValues(1f, 2f);
                animator.start();
                markerState.put(layerId, TRUE);
            }
        }
    }


    void deselectMarkerAnimation(@NonNull String layerId) {
        if (markerState.get(layerId)) {
            ValueAnimator animator = markerLayerAnimator.get(layerId);
            if (animator != null) {
                animator.setObjectValues(2f, 1f);
                animator.start();
                markerState.put(layerId, FALSE);
            }
        }
    }

    void deselectAllMarkersAnimation() {
        for (String layerId : markerLayerAnimator.keySet()) {
            deselectMarkerAnimation(layerId);
        }
    }


    void registrAnimator(final MapboxMap mapboxMap, final @NonNull String layerId) {
        markerLayerAnimator.put(layerId, getAnimator(mapboxMap, layerId));
        markerState.put(layerId, FALSE);
    }


    Boolean markerState(@NonNull String layerId) {
        return markerState.get(layerId);
    }


    private ValueAnimator getAnimator(final MapboxMap mapboxMap, final @NonNull String layerId) {
        ValueAnimator markerAnimator = new ValueAnimator();
        markerAnimator.setDuration(300);
        markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    SymbolLayer layer = (SymbolLayer) style.getLayer(layerId);
                    layer.setProperties(PropertyFactory.iconSize((float) animator.getAnimatedValue()));
                }
            }
        });

        return markerAnimator;
    }

}
