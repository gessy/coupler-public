package com.offgrid.coupler.controller.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.model.dto.UserDto;
import com.offgrid.coupler.model.view.ChatViewModel;
import com.offgrid.coupler.model.view.ContactViewModel;


public class EditContactActivity extends AppCompatActivity implements Observer<Object> {

    private ContactViewModel contactViewModel;
    private ChatViewModel chatViewModel;

    private UserDto userDto;

    private EditText fnInput;
    private EditText lnInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_contact);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Contact");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        fnInput = findViewById(R.id.user_first_name);
        lnInput = findViewById(R.id.user_last_name);

        userDto = UserDto.getInstance(getIntent().getExtras());

        initViewModels();
    }


    private void initViewModels() {
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.loadByGid(userDto.getGid());
        contactViewModel.observe(this, this);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.loadByUserId(userDto.getId());
        chatViewModel.observe(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mock, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_done) {
            User user = contactViewModel.get();
            user.setFirstName(fnInput.getText().toString());
            user.setLastName(lnInput.getText().toString());
            contactViewModel.update(user);
            chatViewModel.updateTitle(user.getFirstName() + " " + user.getLastName());

            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(Object o) {
        if (o instanceof User) {
            User user = (User)o;
            ((TextView) findViewById(R.id.user_gid)).setText(user.getGid());
            fnInput.setText(user.getFirstName());
            lnInput.setText(user.getLastName());
        }
    }

}
