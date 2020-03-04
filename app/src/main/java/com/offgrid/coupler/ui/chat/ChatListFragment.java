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
import com.offgrid.coupler.util.RandomTokenGenerator;

import java.util.List;

public class ChatListFragment extends Fragment {

    private ChatListViewModel chatListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat_list, container, false);

        final ChatListAdapter adapter = new ChatListAdapter(getActivity());

        chatListViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        chatListViewModel.getAllChats().observe(getActivity(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(@Nullable final List<Chat> chats) {
                adapter.setWords(chats);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_chat_list);
        recyclerView.setAdapter(adapter);
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
        if (item.getItemId() == R.id.action_add_chat) {
            int id = RandomTokenGenerator.getInt();
            String title = "Chat ID " + id;
            String message = "Last message on Chat ID " + id;
            chatListViewModel.insert(new Chat(title, message));
        }
        return super.onOptionsItemSelected(item);
    }


}