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

public class MessageListFragment extends Fragment {

    private MessageListViewModel messageListViewModel;

    private ChatDto chatDto;


    public MessageListFragment(ChatDto chatDto) {
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

        final MessageListAdapter adapter = new MessageListAdapter(getActivity());

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_message_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        messageListViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);
        messageListViewModel.getChatMessages(chatDto.getId())
                .observe(getActivity(), new Observer<List<Message>>() {
                    @Override
                    public void onChanged(@Nullable final List<Message> messages) {
                        adapter.setMessages(messages);
                    }
                });

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
        if (item.getItemId() == R.id.action_add_my_message) {
            messageListViewModel.insert(new Message(
                    chatDto.getId(),
                    "Message ID " + randId,
                    System.currentTimeMillis(),
                    "Me"));
            return true;
        } else if (item.getItemId() == R.id.action_add_talker_message) {
            messageListViewModel.insert(new Message(
                    chatDto.getId(),
                    "Message ID " + randId,
                    System.currentTimeMillis(),
                    "Talker"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
