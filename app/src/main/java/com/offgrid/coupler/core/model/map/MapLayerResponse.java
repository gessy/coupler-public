package com.offgrid.coupler.core.model.map;

import com.mapbox.geojson.Feature;

public class MapLayerResponse {
    public enum Action {
        NO_ACTION,
        HIDE,
        EXPAND,
    }

    public Feature feature;

    public Action state = Action.NO_ACTION;

}
