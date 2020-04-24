package com.offgrid.coupler.core.model;

import com.mapbox.geojson.Feature;

public class MapLayerRequest {
    enum State {
        HIDE,
        EXPAND
    }

    Feature feature;

    State state;
}
