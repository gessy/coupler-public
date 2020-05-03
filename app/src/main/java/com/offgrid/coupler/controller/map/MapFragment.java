package com.offgrid.coupler.controller.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.map.configurator.ContactLocationConfigurator;
import com.offgrid.coupler.controller.map.configurator.DeviceLocationConfigurator;
import com.offgrid.coupler.controller.map.configurator.PlaceLocationConfigurator;
import com.offgrid.coupler.core.callback.ContactLocationListener;
import com.offgrid.coupler.core.callback.PlaceLocationListener;
import com.offgrid.coupler.core.model.command.Command;
import com.offgrid.coupler.core.model.command.CommandAcceptor;
import com.offgrid.coupler.core.model.command.RegionLocationCommand;


public class MapFragment extends Fragment
        implements OnMapReadyCallback, View.OnClickListener, Observer<Command>, CommandAcceptor {

    private View rootView;
    private MapView mapView;
    private MapboxMap mapboxMap;

    private final MutableLiveData<Command> pipeCommand = new MutableLiveData();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        Mapbox.getInstance(getActivity(), getString(R.string.map_access_token));
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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

                new PlaceLocationConfigurator()
                        .withContext(getActivity())
                        .withMapbox(MapFragment.this.mapboxMap)
                        .configure();

                pipeCommand.observe(MapFragment.this, MapFragment.this);
            }
        });

        ContactLocationListener contactLocationListener = new ContactLocationListener(getActivity())
                .withMapbox(mapboxMap)
                .withRootView(rootView);
        contactLocationListener.attach();

        PlaceLocationListener placeLocationListener = new PlaceLocationListener(this)
                .withMapbox(mapboxMap)
                .withRootView(rootView)
                .withViewModel()
                .withDialog();
        placeLocationListener.attach();

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
    public void onChanged(Command command) {
        if (command instanceof RegionLocationCommand) {
            RegionLocationCommand cmd = (RegionLocationCommand) command;
            mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(cmd.getLocation())
                            .zoom(cmd.getZoom())
                            .build()
            ));
        }
    }

    @Override
    public void accept(Command command) {
        pipeCommand.setValue(command);
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