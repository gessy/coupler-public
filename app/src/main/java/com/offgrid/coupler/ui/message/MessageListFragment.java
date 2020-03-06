package com.offgrid.coupler.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.util.RandomTokenGenerator;

import java.util.List;

public class MessageListFragment extends Fragment implements Observer<List<Message>> {

    private MessageListViewModel messageListViewModel;
    private MessageListAdapter messageListAdapter;
    private ChatDto chatDto;


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
        messageListViewModel.loadChatMessages(chatDto.getId());
        messageListViewModel.observe(getActivity(), MessageListFragment.this);

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_message_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int randId = RandomTokenGenerator.getInt();

        switch (item.getItemId()) {
            case R.id.action_add_my_message:
                messageListViewModel.insertMessage(new Message(
                        chatDto.getId(),
                        "Message ID " + randId,
                        System.currentTimeMillis(),
                        "Me",
                        true));
                return true;
            case R.id.action_add_talker_message:
                messageListViewModel.insertMessage(new Message(
                        chatDto.getId(),
                        "Message ID " + randId,
                        System.currentTimeMillis(),
                        "Talker",
                        false));
                return true;
            case R.id.action_clear_message_history:
                messageListViewModel.deleteChatMessages(chatDto.getId());
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(@Nullable final List<Message> messages) {
        messageListAdapter.setMessages(messages);
    }
}
