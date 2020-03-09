package com.offgrid.coupler.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.offgrid.coupler.R;
import com.offgrid.coupler.model.Info;
import com.offgrid.coupler.ui.contact.listener.FirstNameTextWatcher;
import com.offgrid.coupler.ui.contact.listener.GidAutoFormatTextWatcher;
import com.offgrid.coupler.ui.contact.status.InputFieldsStatusHolder;


public class NewContactActivity extends AppCompatActivity implements Updatable {

    private InputFieldsStatusHolder statusHolder = new InputFieldsStatusHolder();
    private MenuItem menuItemDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_contact);
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

        EditText gidInput = findViewById(R.id.gid_edit_text);
        gidInput.addTextChangedListener(new GidAutoFormatTextWatcher(gidInput, NewContactActivity.this));

        EditText firstNameInput = findViewById(R.id.user_first_name);
        firstNameInput.addTextChangedListener(new FirstNameTextWatcher(firstNameInput, NewContactActivity.this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuItemDone = menu.findItem(R.id.nav_done);
        activateMenuItem(statusHolder.makeActive());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_done) {
            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateState(int state) {
        statusHolder.updateState(state);
        activateMenuItem(statusHolder.makeActive());
    }


    private void activateMenuItem(boolean active) {
        if (active) {
            menuItemDone.setEnabled(true);
            menuItemDone.getIcon().setAlpha(255);
        } else {
            menuItemDone.setEnabled(false);
            menuItemDone.getIcon().setAlpha(130);
        }
    }

}
