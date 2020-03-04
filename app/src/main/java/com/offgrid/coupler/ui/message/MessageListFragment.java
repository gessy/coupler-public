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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.model.dto.ChatDto;

import java.util.ArrayList;
import java.util.Arrays;

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
        Message message = new Message(1L, "Message in the chat: " + chatDto.getTitle());
        adapter.setMessages(new ArrayList<>(Arrays.asList(message)));

//        messageListViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);
//        messageListViewModel.getAllChats().observe(getActivity(), new Observer<List<Chat>>() {
//            @Override
//            public void onChanged(@Nullable final List<Chat> chats) {
//                adapter.setWords(chats);
//            }
//        });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_message_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

}