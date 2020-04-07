package com.offgrid.coupler.controller.map.listener;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.chat.ChatActivity;
import com.offgrid.coupler.controller.contact.ContactInfoActivity;
import com.offgrid.coupler.core.holder.ContactDetailsViewHolder;
import com.offgrid.coupler.core.model.dto.UserDto;
import com.offgrid.coupler.core.model.dto.wrapper.DtoChatWrapper;
import com.offgrid.coupler.core.model.dto.wrapper.DtoUserWrapper;

import java.util.List;

import static com.offgrid.coupler.controller.map.MapConstants.*;

public class OnClickContactLocationListener implements MapboxMap.OnMapClickListener, View.OnClickListener {

    private MapboxMap mapboxMap;
    private BottomSheetBehavior bottomSheet;
    private ContactDetailsViewHolder viewHolder;
    private ValueAnimator markerAnimator;
    private boolean markerSelected = false;

    private UserDto user;

    private Context context;

    public OnClickContactLocationListener(Context context) {
        this.context = context;
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
            user = UserDto.getInstance(features.get(0));
            displayContact();
        }

        return true;
    }

    private void displayContact() {
        viewHolder.update(user);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact_chat:
                jumpToActivity(DtoChatWrapper.convertAndWrap(user), ChatActivity.class);
                break;
            case R.id.btn_contact_info:
                jumpToActivity(DtoUserWrapper.wrap(user), ContactInfoActivity.class);
                break;
        }

        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void jumpToActivity(Bundle extras, Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(extras);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
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
