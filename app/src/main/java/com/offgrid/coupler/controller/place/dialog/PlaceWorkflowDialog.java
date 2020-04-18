package com.offgrid.coupler.controller.place.dialog;

import android.view.View;

import androidx.annotation.StringRes;
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

public class PlaceWorkflowDialog implements Observer<List<Placelist>>, View.OnClickListener {

    private Fragment fragment;
    private AlertDialog dialog;

    private MapPlacelistAdapter placelistAdapter;
    private PlacelistViewModel placelistViewModel;

    private PlacelistCallback itemClickCallback;
    private PlacelistCallback createCallback;

    private List<Placelist> placelists = new ArrayList<>();
    private boolean addItem = false;


    public PlaceWorkflowDialog(Fragment fragment) {
        this.fragment = fragment;
    }

    public PlaceWorkflowDialog withOnItemClickListener(PlacelistCallback callback) {
        this.itemClickCallback = callback;
        return this;
    }

    public PlaceWorkflowDialog withOnCreateListener(PlacelistCallback callback) {
        this.createCallback = callback;
        return this;
    }


    private View createView() {
        View view = fragment.getLayoutInflater().inflate(R.layout.dialog_placelist, null, false);

        placelistAdapter = new MapPlacelistAdapter(fragment.getContext());
        placelistAdapter.setOnClickListener(itemClickCallback);

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

    public PlaceWorkflowDialog create() {
        dialog = new AlertDialog
                .Builder(requireNonNull(fragment.getContext()))
                .setTitle(getString(R.string.dialog_add_place_to_list))
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
            addItem = true;
            createCallback.call(null);
        }

        if (v.getId() == R.id.cancel_list) {
            addItem = false;
            cancel();
        }
    }

    @Override
    public void onChanged(List<Placelist> placelists) {
        placelistAdapter.setPlacelists(placelists);
        if (addItem) {
            itemClickCallback.call(getLastAdded(placelists));
        }

        setPlacelists(placelists);
    }

    private Placelist getLastAdded(List<Placelist> placelists) {
        List<Placelist> copy = new ArrayList<>(placelists);
        copy.removeAll(this.placelists);
        return copy.get(0);
    }

    private void setPlacelists(List<Placelist> placelists) {
        this.placelists.clear();
        this.placelists.addAll(placelists);
    }

    private String getString(@StringRes int resId) {
        return fragment.getContext().getResources().getString(resId);
    }
}
