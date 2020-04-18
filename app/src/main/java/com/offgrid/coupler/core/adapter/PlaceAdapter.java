package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.holder.PlaceItemViewHolder;
import com.offgrid.coupler.data.entity.Place;

import java.util.List;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Place> places;
    private Context context;

    public PlaceAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlaceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.place_item, parent, false);
        return new PlaceItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceItemViewHolder holder, int position) {
        final Place current = places != null ? places.get(position) : Place.empty();
        holder.update(current);
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }


    public Place getItem(int position) {
        return places.get(position);

    }

    public void removeAt(int position) {
        places.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }
}
