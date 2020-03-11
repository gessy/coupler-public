package com.offgrid.coupler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.offgrid.coupler.MockActivity;
import com.offgrid.coupler.R;
import com.offgrid.coupler.model.Info;
import com.offgrid.coupler.ui.chat.ChatListFragment;
import com.offgrid.coupler.ui.contact.ContactListActivity;
import com.offgrid.coupler.ui.map.MapFragment;
import com.offgrid.coupler.util.EntityHelper;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentController fragmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        fragmentController = createFragmentController();
        fragmentController.displayScreen(R.id.nav_chat_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MockActivity.class);
                intent.putExtras(
                        new Info.BundleBuilder()
                                .withTitle("New Message")
                                .withText("This is new message activity")
                                .withAction(Info.Action.new_message)
                                .build()
                );
                startActivityForResult(intent, 1);
            }
        });

    }


    private FragmentController createFragmentController() {
        FragmentController controller = new FragmentController();
        controller.setFragmentManager(getSupportFragmentManager());
        controller.setLayoutResource(R.id.content_frame);
        controller.registerFragment(R.id.nav_chat_list, new ChatListFragment());
        controller.registerFragment(R.id.nav_map, new MapFragment());
        return controller;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_chat_list || id == R.id.nav_map) {
            fragmentController.displayScreen(id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_contact_list) {
            Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
            intent.putExtras(
                    new Info.BundleBuilder()
                            .withTitle("Contacts")
                            .withText("This is Contact list activity")
                            .withAction(Info.Action.contact_list)
                            .build()
            );
            startActivityForResult(intent, 1);
        } else {
            Intent intent = new Intent(MainActivity.this, MockActivity.class);
            intent.putExtras(EntityHelper.createBundle(item.getItemId()));
            startActivityForResult(intent, 1);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
