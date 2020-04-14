package com.offgrid.coupler.core.callback;

import android.animation.ValueAnimator;
import android.content.Context;
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
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;
import com.offgrid.coupler.core.model.dto.UserDto;


import java.util.Arrays;

import static com.mapbox.geojson.Feature.fromGeometry;
import static com.mapbox.geojson.Point.fromLngLat;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_GEOJSON_ID;
import static com.offgrid.coupler.controller.map.MapConstants.SELECTED_USER_LOCATION_LAYER_ID;

public class OnClickPlaceLocationListener
        implements MapboxMap.OnMapClickListener, View.OnClickListener, MapboxMap.OnMapLongClickListener {

    private MapboxMap mapboxMap;
    private BottomSheetBehavior bottomSheet;
    private PlaceDetailsViewHolder viewHolder;
    private ValueAnimator markerAnimator;
    private boolean markerSelected = false;

    private UserDto user;

    private Context context;

    public OnClickPlaceLocationListener(Context context) {
        this.context = context;
    }

    public OnClickPlaceLocationListener withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.markerAnimator = getAnimator();
        return this;
    }

    public OnClickPlaceLocationListener withBottomSheet(BottomSheetBehavior bottomSheet) {
        this.bottomSheet = bottomSheet;
        this.bottomSheet.addBottomSheetCallback(new BottomSheetCallback());
        return this;
    }

    public OnClickPlaceLocationListener withViewHolder(PlaceDetailsViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        return this;
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Style style = mapboxMap != null ? mapboxMap.getStyle() : null;
        if (style == null) return true;


        return true;
    }


    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        Style style = mapboxMap.getStyle();

        GeoJsonSource source = style.getSourceAs(NEW_PLACE_LOCATION_GEOJSON_ID);
        if (source != null) {
            Feature feature = fromGeometry(fromLngLat(point.getLongitude(), point.getLatitude()));
            source.setGeoJson(FeatureCollection.fromFeatures(Arrays.asList(feature)));
        }

        bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        return true;
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

    @Override
    public void onClick(View view) {
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    class BottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED:
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
