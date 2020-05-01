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
    private final ImageView searchLocation;

    private View.OnClickListener listener;

    public RegionCountryViewHolder(View itemView) {
        super(itemView);
        regionName = itemView.findViewById(R.id.region_name);
        countryName = itemView.findViewById(R.id.country_name);
        tilesCount = itemView.findViewById(R.id.tiles_count);
        searchLocation = itemView.findViewById(R.id.search_location);
    }

    public void update(RegionCountry regionCountry) {
        regionName.setText(regionCountry.region.getName());
        countryName.setText(regionCountry.country.getName());
        tilesCount.setText("3.2K");

        if (regionCountry.region.getDownloadState().equals(DownloadState.DOWNLOADED)) {
            searchLocation.setOnClickListener(listener);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
