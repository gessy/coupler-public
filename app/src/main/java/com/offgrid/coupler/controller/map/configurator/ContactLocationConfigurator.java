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
import com.offgrid.coupler.core.model.converter.FeatureConverter;
import com.offgrid.coupler.core.model.view.ContactListViewModel;
import com.offgrid.coupler.data.entity.User;

import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.offgrid.coupler.controller.map.MapConstants.*;

public class ContactLocationConfigurator implements Observer<List<User>> {

    private Context context;
    private MapboxMap mapboxMap;

    private ContactListViewModel contactListViewModel;

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

    private void initViewModel() {
        contactListViewModel = new ViewModelProvider((FragmentActivity)context).get(ContactListViewModel.class);
        contactListViewModel.observe((FragmentActivity)context, this);
        contactListViewModel.load();
    }

    public void configure() {
        initViewModel();

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


    @Override
    public void onChanged(final List<User> users) {
        if (!users.isEmpty()) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    GeoJsonSource resultSource = style.getSourceAs(USER_LOCATION_GEOJSON_ID);
                    resultSource.setGeoJson(FeatureConverter.convert(users));
                }
            });
        }
    }
}
