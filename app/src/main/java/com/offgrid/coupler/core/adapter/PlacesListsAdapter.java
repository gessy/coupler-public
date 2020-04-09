package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.holder.PlacesListsItemViewHolder;

import java.util.List;

public class PlacesListsAdapter extends RecyclerView.Adapter<PlacesListsItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<String> lists;

    public PlacesListsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlacesListsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.places_lists_item, parent, false);
        return new PlacesListsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlacesListsItemViewHolder holder, int position) {
        final String current = lists != null ? lists.get(position) : "";
        holder.update(current, 3);
    }

    public void setPlacesLists(List<String> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
