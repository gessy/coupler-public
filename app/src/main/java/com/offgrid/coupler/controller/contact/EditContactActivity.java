package com.offgrid.coupler.controller.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.core.model.dto.UserDto;
import com.offgrid.coupler.core.model.view.ContactViewModel;


public class EditContactActivity extends AppCompatActivity implements Observer<Object> {

    private ContactViewModel contactViewModel;

    private UserDto userDto;

    private EditText fnInput;
    private EditText lnInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDto = UserDto.getInstance(getIntent().getExtras());
        initViewModels();

        setContentView(R.layout.activity_edit_contact);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Contact");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fnInput = findViewById(R.id.user_first_name);
        lnInput = findViewById(R.id.user_last_name);
    }


    private void initViewModels() {
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.loadByGid(userDto.getGid());
        contactViewModel.observe(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mock, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_done) {
            contactViewModel.updateFullName(fnInput.getText().toString(), lnInput.getText().toString());
            setResult(RESULT_OK, new Intent());
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(Object o) {
        if (o instanceof UserChat) {
            User user = ((UserChat)o).user;
            ((TextView) findViewById(R.id.user_gid)).setText(user.getGid());
            fnInput.setText(user.getFirstName());
            lnInput.setText(user.getLastName());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }
}
