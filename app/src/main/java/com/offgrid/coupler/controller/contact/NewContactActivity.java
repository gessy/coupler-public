package com.offgrid.coupler.controller.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.model.Info;
import com.offgrid.coupler.controller.contact.listener.FirstNameTextWatcher;
import com.offgrid.coupler.controller.contact.listener.GidAutoFormatTextWatcher;
import com.offgrid.coupler.model.view.ContactViewModel;
import com.offgrid.coupler.controller.contact.status.InputFieldsStatusHolder;


public class NewContactActivity extends AppCompatActivity implements Updatable {

    private InputFieldsStatusHolder statusHolder = new InputFieldsStatusHolder();
    private MenuItem menuItemDone;
    private ContactViewModel contactViewModel;

    private EditText gidInput;
    private EditText fnInput;
    private EditText lnInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Info info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_new_contact);
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


        contactViewModel = new ViewModelProvider(NewContactActivity.this).get(ContactViewModel.class);

        gidInput = findViewById(R.id.gid_edit_text);
        gidInput.addTextChangedListener(new GidAutoFormatTextWatcher(gidInput, NewContactActivity.this));

        fnInput = findViewById(R.id.user_first_name);
        fnInput.addTextChangedListener(new FirstNameTextWatcher(fnInput, NewContactActivity.this));

        lnInput = findViewById(R.id.user_last_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
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
            contactViewModel.insert(
                    new User(
                            fnInput.getText().toString(),
                            lnInput.getText().toString(),
                            gidInput.getText().toString()
                    )
            );

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
