package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.offline.MapRegionLoader;
import com.offgrid.coupler.core.callback.RegionCallback;
import com.offgrid.coupler.core.holder.RegionViewHolder;
import com.offgrid.coupler.data.entity.Region;

import java.util.List;

import static com.offgrid.coupler.data.model.DownloadState.DOWNLOADED;
import static com.offgrid.coupler.data.model.DownloadState.IN_PROGRESS;
import static com.offgrid.coupler.data.model.DownloadState.READY_TO_LOAD;


public class RegionAdapter extends RecyclerView.Adapter<RegionViewHolder> {

    private final LayoutInflater mInflater;
    private List<Region> list;
    private Context context;

    private RegionCallback callback;


    public RegionAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.region_item, parent, false);
        return new RegionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RegionViewHolder holder, int position) {
        final Region region = list.get(position);
        holder.update(region);
        if (region.checkDownloadState(READY_TO_LOAD)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapRegionLoader regionLoader = new MapRegionLoader(context);
                    regionLoader.setObserver(new MapRegionLoader.Observer() {
                        @Override
                        public void onStatusChanged(OfflineRegionStatus status) {
                            if (status.isComplete()) {
                                region.setDownloadState(DOWNLOADED);
                                callback.call(region);
                            } else if (status.isRequiredResourceCountPrecise()) {
                                if (region.checkDownloadState(READY_TO_LOAD)) {
                                    region.setDownloadState(IN_PROGRESS);
                                    callback.call(region);
                                }
                            }
                        }
                    });

                    regionLoader.load(region);
                }
            });
        }
    }

    public void setRegionList(List<Region> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setCallback(RegionCallback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

}
