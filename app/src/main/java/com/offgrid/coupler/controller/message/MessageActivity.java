package com.offgrid.coupler.controller.message;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.adapter.MessageListAdapter;
import com.offgrid.coupler.data.entity.ChatMessages;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.model.view.ChatViewModel;


import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static com.offgrid.coupler.data.entity.Chat.personalChat;


public class MessageActivity
        extends AppCompatActivity
        implements Observer<ChatMessages>, View.OnClickListener {

    private ChatViewModel chatViewModel;

    private MessageListAdapter messageListAdapter;
    private ChatDto chatDto;
    private EditText editText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatDto = ChatDto.getInstance(getIntent().getExtras());
        initViewModels();

        setContentView(R.layout.activity_message);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chatDto.getTitle());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatViewModel.isPersonal() && chatViewModel.noMessages()) {
                    chatViewModel.cleanUpAndDelete();
                }
                onBackPressed();
            }
        });

        messageListAdapter = new MessageListAdapter(this, chatDto.getType());

        RecyclerView recyclerView = findViewById(R.id.recyclerview_message_list);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editText = findViewById(R.id.edit_text);

        ImageButton imageButton = findViewById(R.id.send_message);
        imageButton.setOnClickListener(MessageActivity.this);
    }


    public void initViewModels() {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.observe(this, this);

        if (chatDto.getId() == null) {
            chatViewModel.loadByUserId(chatDto.getReference());
        } else {
            chatViewModel.loadByChatId(chatDto.getId());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_talker_message:
                chatViewModel.addMessage(Message.talkerMessage());
                return true;
            case R.id.action_clear_message_history:
                chatViewModel.deleteMessages();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(ChatMessages chatMessages) {
        if (chatMessages == null) {
            chatViewModel.insert(personalChat(chatDto.getTitle(), chatDto.getReference()));
        } else {
            messageListAdapter.setMessages(chatMessages.messages);
        }
    }


    private void sendMessage() {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            chatViewModel.addMessage(Message.myMessage(message));
            editText.getText().clear();
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_message) {
            sendMessage();
        }
    }

}
