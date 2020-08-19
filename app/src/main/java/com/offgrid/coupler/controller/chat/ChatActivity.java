package com.offgrid.coupler.controller.chat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.contact.ContactInfoActivity;
import com.offgrid.coupler.controller.group.GroupInfoActivity;
import com.offgrid.coupler.core.adapter.MessageListAdapter;
import com.offgrid.coupler.core.model.dto.wrapper.DtoGroupWrapper;
import com.offgrid.coupler.core.model.dto.wrapper.DtoUserWrapper;
import com.offgrid.coupler.core.service.LoraService;
import com.offgrid.coupler.data.entity.ChatMessages;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.core.model.dto.ChatDto;
import com.offgrid.coupler.core.model.view.ChatViewModel;
import com.offgrid.coupler.core.provider.ChatViewModelProvider;
import com.offgrid.coupler.data.entity.User;


import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static com.offgrid.coupler.core.model.Constants.SERVICE_GROUP_CHAT_CLIENT;
import static com.offgrid.coupler.core.model.Constants.SERVICE_MESSAGE;
import static com.offgrid.coupler.core.model.Constants.SERVICE_REGISTER_CLIENT;
import static com.offgrid.coupler.core.model.Constants.SERVICE_UNREGISTER_CLIENT;
import static com.offgrid.coupler.data.model.ChatType.GROUP;


public class ChatActivity
        extends AppCompatActivity
        implements View.OnClickListener, Observer<ChatMessages>, ServiceConnection {

    private static final String LOGTAG = "ChatActivity-sasha";

    private ChatViewModel chatViewModel;

    private MessageListAdapter messageListAdapter;
    private ChatDto chatDto;
    private EditText editText;


    private Messenger mServiceMessenger = null;
    boolean mIsBound;
    private final Messenger mMessenger = new Messenger(new IncomingMessageHandler());
    private ServiceConnection mConnection = this;

    private String thisClient = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatDto = ChatDto.getInstance(getIntent().getExtras());
        initViewModels();

        setContentView(R.layout.activity_chat);
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

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object o = chatViewModel.getOwner();
                Intent intent = null;
                if (o instanceof User) {
                    intent = new Intent(ChatActivity.this, ContactInfoActivity.class);
                    intent.putExtras(DtoUserWrapper.convertAndWrap((User) o));
                } else if (o instanceof Group) {
                    intent = new Intent(ChatActivity.this, GroupInfoActivity.class);
                    intent.putExtras(DtoGroupWrapper.convertAndWrap((Group) o));
                }

                if (intent != null) {
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
                }
            }
        });

        messageListAdapter = new MessageListAdapter(this, chatDto.getType());

        RecyclerView recyclerView = findViewById(R.id.recyclerview_message_list);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editText = findViewById(R.id.message_text);

        ImageButton imageButton = findViewById(R.id.send_message);
        imageButton.setOnClickListener(ChatActivity.this);

    }


    public void initViewModels() {
        chatViewModel = new ChatViewModelProvider(this).get(chatDto.getType());
        chatViewModel.observe(this, this);
        chatViewModel.loadByOwnerId(chatDto.getReference());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (GROUP.equals(chatDto.getType())) {
            getMenuInflater().inflate(R.menu.menu_group_chat, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_user_chat, menu);
        }
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
            case R.id.action_delete_user_chat:
                chatViewModel.setVisibility(false);
                finish();
                overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
                break;
            case R.id.action_delete_leave_group:
                chatViewModel.deleteChat();
                finish();
                overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void sendMessage() {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            chatViewModel.addMessage(Message.myMessage(message));
            editText.getText().clear();
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), HIDE_NOT_ALWAYS);


            if (mIsBound) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("message", message);

                    android.os.Message msg = android.os.Message.obtain(null, SERVICE_MESSAGE, thisClient);
                    msg.setData(bundle);
                    mServiceMessenger.send(msg);
                } catch (RemoteException e) {

                }

            }


        }
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_message) {
            sendMessage();
        }
    }

    @Override
    public void onChanged(ChatMessages chatMessages) {
        if (chatMessages != null) {
            messageListAdapter.setMessages(chatMessages.messages());

            if (!mIsBound) {
                thisClient = chatViewModel.isPersonal()
                        ? ((User) chatViewModel.getOwner()).getGid()
                        : SERVICE_GROUP_CHAT_CLIENT;

                bindLoraService();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (chatViewModel.isPersonal()) {
            if (chatViewModel.noMessages()) {
                chatViewModel.setVisibility(false);
            } else {
                chatViewModel.setVisibility(true);
            }
        }
        finish();
        overridePendingTransition(R.anim.popup_in, R.anim.popup_out);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unbindLoraService();
        } catch (Throwable t) {
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mServiceMessenger = new Messenger(service);
        try {
            android.os.Message msg = android.os.Message.obtain(null, SERVICE_REGISTER_CLIENT, thisClient);
            msg.replyTo = mMessenger;
            mServiceMessenger.send(msg);
        } catch (RemoteException e) {
            // In this case the service has crashed before we could even do anything with it
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mServiceMessenger = null;
    }


    private void bindLoraService() {
        bindService(new Intent(this, LoraService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }


    private void unbindLoraService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with it, then now is the time to unregister.
            if (mServiceMessenger != null) {
                try {
                    android.os.Message msg = android.os.Message.obtain(null, SERVICE_UNREGISTER_CLIENT, thisClient);
                    msg.replyTo = mMessenger;
                    mServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service has crashed.
                }
            }
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }


    private class IncomingMessageHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            Log.e(LOGTAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case SERVICE_MESSAGE:
                    Log.e(LOGTAG, "[SERVICE_MESSAGE] msg.obj: " + msg.obj.toString());
                    String message = msg.getData().getString("message");
                    chatViewModel.addMessage(Message.talkerMessage(message));

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


}
