package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;

public class PlacesListsItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView listName;
    private final TextView placesCount;

    public PlacesListsItemViewHolder(View itemView) {
        super(itemView);
        listName = itemView.findViewById(R.id.places_list_name);
        placesCount = itemView.findViewById(R.id.places_count);
    }

    public void update(String listName, Integer placesCount) {
        this.listName.setText(listName);
        this.placesCount.setText(placesCount.toString());
    }

}
