package com.offgrid.coupler.util;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Random;

public class RandomGps {

    private static int bound = 31917;
    private static double devision = 1000000d;

    private static LatLng defaultLatLng = new LatLng(57.737733d, 41.012231d);

    public static LatLng getRandLocation(LatLng latLng) {
        Random rnd = new Random();

        double deltaLat = (rnd.nextInt((bound - 10) + 1) + 10) / devision;
        double deltaLong = (rnd.nextInt((bound - 10) + 1) + 10) / devision;

        return new LatLng(
                latLng.getLatitude() + deltaLat,
                latLng.getLongitude() + deltaLong
        );
    }

    public static LatLng getRandLocation(double latitude, double longitude) {
        Random rnd = new Random();

        double deltaLat = (rnd.nextInt((bound - 10) + 1) + 10) / devision;
        double deltaLong = (rnd.nextInt((bound - 10) + 1) + 10) / devision;

        return new LatLng(
                latitude + deltaLat,
                longitude + deltaLong
        );
    }

    public static LatLng getRandLocation() {
        return getRandLocation(defaultLatLng);
    }

}
