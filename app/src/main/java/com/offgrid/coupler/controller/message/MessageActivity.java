package com.offgrid.coupler.controller.message;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.model.view.ChatViewModel;
import com.offgrid.coupler.model.view.MessageListViewModel;

import java.util.List;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static com.offgrid.coupler.data.entity.Chat.personalChat;
import static com.offgrid.coupler.data.model.ChatType.PERSONAL;


public class MessageActivity
        extends AppCompatActivity
        implements Observer<List<Message>>, View.OnClickListener {

    private MessageListViewModel messageListViewModel;
    private ChatViewModel chatViewModel;

    private MessageListAdapter messageListAdapter;
    private ChatDto chatDto;
    private EditText editText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatDto = ChatDto.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_message);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chatDto.getTitle());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        messageListAdapter = new MessageListAdapter(this, chatDto.getType());

        RecyclerView recyclerView = findViewById(R.id.recyclerview_message_list);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageListViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);
        messageListViewModel.observe(this, this);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.observe(this, new Observer<Chat>() {
            @Override
            public void onChanged(Chat chat) {
                if (chat != null) {
                    messageListViewModel.loadChatMessages(chat.getId());
                } else if (PERSONAL.equals(chatDto.getType())) {
                    chatViewModel.insert(personalChat(chatDto.getTitle(), chatDto.getReference()));
                }
            }
        });

        if (chatDto.getId() == null) {
            chatViewModel.loadByUserId(chatDto.getReference());
        } else {
            chatViewModel.loadByChatId(chatDto.getId());
        }

        editText = findViewById(R.id.edit_text);

        ImageButton imageButton = findViewById(R.id.send_message);
        imageButton.setOnClickListener(MessageActivity.this);
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
                Message message = Message.talkerMessage();
                messageListViewModel.insert(message);
                chatViewModel.updateLastMessage(message.getMessage());
                return true;
            case R.id.action_clear_message_history:
                messageListViewModel.delete();
                chatViewModel.updateLastMessage("");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(@Nullable final List<Message> messages) {
        messageListAdapter.setMessages(messages);
    }


    private void sendMessage() {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            messageListViewModel.insert(Message.myMessage(message));
            chatViewModel.updateLastMessage(message);
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
