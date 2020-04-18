package com.offgrid.coupler.core.callback;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;
import static com.offgrid.coupler.controller.map.MapConstants.*;

public class ContactLocationListener extends AbstractLocationListener  {

    private MapboxMap mapboxMap;
    private ContactDetailsViewHolder viewHolder;
    private ValueAnimator markerAnimator;
    private boolean markerSelected = false;

    private UserDto user;

    private Context context;

    public ContactLocationListener(Context context) {
        super(R.id.bottom_sheet_contact_details);
        this.context = context;
    }

    public ContactLocationListener withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.markerAnimator = getAnimator();
        return this;
    }


    public ContactLocationListener withRootView(View rootView) {
        this.viewHolder = new ContactDetailsViewHolder(rootView);
        this.rootView = rootView;

        return this;
    }


    public void attach() {
        bottomSheet(new BottomSheetCallback());

        rootView.findViewById(R.id.close_place_details).setOnClickListener(this);
        rootView.findViewById(R.id.btn_contact_info).setOnClickListener(this);
        rootView.findViewById(R.id.btn_contact_chat).setOnClickListener(this);
        rootView.findViewById(R.id.close_contact_details).setOnClickListener(this);

        mapboxMap.addOnMapClickListener(this);
        mapboxMap.addOnMapLongClickListener(this);
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Style style = mapboxMap.getStyle();
        if (style == null) {
            return false;
        }

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

        return false;
    }


    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        bottomSheet(STATE_HIDDEN);
        return false;
    }

    private void displayContact() {
        viewHolder.update(user);
        selectMarker();
        bottomSheet(STATE_EXPANDED);
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

        bottomSheet(STATE_HIDDEN);
    }

    private void jumpToActivity(Bundle extras, Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(extras);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
    }


    class BottomSheetCallback extends BaseBottomSheetCallback {
        @Override
        protected void onStateHidden(@NonNull View bottomSheet) {
            super.onStateHidden(bottomSheet);
            if (markerSelected) deselectMarker();
            viewHolder.gidVisibility(View.VISIBLE);
        }

        @Override
        protected void onStateExpanded(@NonNull View bottomSheet) {
            super.onStateExpanded(bottomSheet);
            if (!markerSelected) selectMarker();
            viewHolder.gidVisibility(View.VISIBLE);
        }

        @Override
        protected void onStateCollapsed(@NonNull View bottomSheet) {
            super.onStateCollapsed(bottomSheet);
            viewHolder.gidVisibility(View.INVISIBLE);
        }
    }
}
