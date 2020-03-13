package com.offgrid.coupler.ui.chat;

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
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.model.view.ChatListViewModel;

import java.util.List;

public class ChatListFragment extends Fragment implements Observer<List<Chat>> {

    private ChatListViewModel chatListViewModel;
    private ChatListAdapter chatListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat_list, container, false);

        chatListAdapter = new ChatListAdapter(getActivity());

        chatListViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        chatListViewModel.loadChats();
        chatListViewModel.observe(getActivity(), ChatListFragment.this);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_chat_list);
        recyclerView.setAdapter(chatListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_group_chat) {
            chatListViewModel.insertChat(Chat.randGroupChat());
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(@Nullable final List<Chat> chats) {
        chatListAdapter.setChats(chats);
    }
}