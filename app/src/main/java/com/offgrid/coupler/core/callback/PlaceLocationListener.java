package com.offgrid.coupler.core.callback;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.map.MapService;
import com.offgrid.coupler.controller.place.dialog.PlaceDialog;
import com.offgrid.coupler.controller.place.dialog.PlaceWorkflowDialog;
import com.offgrid.coupler.controller.place.dialog.PlacelistDialog;
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;
import com.offgrid.coupler.core.model.Operation;
import com.offgrid.coupler.core.model.dto.PlaceDto;
import com.offgrid.coupler.core.model.map.MapLayerResponse;
import com.offgrid.coupler.core.model.map.MapLayerResponse.Action;
import com.offgrid.coupler.core.model.view.PlaceViewModel;
import com.offgrid.coupler.core.model.view.PlacelistViewModel;
import com.offgrid.coupler.data.entity.Place;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.ArrayList;
import java.util.Arrays;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;
import static com.mapbox.geojson.Feature.fromGeometry;
import static com.mapbox.geojson.Point.fromLngLat;
import static com.offgrid.coupler.controller.map.MapConstants.NEW_PLACE_LOCATION_GEOJSON_ID;
import static com.offgrid.coupler.controller.map.MapConstants.SELECTED_PLACE_LOCATION_GEOJSON_ID;
import static com.offgrid.coupler.controller.map.MapConstants.SELECTED_PLACE_LOCATION_LAYER_ID;

public class PlaceLocationListener extends AbstractLocationListener implements Observer<Object> {

    private MapboxMap mapboxMap;
    private Fragment fragment;

    private PlaceWorkflowDialog workflowDialog;
    private PlaceDialog placeDialog;
    private PlacelistDialog placelistDialog;

    private PlaceDetailsViewHolder viewHolder;

    private PlaceViewModel placeViewModel;

    private ImageButton savePlace;

    private boolean markerSelected = false;
    private ValueAnimator markerAnimator;

    private PlaceDto place;


    public PlaceLocationListener(Fragment fragment) {
        super(R.id.bottom_sheet_place_details);
        this.fragment = fragment;
    }

    public PlaceLocationListener withMapbox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        this.markerAnimator = getAnimator();
        return this;
    }

    public PlaceLocationListener withRootView(View rootView) {
        super.rootView = rootView;
        this.viewHolder = new PlaceDetailsViewHolder(rootView);
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
                        viewHolder.setPlacelist(placelist);
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
                .withPlaceHolder(viewHolder)
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
        bottomSheet(new BottomSheetCallback());

        rootView.findViewById(R.id.close_place_details).setOnClickListener(this);

        savePlace = rootView.findViewById(R.id.btn_save_place);
        savePlace.setOnClickListener(this);
        mapboxMap.addOnMapLongClickListener(this);
        mapboxMap.addOnMapClickListener(this);
    }

    private void cleanUp() {
        viewHolder.cleanUp();
        savePlace.setActivated(false);
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
        Style style = mapboxMap.getStyle();
        if (style == null) {
            return false;
        }

        MapLayerResponse response = MapService.placeFeature(mapboxMap, point, markerSelected);

        if (response.state == Action.HIDE) {
            bottomSheet(STATE_HIDDEN);
            return false;
        }

        if (response.state == Action.EXPAND) {
            GeoJsonSource source = style.getSourceAs(SELECTED_PLACE_LOCATION_GEOJSON_ID);
            source.setGeoJson(FeatureCollection.fromFeatures(new Feature[]{
                    Feature.fromGeometry(response.feature.geometry())
            }));

            place = PlaceDto.getInstance(response.feature);
            displayPlace();
        }

        return false;
    }



    private void displayPlace() {
        savePlace.setActivated(true);
        viewHolder.update(place);
        selectMarker();
        bottomSheet(STATE_EXPANDED);
    }


    private void selectMarker() {
        markerAnimator.setObjectValues(1f, 2f);
        markerAnimator.start();
        markerSelected = true;
    }

    private void deselectMarker() {
        markerAnimator.setObjectValues(2f, 1f);
        markerAnimator.start();
        markerSelected = false;
    }


    private ValueAnimator getAnimator() {
        ValueAnimator markerAnimator = new ValueAnimator();
        markerAnimator.setDuration(300);
        markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    SymbolLayer layer = (SymbolLayer) style.getLayer(SELECTED_PLACE_LOCATION_LAYER_ID);
                    layer.setProperties(PropertyFactory.iconSize((float) animator.getAnimatedValue()));
                }
            }
        });

        return markerAnimator;
    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        Style style = mapboxMap.getStyle();

        markerSelected = false;

        cleanUp();

        viewHolder.setPlaceLocation(point);

        GeoJsonSource source = style.getSourceAs(NEW_PLACE_LOCATION_GEOJSON_ID);
        if (source != null) {
            Feature feature = fromGeometry(fromLngLat(point.getLongitude(), point.getLatitude()));
            source.setGeoJson(FeatureCollection.fromFeatures(Arrays.asList(feature)));
        }

        bottomSheet(STATE_EXPANDED);

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

        bottomSheet(STATE_HIDDEN);
    }


    class BottomSheetCallback extends BaseBottomSheetCallback {
        @Override
        protected void onStateHidden(@NonNull View bottomSheet) {
            super.onStateHidden(bottomSheet);
            if (markerSelected) deselectMarker();
            Style style = mapboxMap.getStyle();
            GeoJsonSource source = style.getSourceAs(NEW_PLACE_LOCATION_GEOJSON_ID);
            source.setGeoJson(FeatureCollection.fromFeatures(new ArrayList<Feature>()));
            showFloatingButton();
        }

        @Override
        protected void onStateExpanded(@NonNull View bottomSheet) {
            super.onStateExpanded(bottomSheet);
            if (!markerSelected) selectMarker();
            hideFloatingButton();
        }

        @Override
        protected void onStateCollapsed(@NonNull View bottomSheet) {
            super.onStateCollapsed(bottomSheet);
            if (markerSelected) deselectMarker();
            hideFloatingButton();
        }
    }

}
