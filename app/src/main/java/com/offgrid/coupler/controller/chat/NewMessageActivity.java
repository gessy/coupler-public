package com.offgrid.coupler.controller.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.group.NewGroupActivity;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.core.adapter.NewMessageContactListAdapter;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.core.model.view.ContactListViewModel;
import com.offgrid.coupler.controller.contact.NewContactActivity;

import java.util.List;

public class NewMessageActivity
        extends AppCompatActivity
        implements Observer<List<User>>, View.OnClickListener {

    private NewMessageContactListAdapter contactListAdapter;
    private ContactListViewModel contactListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_new_message);
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

        findViewById(R.id.action_new_contact).setOnClickListener(this);
        findViewById(R.id.action_new_group).setOnClickListener(this);

        contactListAdapter = new NewMessageContactListAdapter(this);

        contactListViewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        contactListViewModel.load();
        contactListViewModel.observe(this, NewMessageActivity.this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_contact_list);
        recyclerView.setAdapter(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(List<User> users) {
        contactListAdapter.setUsers(users);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.action_new_contact) {
            Intent intent = new Intent(NewMessageActivity.this, NewContactActivity.class);
            intent.putExtras(
                    new Info.BundleBuilder()
                            .withTitle("Add Contact")
                            .withText("This is new contact activity")
                            .withAction(Info.Action.add_contact)
                            .build()
            );
            startActivityForResult(intent, 1);
        } else if (view.getId() == R.id.action_new_group) {
            Intent intent = new Intent(NewMessageActivity.this, NewGroupActivity.class);
            intent.putExtras(
                    new Info.BundleBuilder()
                            .withTitle("Add Group")
                            .withText("This is new group activity")
                            .withAction(Info.Action.add_group)
                            .build()
            );
            startActivityForResult(intent, 1);
            finish();
        }
        overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }
}
