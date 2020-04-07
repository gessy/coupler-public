package com.offgrid.coupler.controller.map.listener;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.offgrid.coupler.controller.map.MapConstants;
import com.offgrid.coupler.core.holder.ContactDetailsViewHolder;
import com.offgrid.coupler.core.model.dto.UserDto;

import java.util.List;

import static com.offgrid.coupler.controller.map.MapConstants.*;

public class OnClickContactLocationListener implements MapboxMap.OnMapClickListener {

    private MapboxMap mapboxMap;
    private BottomSheetBehavior bottomSheet;
    private ContactDetailsViewHolder viewHolder;
    private ValueAnimator markerAnimator;
    private boolean markerSelected = false;

    public OnClickContactLocationListener() {
    }

    public OnClickContactLocationListener withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.markerAnimator = getAnimator();
        return this;
    }

    public OnClickContactLocationListener withBottomSheet(BottomSheetBehavior bottomSheet) {
        this.bottomSheet = bottomSheet;
        this.bottomSheet.addBottomSheetCallback(new BottomSheetCallback());
        return this;
    }

    public OnClickContactLocationListener withViewHolder(ContactDetailsViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        return this;
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Style style = mapboxMap != null ? mapboxMap.getStyle() : null;
        if (style == null) return true;

        PointF pixel = mapboxMap.getProjection().toScreenLocation(point);
        List<Feature> features = mapboxMap.queryRenderedFeatures(pixel, USER_LOCATION_LAYER_ID);
        List<Feature> selectedFeature = mapboxMap.queryRenderedFeatures(pixel, SELECTED_USER_LOCATION_LAYER_ID);

        if (selectedFeature.size() > 0 && markerSelected) {
            return false;
        }

        if (features.isEmpty()) {
            return false;
        }

        GeoJsonSource source = style.getSourceAs(SELECTED_USER_LOCATION_GEOJSON_ID);
        if (source != null) {
            source.setGeoJson(FeatureCollection.fromFeatures(new Feature[]{
                    Feature.fromGeometry(features.get(0).geometry())
            }));
        }

        if (features.size() > 0) {
            showContact(features.get(0));
        }

        return true;
    }

    private void showContact(Feature feature) {
        viewHolder.update(feature);
        selectMarker();
        if (bottomSheet != null) {
            bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void selectMarker() {
        markerAnimator.setObjectValues(1f, 2f);
        markerAnimator.start();
        markerSelected = true;
    }

    private void deselectMarker() {
        markerAnimator.setObjectValues(2f, 1f);
        markerAnimator.start();
        markerSelected = false;
    }


    private ValueAnimator getAnimator() {
        ValueAnimator markerAnimator = new ValueAnimator();
        markerAnimator.setDuration(300);
        markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    SymbolLayer layer = (SymbolLayer) style.getLayer(SELECTED_USER_LOCATION_LAYER_ID);
                    layer.setProperties(PropertyFactory.iconSize((float) animator.getAnimatedValue()));
                }
            }
        });

        return markerAnimator;
    }

    class BottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    if (markerSelected) deselectMarker();
                    viewHolder.gidVisibility(View.VISIBLE);
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    if (!markerSelected) selectMarker();
                    viewHolder.gidVisibility(View.VISIBLE);
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED:
                    viewHolder.gidVisibility(View.INVISIBLE);
                case BottomSheetBehavior.STATE_DRAGGING:
                case BottomSheetBehavior.STATE_HALF_EXPANDED:
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }

}
