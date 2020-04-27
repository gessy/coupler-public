package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.RegionCountry;

public class RegionViewHolder extends RecyclerView.ViewHolder {

    private final TextView regionName;
    private final TextView countryName;
    private final TextView tilesCount;

    public RegionViewHolder(View itemView) {
        super(itemView);
        regionName = itemView.findViewById(R.id.region_name);
        countryName = itemView.findViewById(R.id.country_name);
        tilesCount = itemView.findViewById(R.id.tiles_count);
    }

    public void update(RegionCountry regionCountry) {
        regionName.setText(regionCountry.region.getName());
        countryName.setText(regionCountry.country.getName());
        tilesCount.setText("3.2K");
    }

}
