package com.offgrid.coupler.controller.map.configurator;

import android.content.Context;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.offgrid.coupler.core.callback.DeviceLocationChangeCallback;

public class DeviceLocationConfigurator {

    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    private Context context;
    private MapboxMap mapboxMap;

    private long maxWaitTime;
    private long interval;

    public DeviceLocationConfigurator() {
        maxWaitTime = DEFAULT_MAX_WAIT_TIME;
        interval = DEFAULT_INTERVAL_IN_MILLISECONDS;
    }

    public DeviceLocationConfigurator withContext(Context context) {
        this.context = context;
        return this;
    }

    public DeviceLocationConfigurator withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        return this;
    }

    public void configure() {
        LocationComponent locationComponent = mapboxMap.getLocationComponent();

        locationComponent.activateLocationComponent(LocationComponentActivationOptions
                .builder(context, mapboxMap.getStyle())
                .useDefaultLocationEngine(false)
                .build());

        locationComponent.setLocationComponentEnabled(true);
        locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);
        locationComponent.setRenderMode(RenderMode.COMPASS);

        LocationEngineRequest request = new LocationEngineRequest
                .Builder(interval)
                .setMaxWaitTime(maxWaitTime)
                .build();

        DeviceLocationChangeCallback callback = new DeviceLocationChangeCallback(context, mapboxMap);

        LocationEngine locationEngine = LocationEngineProvider.getBestLocationEngine(context);
        locationEngine.requestLocationUpdates(request, callback, context.getMainLooper());
        locationEngine.getLastLocation(callback);
    }

}
