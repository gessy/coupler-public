package com.offgrid.coupler.core.provider;

import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.OnDragInitiatedListener;
import androidx.recyclerview.selection.OnItemActivatedListener;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.core.adapter.UserLookup;
import com.offgrid.coupler.data.entity.User;

import java.util.List;

public class ContactSelectionTracker {

    public SelectionTracker get(RecyclerView recyclerView, List<User> users) {
        return new SelectionTracker.Builder<>(
                "my-selection-id",
                recyclerView,
                new UserKeyProvider(users),
                new UserLookup(recyclerView),
                StorageStrategy.createLongStorage()
        ).withOnItemActivatedListener(new OnItemActivatedListener<Long>() {
            @Override
            public boolean onItemActivated(@NonNull ItemDetailsLookup.ItemDetails<Long> item, @NonNull MotionEvent e) {
                Log.d("ZZZ", "Selected ItemId: " + item.toString());
                return true;
            }
        }).withOnDragInitiatedListener(new OnDragInitiatedListener() {
            @Override
            public boolean onDragInitiated(@NonNull MotionEvent e) {
                Log.d("ZZZ", "onDragInitiated");
                return true;
            }
        }).build();
    }

}
