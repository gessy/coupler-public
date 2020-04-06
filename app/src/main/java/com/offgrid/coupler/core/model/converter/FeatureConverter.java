package com.offgrid.coupler.core.model.converter;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.data.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mapbox.geojson.Feature.fromGeometry;
import static com.mapbox.geojson.Point.fromLngLat;
import static com.offgrid.coupler.core.model.Constants.KEY_CONTACT_FIRST_NAME;
import static com.offgrid.coupler.core.model.Constants.KEY_CONTACT_FULL_NAME;
import static com.offgrid.coupler.core.model.Constants.KEY_CONTACT_GID;
import static com.offgrid.coupler.core.model.Constants.KEY_CONTACT_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_CONTACT_LAST_NAME;

public class FeatureConverter {

    public static Feature convert(User user) {
        LatLng latLng = user.getLocation();

        Feature feature = fromGeometry(fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
        feature.addNumberProperty(KEY_CONTACT_ID, user.getId());
        feature.addStringProperty(KEY_CONTACT_FULL_NAME, user.fullName());
        feature.addStringProperty(KEY_CONTACT_FIRST_NAME, user.getFirstName());
        feature.addStringProperty(KEY_CONTACT_LAST_NAME, user.getLastName());
        feature.addStringProperty(KEY_CONTACT_GID, user.getGid());

        return feature;
    }


    public static FeatureCollection convert(Collection<User> users) {
        List<Feature> list = new ArrayList<>();
        for (User user : users) {
            list.add(FeatureConverter.convert(user));
        }
        return FeatureCollection.fromFeatures(list);
    }

}
