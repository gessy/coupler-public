package com.offgrid.coupler.controller.place;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.place.dialog.PlaceDialog;
import com.offgrid.coupler.core.adapter.PlaceAdapter;
import com.offgrid.coupler.core.callback.PlaceCallback;
import com.offgrid.coupler.core.callback.SwipeToDeleteCallback;
import com.offgrid.coupler.core.model.dto.PlacelistDto;
import com.offgrid.coupler.core.model.view.ListPlacesViewModel;
import com.offgrid.coupler.data.entity.ListPlaces;
import com.offgrid.coupler.data.entity.Place;


public class PlacesActivity extends AppCompatActivity
        implements OnClickListener, OnCheckedChangeListener, Observer<ListPlaces> {

    private PlaceAdapter placeAdapter;
    private ListPlacesViewModel listPlacesViewModel;
    private PlacelistDto placelistDto;

    private Switch switcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placelistDto = PlacelistDto.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_places);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(placelistDto.getName());

        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        placeAdapter = new PlaceAdapter(this);
        placeAdapter.setPlaceDialog(createPlaceDialog());

        RecyclerView recyclerView = findViewById(R.id.recyclerview_place);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper touchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                listPlacesViewModel.removePlace(placeAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });
        touchHelper.attachToRecyclerView(recyclerView);

        findViewById(R.id.layout_visibility_status).setOnClickListener(this);
        switcher = findViewById(R.id.placelist_visibility_status);
        switcher.setOnCheckedChangeListener(this);

        initViewModels();
    }

    public void initViewModels() {
        listPlacesViewModel = new ViewModelProvider(this).get(ListPlacesViewModel.class);
        listPlacesViewModel.load(placelistDto.getId());
        listPlacesViewModel.observe(this, this);
    }

    private PlaceDialog createPlaceDialog() {
        return new PlaceDialog(this)
                .withOnCreateListener(new PlaceCallback() {
                    @Override
                    public void call(Place place) {
                        listPlacesViewModel.updatePlace(place);
                    }
                }).create();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layout_visibility_status) {
            switcher.setChecked(!switcher.isChecked());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean state) {
        listPlacesViewModel.visibility(state);
    }

    @Override
    public void onChanged(ListPlaces listPlaces) {
        if (listPlaces != null) {
            placeAdapter.setPlaces(listPlaces.place);
            if (switcher.isChecked() != listPlaces.placelist.getShowOnMap()) {
                switcher.setChecked(listPlaces.placelist.getShowOnMap());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }
}
