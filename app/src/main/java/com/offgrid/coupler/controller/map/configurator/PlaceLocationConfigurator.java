package com.offgrid.coupler.controller.map.configurator;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.converter.PlaceFeatureConverter;
import com.offgrid.coupler.core.model.view.CollectionOfListPlacesViewModel;
import com.offgrid.coupler.data.entity.ListPlaces;


import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_IMAGE_ID;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_GEOJSON_ID;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_LAYER_ID;
import static com.offgrid.coupler.controller.map.MapConstants.PLACE_LOCATION_GEOJSON_ID;
import static com.offgrid.coupler.controller.map.MapConstants.PLACE_LOCATION_LAYER_ID;

public class PlaceLocationConfigurator implements Observer<List<ListPlaces>> {

    private Context context;
    private MapboxMap mapboxMap;

    private CollectionOfListPlacesViewModel listPlacesViewModel;

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
        listPlacesViewModel = new ViewModelProvider((FragmentActivity)context).get(CollectionOfListPlacesViewModel.class);
        listPlacesViewModel.observe((FragmentActivity)context, this);
        listPlacesViewModel.load();
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

        style.addSource(new GeoJsonSource(PLACE_LOCATION_GEOJSON_ID));
        style.addLayer(new SymbolLayer(PLACE_LOCATION_LAYER_ID, PLACE_LOCATION_GEOJSON_ID)
                .withProperties(iconImage(NEW_PLACE_LOCATION_IMAGE_ID),
                        iconSize(1f),
                        iconAllowOverlap(true),
                        iconOffset(new Float[]{0f, -9f})));
    }


    @Override
    public void onChanged(final List<ListPlaces> listPlaces) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                GeoJsonSource resultSource = style.getSourceAs(PLACE_LOCATION_GEOJSON_ID);
                resultSource.setGeoJson(PlaceFeatureConverter.convert(listPlaces));
            }
        });
    }
}
