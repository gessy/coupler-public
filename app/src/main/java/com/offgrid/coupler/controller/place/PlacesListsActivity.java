package com.offgrid.coupler.controller.place;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.PlacesListsAdapter;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.core.model.view.PlacelistViewModel;
import com.offgrid.coupler.data.entity.Placelist;

import androidx.lifecycle.Observer;

import java.util.List;


public class PlacesListsActivity extends AppCompatActivity
        implements View.OnClickListener, Observer<List<Placelist>> {

    private PlacesListsAdapter placesListsAdapter;
    private PlacelistViewModel placeListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_places_lists);
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

        placesListsAdapter = new PlacesListsAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_places_lists);
        recyclerView.setAdapter(placesListsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_create_list).setOnClickListener(this);

        placeListViewModel = new ViewModelProvider(this).get(PlacelistViewModel.class);
        placeListViewModel.observe(this, PlacesListsActivity.this);
        placeListViewModel.load();
    }


    private void showNewListDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_new_list, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(PlacesListsActivity.this)
                .setTitle("New List")
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.save_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = dialogView.findViewById(R.id.new_places_list_name);
                placeListViewModel.insert(input.getText().toString());
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.cancel_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_create_list) {
            showNewListDialog();
        }
    }

    @Override
    public void onChanged(List<Placelist> placelists) {
        placesListsAdapter.setPlacesLists(placelists);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_edit) {
            Toast.makeText(this, "Edit list", Toast.LENGTH_SHORT).show();
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
