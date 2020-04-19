package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.callback.PlacelistCallback;
import com.offgrid.coupler.core.holder.PlacelistItemViewHolder;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;


public class MapPlacelistAdapter extends RecyclerView.Adapter<PlacelistItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Placelist> lists;
    private PlacelistCallback callback;


    public MapPlacelistAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlacelistItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.placelist_item, parent, false);
        return new PlacelistItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlacelistItemViewHolder holder, int position) {
        final Placelist current = lists.get(position);
        holder.update(current);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.call(current);
            }
        });
    }

    public void setOnClickListener(PlacelistCallback callback) {
        this.callback = callback;
    }

    public void setPlacelists(List<Placelist> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

}
