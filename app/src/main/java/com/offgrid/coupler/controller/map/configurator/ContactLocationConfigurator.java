package com.offgrid.coupler.controller.map.configurator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.offgrid.coupler.R;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.offgrid.coupler.controller.map.MapConstants.*;

public class ContactLocationConfigurator {

    private Context context;
    private MapboxMap mapboxMap;

    private static final int DRAWABLE_RES = R.drawable.ic_location_on_blue_24dp;

    public ContactLocationConfigurator() {
    }

    public ContactLocationConfigurator withContext(Context context) {
        this.context = context;
        return this;
    }

    public ContactLocationConfigurator withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        return this;
    }

    public void configure() {
        Style style = mapboxMap.getStyle();

        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), DRAWABLE_RES, null);
        style.addImage(USER_LOCATION_IMAGE_ID, BitmapUtils.getBitmapFromDrawable(drawable));

        style.addSource(new GeoJsonSource(USER_LOCATION_GEOJSON_ID));
        style.addLayer(new SymbolLayer(USER_LOCATION_LAYER_ID, USER_LOCATION_GEOJSON_ID)
                .withProperties(iconImage(USER_LOCATION_IMAGE_ID),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -9f})));

        style.addSource(new GeoJsonSource(SELECTED_USER_LOCATION_GEOJSON_ID));
        style.addLayer(new SymbolLayer(SELECTED_USER_LOCATION_LAYER_ID, SELECTED_USER_LOCATION_GEOJSON_ID)
                .withProperties(iconImage(USER_LOCATION_IMAGE_ID),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -9f})));
    }

}
