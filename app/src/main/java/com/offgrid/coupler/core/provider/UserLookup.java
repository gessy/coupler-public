package com.offgrid.coupler.core.provider;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.core.holder.ContactItemViewHolder;

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
            ContactItemViewHolder holder = (ContactItemViewHolder) recyclerView.getChildViewHolder(view);
            if (holder != null) return holder.getLookupDetails();
        }

        return null;
    }
}
