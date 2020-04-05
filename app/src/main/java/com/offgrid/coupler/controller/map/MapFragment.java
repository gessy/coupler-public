package com.offgrid.coupler.controller.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.map.configurator.ContactLocationConfigurator;
import com.offgrid.coupler.controller.map.configurator.DeviceLocationConfigurator;
import com.offgrid.coupler.controller.map.listener.HighlightMarkerContactLocationListener;
import com.offgrid.coupler.core.model.view.ContactListViewModel;
import com.offgrid.coupler.data.entity.User;

import java.util.ArrayList;
import java.util.List;

import static com.offgrid.coupler.controller.map.MapConstants.*;


public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, Observer<List<User>> {

    private View rootView;
    private MapView mapView;
    private MapboxMap mapboxMap;

    private ContactListViewModel contactListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Mapbox.getInstance(getActivity(), getString(R.string.map_access_token));
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        contactListViewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        contactListViewModel.observe(this, this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                new DeviceLocationConfigurator()
                        .withContext(getActivity())
                        .withMapbox(MapFragment.this.mapboxMap)
                        .configure();

                new ContactLocationConfigurator()
                        .withContext(getActivity())
                        .withMapbox(MapFragment.this.mapboxMap)
                        .configure();

                contactListViewModel.load();
            }
        });

        mapboxMap.addOnMapClickListener(new HighlightMarkerContactLocationListener(mapboxMap));

        rootView.findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_to_camera_tracking_mode) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);
            locationComponent.zoomWhileTracking(16f);
        }
    }

    @Override
    public void onChanged(List<User> users) {
        if (!users.isEmpty()) {
            final List<Feature> featureList = new ArrayList<>();
            for (User user : users) {
                LatLng loc = user.getLocation();
                featureList.add(Feature.fromGeometry(Point.fromLngLat(loc.getLongitude(), loc.getLatitude())));
            }

            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    GeoJsonSource resultSource = style.getSourceAs(USER_LOCATION_GEOJSON_ID);
                    resultSource.setGeoJson(FeatureCollection.fromFeatures(featureList));
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}