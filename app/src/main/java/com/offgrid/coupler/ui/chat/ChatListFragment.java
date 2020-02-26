package com.offgrid.coupler.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.domain.Chat;

import java.util.Arrays;

public class ChatListFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        View root = inflater.inflate(R.layout.fragment_chat_list, container, false);

        WordListAdapter adapter = new WordListAdapter(context);
        adapter.setWords(Arrays.asList(new Chat("First Chat"), new Chat("Second Chat"), new Chat("Third Chat")));

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_chat_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}