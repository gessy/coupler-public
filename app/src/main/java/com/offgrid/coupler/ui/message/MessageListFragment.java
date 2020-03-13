package com.offgrid.coupler.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.ui.chat.ChatViewModel;

import java.util.List;


public class MessageListFragment extends Fragment implements Observer<List<Message>>, View.OnClickListener {

    private MessageListViewModel messageListViewModel;
    private ChatViewModel chatViewModel;


    private MessageListAdapter messageListAdapter;
    private NestedScrollView nestedScrollView;
    private ChatDto chatDto;
    private EditText editText;

    MessageListFragment(ChatDto chatDto) {
        super();
        this.chatDto = chatDto;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message_list, container, false);

        messageListAdapter = new MessageListAdapter(getActivity(), chatDto.getType());

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_message_list);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        messageListViewModel = new ViewModelProvider(MessageListFragment.this).get(MessageListViewModel.class);
        messageListViewModel.observe(getActivity(), MessageListFragment.this);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        if (chatDto.getId() == null) {
            chatViewModel.observe(getActivity(), new Observer<Chat>() {
                @Override
                public void onChanged(Chat chat) {
                    if (chat != null) {
                        messageListViewModel.loadChatMessages(chat.getId());
                    } else if (ChatType.PERSONAL.equals(chatDto.getType())) {
                        chatViewModel.insert(Chat.personalChat(chatDto.getTitle(), chatDto.getReference()));
                    }
                }
            });

            chatViewModel.loadByUserId(chatDto.getReference());
        } else {
            messageListViewModel.loadChatMessages(chatDto.getId());
        }

        editText = getActivity().findViewById(R.id.edit_text);

        ImageButton imageButton = getActivity().findViewById(R.id.send_message);
        imageButton.setOnClickListener(MessageListFragment.this);

        nestedScrollView = root.findViewById(R.id.nested_scroll_view);

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_message_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_talker_message:
                messageListViewModel.insert(Message.talkerMessage());
                return true;
            case R.id.action_clear_message_history:
                messageListViewModel.delete();
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
            editText.getText().clear();
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_message) {
            sendMessage();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageListViewModel.getMessages().isEmpty()) {
            chatViewModel.delete();
        }
    }
}
