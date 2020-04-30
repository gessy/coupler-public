package com.offgrid.coupler.controller.offline;

import android.content.Context;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.converter.RegionMetadataConverter;
import com.offgrid.coupler.data.entity.Region;


public class MapRegionLoader {

    private static final String DEFAULT_STYLE_URL = Style.MAPBOX_STREETS;

    public interface Observer {
        void onStatusChanged(OfflineRegionStatus status);
    }

    private Context context;
    private String styleURL = DEFAULT_STYLE_URL;
    private Observer observer;


    public MapRegionLoader(Context context) {
        this.context = context;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public void load(Region region) {
        byte[] metadata = RegionMetadataConverter.convert(region);
        if (metadata == null) return;

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(new LatLng(region.getNorthEastLatitude(), region.getNorthEastLongitude())) // Northeast
                .include(new LatLng(region.getSouthWestLatitude(), region.getSouthWestLongitude())) // Southwest
                .build();

        OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                styleURL,
                latLngBounds,
                region.getMinZoom(),
                region.getMaxZoom(),
                context.getResources().getDisplayMetrics().density);


        Mapbox.getInstance(context, context.getResources().getString(R.string.map_access_token));

        OfflineManager.getInstance(context).createOfflineRegion(
                definition,
                metadata,
                new OfflineManager.CreateOfflineRegionCallback() {
                    @Override
                    public void onCreate(OfflineRegion offlineRegion) {
                        offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);
                        offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
                            @Override
                            public void onStatusChanged(OfflineRegionStatus status) {
                                if (observer != null) {
                                    observer.onStatusChanged(status);
                                }
                            }

                            @Override
                            public void onError(OfflineRegionError error) {
                                Toast.makeText(context, "Error: " + error.getReason(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void mapboxTileCountLimitExceeded(long limit) {
                                Toast.makeText(context, "limit: " + limit, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                    }
                }
        );
    }
    
}
