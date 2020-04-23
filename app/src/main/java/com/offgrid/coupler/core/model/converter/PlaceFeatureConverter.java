package com.offgrid.coupler.core.model.converter;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.data.entity.ListPlaces;
import com.offgrid.coupler.data.entity.Place;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mapbox.geojson.Feature.fromGeometry;
import static com.mapbox.geojson.Point.fromLngLat;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACELIST_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACELIST_NAME;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_LATITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_LONGITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_NAME;

public class PlaceFeatureConverter {

    public static List<Feature> convert(ListPlaces listPlaces) {
        List<Feature> list = new ArrayList<>();

        for (Place place : listPlaces.place) {
            LatLng latLng = place.getLocation();

            Feature feature = fromGeometry(fromLngLat(latLng.getLongitude(), latLng.getLatitude()));

            feature.addNumberProperty(KEY_PLACE_LATITUDE, latLng.getLatitude());
            feature.addNumberProperty(KEY_PLACE_LONGITUDE, latLng.getLongitude());
            feature.addNumberProperty(KEY_PLACE_ID, place.getId());
            feature.addStringProperty(KEY_PLACE_NAME, place.getName());

            Placelist placelist = listPlaces.placelist;
            if (placelist != null) {
                feature.addNumberProperty(KEY_PLACELIST_ID, placelist.getId());
                feature.addStringProperty(KEY_PLACELIST_NAME, placelist.getName());
            }

            list.add(feature);
        }

        return list;
    }

    public static FeatureCollection convert(Collection<ListPlaces> collection) {
        List<Feature> list = new ArrayList<>();
        for (ListPlaces listPlaces : collection) {
            list.addAll(convert(listPlaces));
        }

        return FeatureCollection.fromFeatures(list);
    }
}
