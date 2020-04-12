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
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_IMAGE_ID;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_GEOJSON_ID;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_LAYER_ID;

public class PlaceLocationConfigurator  {

    private Context context;
    private MapboxMap mapboxMap;


    private static final int NEW_PLACE_DRAWABLE_RES = R.drawable.ic_place_yellow_24dp;

    public PlaceLocationConfigurator() {
    }

    public PlaceLocationConfigurator withContext(Context context) {
        this.context = context;
        return this;
    }

    public PlaceLocationConfigurator withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        return this;
    }

    private void initViewModel() {

    }

    public void configure() {
        initViewModel();

        Style style = mapboxMap.getStyle();

        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), NEW_PLACE_DRAWABLE_RES, null);
        style.addImage(NEW_PLACE_LOCATION_IMAGE_ID, BitmapUtils.getBitmapFromDrawable(drawable));

        style.addSource(new GeoJsonSource(NEW_PLACE_LOCATION_GEOJSON_ID));
        style.addLayer(new SymbolLayer(NEW_PLACE_LOCATION_LAYER_ID, NEW_PLACE_LOCATION_GEOJSON_ID)
                .withProperties(iconImage(NEW_PLACE_LOCATION_IMAGE_ID),
                        iconSize(2f),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -9f})));
    }

}
