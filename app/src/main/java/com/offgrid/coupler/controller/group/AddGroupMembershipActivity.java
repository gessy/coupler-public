package com.offgrid.coupler.controller.group;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.AddGroupMembershipContactListAdapter;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.core.model.view.ContactListViewModel;
import com.offgrid.coupler.data.entity.User;

import java.util.List;


public class AddGroupMembershipActivity extends AppCompatActivity
        implements Observer<List<User>>, View.OnClickListener {

    private GroupDto groupDto;
    private ContactListViewModel contactListViewModel;
    private AddGroupMembershipContactListAdapter contactListAdapter;

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

        contactListViewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        contactListViewModel.load();
        contactListViewModel.observe(this, this);

        contactListAdapter = new AddGroupMembershipContactListAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_contact_list);
        recyclerView.setAdapter(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    @Override
    public void onChanged(List<User> users) {
        contactListAdapter.setUsers(users);
    }


}
