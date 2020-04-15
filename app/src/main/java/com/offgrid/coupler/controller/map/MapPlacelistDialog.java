package com.offgrid.coupler.controller.map;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.MapPlacelistAdapter;
import com.offgrid.coupler.core.callback.PlacelistCallback;
import com.offgrid.coupler.core.model.view.PlacelistViewModel;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class MapPlacelistDialog implements Observer<List<Placelist>>, View.OnClickListener {

    private Fragment fragment;
    private MapPlacelistAdapter placelistAdapter;
    private PlacelistViewModel placelistViewModel;
    private AlertDialog dialog;
    private String title;

    private PlacelistCallback callback;

    private boolean addedPlacelist = false;
    private List<Placelist> placelists = new ArrayList<>();


    public MapPlacelistDialog(Fragment fragment) {
        this.fragment = fragment;
    }


    public MapPlacelistDialog withOnClickListener(PlacelistCallback callback) {
        this.callback = callback;
        return this;
    }

    public MapPlacelistDialog withTitle(String title) {
        this.title = title;
        return this;
    }


    private View createView() {
        View view = fragment.getLayoutInflater().inflate(R.layout.dialog_placelist, null, false);

        placelistAdapter = new MapPlacelistAdapter(fragment.getContext());
        placelistAdapter.setOnClickListener(callback);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_placelist);
        recyclerView.setAdapter(placelistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

        placelistViewModel = new ViewModelProvider(fragment).get(PlacelistViewModel.class);
        placelistViewModel.observe(fragment, this);
        placelistViewModel.load();

        view.findViewById(R.id.create_list).setOnClickListener(this);
        view.findViewById(R.id.cancel_list).setOnClickListener(this);

        return view;
    }


    public MapPlacelistDialog create() {
        dialog = new AlertDialog
                .Builder(requireNonNull(fragment.getContext()))
                .setTitle(title)
                .setView(createView())
                .create();
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_list) {
            addedPlacelist = true;
            dialog.dismiss();
        }

        if (v.getId() == R.id.cancel_list) {
            dialog.cancel();
        }
    }

    @Override
    public void onChanged(List<Placelist> placelists) {
        if (addedPlacelist) {
            List<Placelist> copyList = new ArrayList<>(placelists);
            copyList.removeAll(this.placelists);
        } else {
            placelistAdapter.setPlacelists(placelists);
            this.placelists = placelists;
        }
    }
}
