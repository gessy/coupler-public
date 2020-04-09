package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.holder.PlacesListsItemViewHolder;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;

public class PlacesListsAdapter extends RecyclerView.Adapter<PlacesListsItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Placelist> lists;

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
        final Placelist current = lists != null ? lists.get(position) : Placelist.empty();
        holder.update(current);
    }

    public void setPlacesLists(List<Placelist> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
