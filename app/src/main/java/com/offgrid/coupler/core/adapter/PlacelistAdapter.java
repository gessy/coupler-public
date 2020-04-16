package com.offgrid.coupler.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.place.dialog.PlacelistDialog;
import com.offgrid.coupler.controller.place.PlacesActivity;
import com.offgrid.coupler.core.holder.PlacelistItemViewHolder;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;


public class PlacelistAdapter extends RecyclerView.Adapter<PlacelistItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Placelist> lists;
    private Context context;

    private PlacelistDialog placelistDialog;

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

        ClickListener listener = new ClickListener(current);
        holder.itemView.setOnClickListener(listener);
        holder.itemView.setOnLongClickListener(listener);
    }

    public void setPlacelists(List<Placelist> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }


    public void setPlacelistDialog(PlacelistDialog placelistDialog) {
        this.placelistDialog = placelistDialog;
    }

    public Placelist getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }


    private class ClickListener implements View.OnClickListener, View.OnLongClickListener {
        Placelist current;

        private ClickListener(Placelist current) {
            this.current = current;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PlacesActivity.class);
            intent.putExtras(new Info.BundleBuilder()
                    .withTitle(current.getName())
                    .withText("This is places activity")
                    .build());
            context.startActivity(intent);
//                ((Activity) context).finish();
            ((Activity) context).overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
        }

        @Override
        public boolean onLongClick(View v) {
            placelistDialog.show(current);
            return true;
        }

    }

}
