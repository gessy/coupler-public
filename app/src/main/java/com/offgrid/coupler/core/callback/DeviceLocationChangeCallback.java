package com.offgrid.coupler.core.callback;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.mapboxsdk.maps.MapboxMap;


public class DeviceLocationChangeCallback implements LocationEngineCallback<LocationEngineResult> {

    private MapboxMap mapboxMap;
    private Context context;

    public DeviceLocationChangeCallback(Context context, MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.context = context;
    }

    /**
     * The LocationEngineCallback interface's method which fires when the device's location has changed.
     *
     * @param result the LocationEngineResult object which has the last known location within it.
     */
    @Override
    public void onSuccess(LocationEngineResult result) {
        Location location = result.getLastLocation();

        if (location == null) {
            return;
        }

        if (mapboxMap != null && result.getLastLocation() != null) {
            mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
        }
    }

    /**
     * The LocationEngineCallback interface's method which fires when the device's location can't be captured
     *
     * @param exception the exception message
     */
    @Override
    public void onFailure(@NonNull Exception exception) {
        if (context != null) {
            Toast.makeText(context, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
