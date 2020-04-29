package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.RegionCountry;
import com.offgrid.coupler.data.model.DownloadState;

public class RegionCountryViewHolder extends RecyclerView.ViewHolder {

    private final TextView regionName;
    private final TextView countryName;
    private final TextView tilesCount;
    private final ImageView downloadState;

    public RegionCountryViewHolder(View itemView) {
        super(itemView);
        regionName = itemView.findViewById(R.id.region_name);
        countryName = itemView.findViewById(R.id.country_name);
        tilesCount = itemView.findViewById(R.id.tiles_count);
        downloadState = itemView.findViewById(R.id.download_state_icon);
    }

    public void update(RegionCountry regionCountry) {
        regionName.setText(regionCountry.region.getName());
        countryName.setText(regionCountry.country.getName());
        tilesCount.setText("3.2K");

        if (regionCountry.region.getDownloadState().equals(DownloadState.DOWNLOADED)) {
            downloadState.setVisibility(View.GONE);
        } else {
            downloadState.setVisibility(View.VISIBLE);
        }

    }

}
