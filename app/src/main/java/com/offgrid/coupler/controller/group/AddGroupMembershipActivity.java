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
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.MembershipContactListAdapter;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.core.model.view.ContactListViewModel;
import com.offgrid.coupler.core.provider.ContactSelectionTracker;
import com.offgrid.coupler.data.entity.User;

import java.util.List;


public class AddGroupMembershipActivity extends AppCompatActivity
        implements Observer<List<User>>, View.OnClickListener {

    private GroupDto groupDto;
    private ContactListViewModel contactListViewModel;
    private MembershipContactListAdapter contactListAdapter;

    private SelectionTracker selectionTracker;
    private RecyclerView recyclerView;

    private FloatingActionButton fb;


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

        fb = findViewById(R.id.fab_add_membership);
        fb.setOnClickListener(this);

        contactListViewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        contactListViewModel.load();
        contactListViewModel.observe(this, this);

        contactListAdapter = new MembershipContactListAdapter(this);

        recyclerView = findViewById(R.id.recyclerview_contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactListAdapter);

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
            Toast.makeText(this, "new", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChanged(List<User> users) {
        contactListAdapter.setUsers(users);

        selectionTracker = new ContactSelectionTracker().get(recyclerView, users);
        contactListAdapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if (selectionTracker.hasSelection()) {
                    fb.show();
                } else {
                    fb.hide();
                }
            }
        });
    }

}
