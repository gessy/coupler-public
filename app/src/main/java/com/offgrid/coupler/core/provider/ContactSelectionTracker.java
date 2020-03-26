package com.offgrid.coupler.core.provider;


import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.data.entity.User;

import java.util.List;

public class ContactSelectionTracker {
    private static final String SELECTION_ID = "contact-selection-id";

    public SelectionTracker get(RecyclerView recyclerView, List<User> users) {
        return new SelectionTracker.Builder<>(
                SELECTION_ID,
                recyclerView,
                new UserKeyProvider(users),
                new UserLookup(recyclerView),
                StorageStrategy.createLongStorage()
        ).build();
    }

}
