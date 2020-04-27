package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.holder.RegionViewHolder;
import com.offgrid.coupler.data.entity.Region;
import com.offgrid.coupler.data.entity.RegionCountry;

import java.util.List;


public class RegionAdapter extends RecyclerView.Adapter<RegionViewHolder> {

    private final LayoutInflater mInflater;
    private List<RegionCountry> list;
    private Context context;


    public RegionAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.region_country_item, parent, false);
        return new RegionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RegionViewHolder holder, int position) {
        if (list != null) {
            holder.update(list.get(position));
        }
    }

    public void setRegionCountryList(List<RegionCountry> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Region getRegion(int position) {
        return list.get(position).region;
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

}
