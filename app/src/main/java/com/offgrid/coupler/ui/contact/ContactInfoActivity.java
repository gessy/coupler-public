package com.offgrid.coupler.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.model.dto.UserDto;


public class ContactInfoActivity extends AppCompatActivity implements Observer<User> {

    private ContactInfoViewModel contactInfoViewModel;
    private UserDto userDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDto = UserDto.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_contact_info);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        contactInfoViewModel = new ViewModelProvider(this).get(ContactInfoViewModel.class);
        contactInfoViewModel.loadUser(userDto.getGid());
        contactInfoViewModel.observe(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_contact) {
            contactInfoViewModel.delete();
            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(User user) {
        if (user != null) {
            ((TextView) findViewById(R.id.user_gid)).setText(user.getGid());
            ((TextView) findViewById(R.id.notification_status)).setText(user.isAllowNotify() ? getString(R.string.notification_status_on) : getString(R.string.notification_status_off));
            ((TextView) findViewById(R.id.user_full_name)).setText(user.getFirstName() + " " + user.getLastName());
        }
    }
}
