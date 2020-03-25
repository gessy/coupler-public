package com.offgrid.coupler.controller.group;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.dto.GroupDto;


public class AddGroupMembershipActivity extends AppCompatActivity implements View.OnClickListener {

    private GroupDto groupDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupDto = GroupDto.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_add_group_membership);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Members");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.fab_add_membership).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add_membership) {
            Toast.makeText(this,"new", Toast.LENGTH_SHORT).show();
        }
    }
}
