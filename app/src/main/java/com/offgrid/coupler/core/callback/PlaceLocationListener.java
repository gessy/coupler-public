package com.offgrid.coupler.core.callback;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.State;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener;
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapLongClickListener;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.place.dialog.PlaceDialog;
import com.offgrid.coupler.controller.place.dialog.PlaceWorkflowDialog;
import com.offgrid.coupler.controller.place.dialog.PlacelistDialog;
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;
import com.offgrid.coupler.core.model.view.Operation;
import com.offgrid.coupler.core.model.view.PlaceViewModel;
import com.offgrid.coupler.core.model.view.PlacelistViewModel;
import com.offgrid.coupler.data.entity.Place;
import com.offgrid.coupler.data.entity.Placelist;


import java.util.ArrayList;
import java.util.Arrays;

import static com.mapbox.geojson.Feature.fromGeometry;
import static com.mapbox.geojson.Point.fromLngLat;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_GEOJSON_ID;

public class PlaceLocationListener
        implements OnClickListener, OnMapClickListener, OnMapLongClickListener, Observer<Object> {

    private MapboxMap mapboxMap;
    private Fragment fragment;

    private PlaceWorkflowDialog workflowDialog;
    private PlaceDialog placeDialog;
    private PlacelistDialog placelistDialog;

    private BottomSheetBehavior bottomSheet;
    private PlaceDetailsViewHolder placeViewHolder;

    private PlaceViewModel placeViewModel;

    private ImageButton savePlace;
    private View rootView;


    public PlaceLocationListener(Fragment fragment) {
        this.fragment = fragment;
    }

    public PlaceLocationListener withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        return this;
    }

    public PlaceLocationListener withRootView(View rootView) {
        this.rootView = rootView;
        this.placeViewHolder = new PlaceDetailsViewHolder(rootView);
        return this;
    }

    public PlaceLocationListener withViewModel() {
        placeViewModel = new ViewModelProvider(fragment).get(PlaceViewModel.class);
        placeViewModel.observeOperation(fragment, this);
        return this;
    }

    public PlaceLocationListener withDialog() {
        workflowDialog = new PlaceWorkflowDialog(fragment)
                .withOnItemClickListener(new PlacelistCallback() {
                    @Override
                    public void call(Placelist placelist) {
                        workflowDialog.dismiss();
                        placeViewHolder.setPlacelist(placelist);
                        placeDialog.show();
                    }
                })
                .withOnCreateListener(new PlacelistCallback() {
                    @Override
                    public void call(Placelist placelist) {
                        workflowDialog.dismiss();
                        placelistDialog.show();
                    }
                })
                .create();

        placeDialog = new PlaceDialog(fragment.getContext())
                .withPlaceHolder(placeViewHolder)
                .withOnCreateListener(new PlaceCallback() {
                    @Override
                    public void call(Place place) {
                        placeViewModel.insert(place);
                        placeDialog.dismiss();
                    }
                })
                .create();

        placelistDialog = new PlacelistDialog(fragment.getContext())
                .withOnCreateListener(new PlacelistCallback() {
                    @Override
                    public void call(Placelist placelist) {
                        new ViewModelProvider(fragment)
                                .get(PlacelistViewModel.class)
                                .insert(placelist);
                        placelistDialog.dismiss();
                    }
                })
                .create();

        return this;
    }

    public void attach() {
        rootView.findViewById(R.id.close_place_details).setOnClickListener(this);

        savePlace = rootView.findViewById(R.id.btn_save_place);
        savePlace.setOnClickListener(this);
        mapboxMap.addOnMapLongClickListener(this);
        mapboxMap.addOnMapClickListener(this);
    }

    private void cleanUp() {
        placeViewHolder.cleanUp();
        savePlace.setActivated(false);
    }


    private void bottomSheet(@State int state) {
        if (state == BottomSheetBehavior.STATE_HIDDEN && bottomSheet != null) {
            bottomSheet.setState(state);
        } else if (state == BottomSheetBehavior.STATE_EXPANDED) {
            if (bottomSheet == null) {
                bottomSheet = BottomSheetBehavior.from(rootView.findViewById(R.id.bottom_sheet_place_details));
                bottomSheet.addBottomSheetCallback(new BottomSheetCallback());
            }
            bottomSheet.setState(state);
        }
    }


    @Override
    public void onChanged(Object o) {
        if (o instanceof Operation) {
            Operation operation = (Operation) o;
            if (Operation.INSERT.equals(operation)) {
                savePlace.setActivated(true);
            }
            if (Operation.DELETE.equals(operation)) {
                savePlace.setActivated(false);
            }
        }
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        bottomSheet(BottomSheetBehavior.STATE_HIDDEN);
        return false;
    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        Style style = mapboxMap.getStyle();

        cleanUp();

        placeViewHolder.setPlaceLocation(point);

        GeoJsonSource source = style.getSourceAs(NEW_PLACE_LOCATION_GEOJSON_ID);
        if (source != null) {
            Feature feature = fromGeometry(fromLngLat(point.getLongitude(), point.getLatitude()));
            source.setGeoJson(FeatureCollection.fromFeatures(Arrays.asList(feature)));
        }

        bottomSheet(BottomSheetBehavior.STATE_EXPANDED);

        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save_place) {
            if (!savePlace.isActivated()) {
                workflowDialog.show();
            }
            return;
        }

        bottomSheet(BottomSheetBehavior.STATE_HIDDEN);

    }

    class BottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    Style style = mapboxMap.getStyle();
                    GeoJsonSource source = style.getSourceAs(NEW_PLACE_LOCATION_GEOJSON_ID);
                    source.setGeoJson(FeatureCollection.fromFeatures(new ArrayList<Feature>()));
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED:
                case BottomSheetBehavior.STATE_DRAGGING:
                case BottomSheetBehavior.STATE_HALF_EXPANDED:
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }

}
