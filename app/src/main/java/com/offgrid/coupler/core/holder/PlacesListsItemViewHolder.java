package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Placelist;

public class PlacesListsItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView listName;
    private final TextView placesCount;

    public PlacesListsItemViewHolder(View itemView) {
        super(itemView);
        listName = itemView.findViewById(R.id.placelist_name);
        placesCount = itemView.findViewById(R.id.places_count);
    }

    public void update(Placelist placelist) {
        this.listName.setText(placelist.getName());
        this.placesCount.setText("3");
    }

}
