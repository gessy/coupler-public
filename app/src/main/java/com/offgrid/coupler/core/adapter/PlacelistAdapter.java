package com.offgrid.coupler.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.place.PlacesActivity;
import com.offgrid.coupler.core.holder.PlacelistItemViewHolder;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;


public class PlacelistAdapter extends RecyclerView.Adapter<PlacelistItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Placelist> lists;
    private Context context;

    public PlacelistAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PlacelistItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.placelist_item, parent, false);
        return new PlacelistItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlacelistItemViewHolder holder, int position) {
        final Placelist current = lists != null ? lists.get(position) : Placelist.empty();
        holder.update(current);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlacesActivity.class);
                intent.putExtras(new Info.BundleBuilder()
                        .withTitle(current.getName())
                        .withText("This is places activity")
                        .build());
                context.startActivity(intent);
//                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
            }
        });
    }

    public void setPlacelists(List<Placelist> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }


    public Placelist getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
