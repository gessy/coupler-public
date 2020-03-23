package com.offgrid.coupler.controller.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.core.model.view.GroupViewModel;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;


public class EditGroupActivity extends AppCompatActivity implements Observer<Object> {

    private GroupViewModel groupViewModel;
    private EditText gnInput;
    private EditText gdInput;

    private GroupDto groupDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupDto = GroupDto.getInstance(getIntent().getExtras());
        initViewModels();

        setContentView(R.layout.activity_new_group);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Group");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        gnInput = findViewById(R.id.group_name);
        gdInput = findViewById(R.id.group_description);
    }


    private void initViewModels() {
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.loadByGroupId(groupDto.getId());
        groupViewModel.observe(this, this);
    }


    @Override
    public void onChanged(Object o) {
        if (o instanceof GroupChat) {
            Group group = ((GroupChat)o).group;
            gnInput.setText(group.getName());
            gdInput.setText(group.getDescription());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_done) {
            String name = gnInput.getText().toString();
            String description = gdInput.getText().toString();
            if (name.length() >= 3) {
                groupViewModel.updateGroup(name, description);
                setResult(RESULT_OK, new Intent());
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }
}
