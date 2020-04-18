package com.offgrid.coupler.controller.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.offgrid.coupler.core.adapter.PlaceAdapter;
import com.offgrid.coupler.core.callback.SwipeToDeleteCallback;
import com.offgrid.coupler.core.model.dto.PlacelistDto;
import com.offgrid.coupler.core.model.view.ListPlacesViewModel;
import com.offgrid.coupler.data.entity.ListPlaces;


public class PlacesActivity extends AppCompatActivity implements Observer<ListPlaces> {

    private PlaceAdapter placeAdapter;
    private ListPlacesViewModel listPlacesViewModel;
    private PlacelistDto placelistDto;

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        placeAdapter = new PlaceAdapter(this);

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

        initViewModels();
    }

    public void initViewModels() {
        listPlacesViewModel = new ViewModelProvider(this).get(ListPlacesViewModel.class);
        listPlacesViewModel.load(placelistDto.getId());
        listPlacesViewModel.observe(this, this);
    }

    @Override
    public void onChanged(ListPlaces listPlaces) {
        placeAdapter.setPlaces(listPlaces.place);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_edit) {
            setResult(RESULT_OK, new Intent());
            finish();
            overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }
}
