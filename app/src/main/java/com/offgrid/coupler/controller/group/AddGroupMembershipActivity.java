package com.offgrid.coupler.controller.group;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.offgrid.coupler.core.adapter.AddGroupMembershipListAdapter;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.core.model.view.ContactListViewModel;
import com.offgrid.coupler.core.model.view.GroupUsersViewModel;
import com.offgrid.coupler.core.provider.ContactSelectionTracker;
import com.offgrid.coupler.data.entity.GroupUsers;
import com.offgrid.coupler.data.entity.User;

import java.util.ArrayList;
import java.util.List;


public class AddGroupMembershipActivity extends AppCompatActivity
        implements Observer<List<User>>, View.OnClickListener {

    private GroupDto groupDto;

    private ContactListViewModel contactListViewModel;
    private GroupUsersViewModel groupUsersViewModel;

    private AddGroupMembershipListAdapter membershipAdapter;

    private SelectionTracker selectionTracker;
    private RecyclerView recyclerView;

    private FloatingActionButton fb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupDto = GroupDto.getInstance(getIntent().getExtras());
        initViews();

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

        membershipAdapter = new AddGroupMembershipListAdapter(this);

        recyclerView = findViewById(R.id.recyclerview_contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(membershipAdapter);

        fb = findViewById(R.id.fab_add_membership);
        fb.setOnClickListener(this);
    }

    private void initViews() {
        contactListViewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        contactListViewModel.load();
        contactListViewModel.observe(this, this);

        groupUsersViewModel = new ViewModelProvider(this).get(GroupUsersViewModel.class);
        groupUsersViewModel.observe(this, new Observer<GroupUsers>() {
            @Override
            public void onChanged(GroupUsers groupUsers) {
                selectionTracker.setItemsSelected(groupUsers.users, true);
            }
        });
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
            List<User> users = new ArrayList<>();
            for (User user : (Iterable<User>) selectionTracker.getSelection()) {
                if (!user.isEmpty()) users.add(user);
            }
            groupUsersViewModel.setUsers(users);
            onBackPressed();
        }
    }

    @Override
    public void onChanged(List<User> users) {
        membershipAdapter.setUsers(users);

        if (selectionTracker == null) {
            selectionTracker = new ContactSelectionTracker().get(recyclerView, users);
            selectionTracker.addObserver(new SelectionObserver());
            selectionTracker.select(User.getEmpty());
            membershipAdapter.setSelectionTracker(selectionTracker);
            groupUsersViewModel.loadByOwnerId(groupDto.getId());
        }
    }


    class SelectionObserver extends SelectionTracker.SelectionObserver {
        @Override
        public void onSelectionChanged() {
            super.onSelectionChanged();
        }

        @Override
        protected void onSelectionCleared() {
            selectionTracker.select(User.getEmpty());
        }
    }


}
