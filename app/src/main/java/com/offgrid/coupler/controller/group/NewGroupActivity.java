package com.offgrid.coupler.controller.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.model.Info;
import com.offgrid.coupler.model.view.GroupViewModel;


public class NewGroupActivity extends AppCompatActivity {

    GroupViewModel groupViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_new_group);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(info.getTitle());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_done) {
            groupViewModel.insert(new Group("ZZZZZZ"));
            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
