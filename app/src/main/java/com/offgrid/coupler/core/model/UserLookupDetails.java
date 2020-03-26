package com.offgrid.coupler.core.model;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

import com.offgrid.coupler.data.entity.User;

public class UserLookupDetails extends ItemDetailsLookup.ItemDetails {
    private final int adapterPosition;
    private final User selectionKey;

    public UserLookupDetails(int adapterPosition, User selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey() {
        return selectionKey;
    }
}