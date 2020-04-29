package com.offgrid.coupler.controller.offline;

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
import com.offgrid.coupler.core.adapter.RegionAdapter;
import com.offgrid.coupler.core.callback.SwipeToDeleteCallback;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.core.model.view.RegionCountryViewModel;
import com.offgrid.coupler.data.entity.RegionCountry;

import java.util.List;


public class OfflineRegionListActivity extends AppCompatActivity implements Observer<List<RegionCountry>> {

    private RegionAdapter regionAdapter;
    private RegionCountryViewModel regionCountryViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_offline_region);
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

        regionAdapter = new RegionAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_loaded_regions);
        recyclerView.setAdapter(regionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper touchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                regionCountryViewModel.dropFromLoad(regionAdapter.getRegion(viewHolder.getAdapterPosition()));
            }
        });
        touchHelper.attachToRecyclerView(recyclerView);

        regionCountryViewModel = new ViewModelProvider(this).get(RegionCountryViewModel.class);
        regionCountryViewModel.observe(this, this);
        regionCountryViewModel.load();
    }

    @Override
    public void onChanged(List<RegionCountry> regionCountries) {
        regionAdapter.setRegionCountryList(regionCountries);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchRegionActivity.class);
            intent.putExtras(new Info.BundleBuilder()
                    .withTitle(getString(R.string.search_region))
                    .build());
            startActivityForResult(intent, 1);

            overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }
}
