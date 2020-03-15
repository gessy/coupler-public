package com.offgrid.coupler.controller.contact;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.model.dto.UserDto;
import com.offgrid.coupler.model.view.ChatViewModel;
import com.offgrid.coupler.model.view.ContactViewModel;
import com.offgrid.coupler.controller.message.MessageActivity;
import com.offgrid.coupler.model.view.MessageListViewModel;


public class ContactInfoActivity extends AppCompatActivity
        implements Observer<Object>, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ContactViewModel contactViewModel;
    private ChatViewModel chatViewModel;
    private MessageListViewModel messageListViewModel;

    private Switch switcher;

    private UserDto userDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        userDto = UserDto.getInstance(getIntent().getExtras());
        initViewModels();

        switcher = findViewById(R.id.notification_status_switcher);
        switcher.setOnCheckedChangeListener(this);

        FloatingActionButton fab = findViewById(R.id.fb_start_user_chat);
        fab.setOnClickListener(this);
    }

    private void initViewModels() {
        messageListViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.loadByUserId(userDto.getId());
        chatViewModel.observe(this, this);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.loadByGid(userDto.getGid());
        contactViewModel.observe(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_contact) {
            messageListViewModel.delete();
            chatViewModel.delete();
            contactViewModel.delete();
            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_edit_contact) {
            Intent intent = new Intent(this, EditContactActivity.class);
            User user = contactViewModel.get();
            intent.putExtras(
                    new UserDto
                            .BundleBuilder()
                            .withId(user.getId())
                            .withFirstName(user.getFirstName())
                            .withLastName(user.getLastName())
                            .withGid(user.getGid())
                            .build()
            );
            startActivityForResult(intent, 1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChanged(Object o) {
        if (o instanceof User) {
            User user = (User)o;
            ((TextView) findViewById(R.id.user_gid)).setText(user.getGid());
            ((TextView) findViewById(R.id.notification_status)).setText(user.isAllowNotify() ? getString(R.string.notification_status_on) : getString(R.string.notification_status_off));
            ((TextView) findViewById(R.id.user_full_name)).setText(user.getFirstName() + " " + user.getLastName());

            if (switcher.isChecked() != user.isAllowNotify()) {
                switcher.setChecked(user.isAllowNotify());
            }
        } else if (o instanceof Chat) {
            Chat chat = (Chat)o;
            messageListViewModel.loadChatMessages(chat.getId());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        User user = contactViewModel.get();
        user.setAllowNotify(b);
        contactViewModel.update(user);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fb_start_user_chat) {
            User user = contactViewModel.get();
            if (user != null) {
                Intent intent = new Intent(ContactInfoActivity.this, MessageActivity.class);
                intent.putExtras(
                        new ChatDto
                                .BundleBuilder()
                                .withReference(user.getId())
                                .withTitle(user.getFirstName() + " " + user.getLastName())
                                .withType(ChatType.PERSONAL)
                                .build()
                );
                startActivityForResult(intent, 1);
            }
        }
    }
}
