package com.offgrid.coupler.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.maps.Style;
import com.offgrid.coupler.MockActivity;
import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.group.NewGroupActivity;
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
        } else if (item.getItemId() == R.id.nav_group) {
            Intent intent = new Intent(MainActivity.this, NewGroupActivity.class);
            intent.putExtras(
                    new Info.BundleBuilder()
                            .withTitle("Add Group")
                            .withText("This is new group activity")
                            .withAction(Info.Action.add_group)
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

        overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
        return true;
    }
}
