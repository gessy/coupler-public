package com.offgrid.coupler.controller.offline;

import android.content.Context;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.offgrid.coupler.R;
import com.offgrid.coupler.core.callback.RegionCallback;
import com.offgrid.coupler.core.model.converter.RegionMetadataConverter;
import com.offgrid.coupler.data.entity.Region;


public class MapRegionJanitor {

    private Context context;
    private RegionCallback callback;

    public MapRegionJanitor(Context context) {
        this.context = context;
    }

    public void setCallback(RegionCallback callback) {
        this.callback = callback;
    }

    public void delete(final Region region) {
        Mapbox.getInstance(context, context.getResources().getString(R.string.map_access_token));

        OfflineManager offlineManager = OfflineManager.getInstance(context);

        offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
            @Override
            public void onList(OfflineRegion[] offlineRegions) {
                if (offlineRegions == null || offlineRegions.length == 0) {
                    return;
                }

                for (OfflineRegion offlineRegion : offlineRegions) {
                    String regionName = RegionMetadataConverter.regionName(offlineRegion.getMetadata());
                    if (region.getName().equals(regionName)) {
                        offlineRegion.delete(new OfflineRegion.OfflineRegionDeleteCallback() {
                            @Override
                            public void onDelete() {
                                if (callback != null) {
                                    callback.call(region);
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
