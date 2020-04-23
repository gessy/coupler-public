package com.offgrid.coupler.controller.map;

import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.offgrid.coupler.core.model.map.MapLayerResponse;

import java.util.List;

import static com.offgrid.coupler.controller.map.MapConstants.PLACE_LOCATION_LAYER_ID;
import static com.offgrid.coupler.controller.map.MapConstants.SELECTED_PLACE_LOCATION_LAYER_ID;
import static com.offgrid.coupler.controller.map.MapConstants.SELECTED_USER_LOCATION_LAYER_ID;
import static com.offgrid.coupler.controller.map.MapConstants.USER_LOCATION_LAYER_ID;

public class MapService {


    public static MapLayerResponse placeFeature(MapboxMap mapboxMap,
                                                @NonNull LatLng point,
                                                @NonNull Boolean markerSelected) {
        MapLayerResponse response = new MapLayerResponse();

        PointF pixel = mapboxMap.getProjection().toScreenLocation(point);

        List<Feature> selectedFeature = mapboxMap.queryRenderedFeatures(pixel, SELECTED_PLACE_LOCATION_LAYER_ID);
        if (selectedFeature.size() > 0 && markerSelected) {
            response.state = MapLayerResponse.Action.NO_ACTION;
            return response;
        }

        List<Feature> features = mapboxMap.queryRenderedFeatures(pixel, PLACE_LOCATION_LAYER_ID);
        if (features.size() > 0) {
            response.state = MapLayerResponse.Action.EXPAND;
            response.feature = features.get(0);
            return response;
        }


        features = mapboxMap.queryRenderedFeatures(pixel, USER_LOCATION_LAYER_ID);
        selectedFeature = mapboxMap.queryRenderedFeatures(pixel, SELECTED_USER_LOCATION_LAYER_ID);
        if (features.size() > 0 || selectedFeature.size() > 0) {
            response.state = MapLayerResponse.Action.HIDE;
            return response;
        }


        response.state = MapLayerResponse.Action.NO_ACTION;

        return response;
    }


    public static MapLayerResponse contactFeature(MapboxMap mapboxMap,
                                                  @NonNull LatLng point,
                                                  @NonNull Boolean markerSelected) {
        MapLayerResponse response = new MapLayerResponse();

        PointF pixel = mapboxMap.getProjection().toScreenLocation(point);

        List<Feature> selectedFeature = mapboxMap.queryRenderedFeatures(pixel, SELECTED_USER_LOCATION_LAYER_ID);
        if (selectedFeature.size() > 0 && markerSelected) {
            response.state = MapLayerResponse.Action.NO_ACTION;
            return response;
        }

        List<Feature> features = mapboxMap.queryRenderedFeatures(pixel, USER_LOCATION_LAYER_ID);
        if (features.size() > 0) {
            response.state = MapLayerResponse.Action.EXPAND;
            response.feature = features.get(0);
            return response;
        }


        features = mapboxMap.queryRenderedFeatures(pixel, PLACE_LOCATION_LAYER_ID);
        selectedFeature = mapboxMap.queryRenderedFeatures(pixel, SELECTED_PLACE_LOCATION_LAYER_ID);
        if (features.size() > 0 || selectedFeature.size() > 0) {
            response.state = MapLayerResponse.Action.HIDE;
            return response;
        }


        response.state = MapLayerResponse.Action.NO_ACTION;

        return response;
    }

}
