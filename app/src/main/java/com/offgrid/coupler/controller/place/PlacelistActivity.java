package com.offgrid.coupler.controller.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.PlacelistAdapter;
import com.offgrid.coupler.core.callback.SwipeToDeleteCallback;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.core.model.view.PlacelistViewModel;
import com.offgrid.coupler.data.entity.Placelist;

import androidx.lifecycle.Observer;

import java.util.List;


public class PlacelistActivity extends AppCompatActivity
        implements View.OnClickListener, Observer<List<Placelist>> {

    private PlacelistAdapter placelistAdapter;
    private PlacelistViewModel placelistViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_placelist);
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

        placelistAdapter = new PlacelistAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_places_lists);
        recyclerView.setAdapter(placelistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ItemTouchHelper touchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                placelistViewModel.remove(placelistAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });

        touchHelper.attachToRecyclerView(recyclerView);

        findViewById(R.id.btn_create_list).setOnClickListener(this);

        placelistViewModel = new ViewModelProvider(this).get(PlacelistViewModel.class);
        placelistViewModel.observe(this, PlacelistActivity.this);
        placelistViewModel.load();
    }


    private void showNewListDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_new_list, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(PlacelistActivity.this)
                .setTitle("New List")
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.save_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = dialogView.findViewById(R.id.new_places_list_name);
                placelistViewModel.insert(input.getText().toString());
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
        placelistAdapter.setPlacesLists(placelists);
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
