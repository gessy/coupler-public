package com.offgrid.coupler.core.callback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.chat.ChatActivity;
import com.offgrid.coupler.controller.contact.ContactInfoActivity;
import com.offgrid.coupler.controller.map.MapService;
import com.offgrid.coupler.core.holder.ContactDetailsViewHolder;
import com.offgrid.coupler.core.model.dto.UserDto;
import com.offgrid.coupler.core.model.dto.wrapper.DtoChatWrapper;
import com.offgrid.coupler.core.model.dto.wrapper.DtoUserWrapper;
import com.offgrid.coupler.core.model.map.MapLayerResponse;
import com.offgrid.coupler.core.model.map.MapLayerResponse.Action;

import static com.offgrid.coupler.controller.map.MapConstants.*;

public class ContactLocationListener extends AbstractLocationListener {

    private MapboxMap mapboxMap;
    private ContactDetailsViewHolder viewHolder;

    private Context context;

    public ContactLocationListener(Context context) {
        super(R.id.bottom_sheet_contact_details);
        this.context = context;
    }

    public ContactLocationListener withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        registrAnimator(mapboxMap, SELECTED_USER_LOCATION_LAYER_ID);
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


        MapLayerResponse response = MapService.contactFeature(mapboxMap, point, markerState(SELECTED_USER_LOCATION_LAYER_ID));

        if (response.state == Action.HIDE) {
            hideBottomSheet();
            return false;
        }

        if (response.state == Action.EXPAND) {
            GeoJsonSource source = style.getSourceAs(SELECTED_USER_LOCATION_GEOJSON_ID);
            source.setGeoJson(FeatureCollection.fromFeatures(new Feature[]{
                    Feature.fromGeometry(response.feature.geometry())
            }));

            displayContact(UserDto.getInstance(response.feature));
        }

        return false;
    }


    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        hideBottomSheet();
        return false;
    }

    private void displayContact(UserDto user) {
        viewHolder.update(user);
        showBottomSheet();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact_chat:
                jumpToActivity(DtoChatWrapper.convertAndWrap(viewHolder.get()), ChatActivity.class);
                break;
            case R.id.btn_contact_info:
                jumpToActivity(DtoUserWrapper.wrap(viewHolder.get()), ContactInfoActivity.class);
                break;
        }

        hideBottomSheet();
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
            deselectMarkerAnimation(SELECTED_USER_LOCATION_LAYER_ID);
        }

        @Override
        protected void onStateExpanded(@NonNull View bottomSheet) {
            super.onStateExpanded(bottomSheet);
            selectMarkerAnimation(SELECTED_USER_LOCATION_LAYER_ID);
        }

        @Override
        protected void onStateCollapsed(@NonNull View bottomSheet) {
            super.onStateCollapsed(bottomSheet);
            deselectMarkerAnimation(SELECTED_USER_LOCATION_LAYER_ID);
        }
    }
}
