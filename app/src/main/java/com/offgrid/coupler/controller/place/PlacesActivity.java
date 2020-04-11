package com.offgrid.coupler.controller.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.PlaceAdapter;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.data.entity.Place;

import java.util.Arrays;


public class PlacesActivity extends AppCompatActivity {

    private PlaceAdapter placeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_places);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(info.getTitle());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        placeAdapter = new PlaceAdapter(this);
        placeAdapter.setPlaces(Arrays.asList(
                new Place("Tree", "This is a Tree"),
                new Place("Compas", "Camping sdfsdfdfsdf sfsdfdsfsd sddfgf sdfsfsd sdfsdf dgfdfgdf dfgfdgrte dfgdfg dfgdf g")
        ));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_place);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
