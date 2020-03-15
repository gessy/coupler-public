package com.offgrid.coupler.controller.chat;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.offgrid.coupler.R;
import com.offgrid.coupler.adapter.ChatListAdapter;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.model.Info;
import com.offgrid.coupler.model.view.ChatListViewModel;
import com.offgrid.coupler.controller.message.NewMessageActivity;

import java.util.List;

public class ChatListFragment extends Fragment implements Observer<List<Chat>>, View.OnClickListener {

    private ChatListViewModel chatListViewModel;
    private ChatListAdapter chatListAdapter;

    private FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat_list, container, false);

        chatListAdapter = new ChatListAdapter(this);

        chatListViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        chatListViewModel.loadChats();
        chatListViewModel.observe(getActivity(), ChatListFragment.this);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_chat_list);
        recyclerView.setAdapter(chatListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab = getActivity().findViewById(R.id.fab_new_message);
        fab.setOnClickListener(ChatListFragment.this);

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChanged(@Nullable final List<Chat> chats) {
        chatListAdapter.setChats(chats);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_new_message) {
            Intent intent = new Intent(getActivity(), NewMessageActivity.class);
            intent.putExtras(
                    new Info.BundleBuilder()
                            .withTitle("New Message")
                            .withText("This is new message activity")
                            .withAction(Info.Action.new_message)
                            .build()
            );
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            fab.hide();
        } else {
            fab.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}