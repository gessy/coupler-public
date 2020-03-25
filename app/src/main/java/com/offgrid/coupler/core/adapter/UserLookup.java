package com.offgrid.coupler.core.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.core.holder.ContactListItemViewHolder;

public class UserLookup extends ItemDetailsLookup {

    private final RecyclerView recyclerView;

    public UserLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof ContactListItemViewHolder) {
                return ((ContactListItemViewHolder) viewHolder).getItemDetails();
            }
        }

        return null;
    }
}
