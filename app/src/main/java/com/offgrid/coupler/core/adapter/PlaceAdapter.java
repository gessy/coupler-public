package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.place.dialog.PlaceDialog;
import com.offgrid.coupler.core.holder.PlaceItemViewHolder;
import com.offgrid.coupler.data.entity.Place;

import java.util.List;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Place> places;
    private Context context;

    private PlaceDialog placeDialog;

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

        ClickListener listener = new ClickListener(current);
        holder.itemView.setOnLongClickListener(listener);
        holder.itemView.setOnClickListener(listener);
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    public void setPlaceDialog(PlaceDialog placeDialog) {
        this.placeDialog = placeDialog;
    }

    public Place getItem(int position) {
        return places.get(position);

    }


    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }


    private class ClickListener implements OnClickListener, OnLongClickListener {
        Place current;

        private ClickListener(Place current) {
            this.current = current;
        }

        @Override
        public void onClick(View v) {
            //TODO move to the map
        }

        @Override
        public boolean onLongClick(View v) {
            placeDialog.show(current);
            return true;
        }
    }

}
