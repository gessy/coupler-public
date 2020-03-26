package com.offgrid.coupler.controller.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.chat.ChatActivity;
import com.offgrid.coupler.core.adapter.GroupMembershipListAdapter;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.core.model.dto.wrapper.DtoChatWrapper;
import com.offgrid.coupler.core.model.dto.wrapper.DtoGroupWrapper;
import com.offgrid.coupler.core.model.view.GroupUsersViewModel;
import com.offgrid.coupler.core.model.view.GroupViewModel;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;
import com.offgrid.coupler.data.entity.GroupUsers;


public class GroupInfoActivity extends AppCompatActivity
        implements Observer<Object>, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private GroupViewModel groupViewModel;
    private GroupUsersViewModel groupUsersViewModel;

    private GroupMembershipListAdapter contactListAdapter;

    private Switch switcher;

    private GroupDto groupDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_info);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        groupDto = GroupDto.getInstance(getIntent().getExtras());
        initViewModels();

        contactListAdapter = new GroupMembershipListAdapter(this);
        contactListAdapter.setDto(groupDto);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_contact_list);
        recyclerView.setAdapter(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switcher = findViewById(R.id.notification_status_switcher);
        switcher.setOnCheckedChangeListener(this);

        findViewById(R.id.fb_start_chat).setOnClickListener(this);
        findViewById(R.id.action_add_member).setOnClickListener(this);
    }

    private void initViewModels() {
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.loadByGroupId(groupDto.getId());
        groupViewModel.observe(this, this);

        groupUsersViewModel = new ViewModelProvider(this).get(GroupUsersViewModel.class);
        groupUsersViewModel.loadByOwnerId(groupDto.getId());
        groupUsersViewModel.observe(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_leave_group) {
            groupViewModel.delete();
            setResult(RESULT_OK, new Intent());
            finish();
            overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
            return true;
        } else if (item.getItemId() == R.id.action_edit_group) {
            Intent intent = new Intent(this, EditGroupActivity.class);
            Group group = groupViewModel.getGroup();
            intent.putExtras(DtoGroupWrapper.convertAndWrap(group));
            startActivityForResult(intent, 1);
            overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChanged(Object o) {
        if (o instanceof GroupChat) {
            Group group = ((GroupChat)o).group;
            ((TextView) findViewById(R.id.group_name)).setText(group.getName());
            ((TextView) findViewById(R.id.notification_status)).setText(group.isAllowNotify() ? getString(R.string.notification_status_on) : getString(R.string.notification_status_off));
            ((TextView) findViewById(R.id.group_description)).setText(group.getDescription());

            if (switcher.isChecked() != group.isAllowNotify()) {
                switcher.setChecked(group.isAllowNotify());
            }
        } else if (o instanceof GroupUsers) {
            contactListAdapter.setUsers(((GroupUsers)o).users);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        groupViewModel.allowNotification(b);
    }

    @Override
    public void onClick(View view) {
        Group group = groupViewModel.getGroup();
        if (view.getId() == R.id.fb_start_chat) {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtras(DtoChatWrapper.convertAndWrap(group));
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
        } else if (view.getId() == R.id.action_add_member) {
            Intent intent = new Intent(this, AddGroupMembershipActivity.class);
            intent.putExtras(DtoGroupWrapper.convertAndWrap(group));
            startActivity(intent);
            overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }

}
