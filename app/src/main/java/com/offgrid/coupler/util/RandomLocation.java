package com.offgrid.coupler.util;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Random;

public class RandomLocation {

    private static double RADIUS = 0.031917d;
    private static int BOUND = 360;

    private static LatLng defaultLatLng = new LatLng(57.737733d, 41.012231d);

    public static LatLng get(LatLng latLng) {
        return get(latLng.getLatitude(), latLng.getLongitude());
    }

    public static LatLng get() {
        return get(defaultLatLng);
    }

    public static LatLng get(double latitude, double longitude) {
        Random rnd = new Random();
        int degree = rnd.nextInt((BOUND - 10) + 1) + 10;
        return new LatLng(
                latitude + RADIUS * Math.cos(degree),
                longitude + RADIUS * Math.sin(degree)
        );
    }

}
