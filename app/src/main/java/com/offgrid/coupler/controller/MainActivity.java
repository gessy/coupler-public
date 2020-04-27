package com.offgrid.coupler.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.offgrid.coupler.MockActivity;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.group.NewGroupActivity;
import com.offgrid.coupler.controller.offline.OfflineActivity;
import com.offgrid.coupler.controller.place.PlacelistActivity;
import com.offgrid.coupler.core.model.Info;
import com.offgrid.coupler.controller.chat.ChatListFragment;
import com.offgrid.coupler.controller.contact.ContactListActivity;
import com.offgrid.coupler.controller.map.MapFragment;
import com.offgrid.coupler.util.EntityHelper;

import java.util.List;


public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PermissionsListener {

    private FragmentController fragmentController;
    private PermissionsManager permissionsManager;

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resources = getResources();

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


        if (!PermissionsManager.areLocationPermissionsGranted(MainActivity.this)) {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(MainActivity.this);
        }
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionResult(boolean granted) {
        //TODO
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
        switch (item.getItemId()) {
            case R.id.nav_contact_list:
                return jumpToActivity(
                        new Info.BundleBuilder()
                                .withTitle(resources.getString(R.string.menu_contact_list)).build(),
                        ContactListActivity.class);
            case R.id.nav_group:
                return jumpToActivity(
                        new Info.BundleBuilder()
                                .withTitle(resources.getString(R.string.menu_group)).build(),
                        NewGroupActivity.class);
            case R.id.nav_places_lists:
                return jumpToActivity(
                        new Info.BundleBuilder().withTitle(resources.getString(R.string.menu_my_places)).build(),
                        PlacelistActivity.class);
            case R.id.nav_offline_maps:
                return jumpToActivity(
                        new Info.BundleBuilder()
                                .withTitle("Offline maps")
                                .withText("This is offline maps activity")
                                .withAction(Info.Action.info)
                                .build(),
                        OfflineActivity.class);
        }

        return jumpToActivity(EntityHelper.createBundle(item.getItemId()), MockActivity.class);
    }

    private boolean jumpToActivity(Bundle extras, Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);

        return true;
    }
}
