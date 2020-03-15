package com.offgrid.coupler.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.chat.ChatListFragment;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.holder.ChatListItemViewHolder;
import com.offgrid.coupler.controller.message.MessageActivity;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    private ChatListFragment fragment;

    public ChatListAdapter(ChatListFragment fragment) {
        this.fragment = fragment;
        mInflater = LayoutInflater.from(fragment.getActivity());

    }

    @Override
    public ChatListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.chat_list_item, parent, false);
        return new ChatListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatListItemViewHolder holder, int position) {
        final Chat current = chats != null ? chats.get(position) : Chat.getEmpty();
        holder.update(current);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment.getActivity(), MessageActivity.class);
                intent.putExtras(
                        new ChatDto
                                .BundleBuilder()
                                .withId(current.getId())
                                .withTitle(current.getTitle())
                                .withType(ChatType.valueOf(current.getType()))
                                .build()
                );
                fragment.startActivityForResult(intent, 1);
            }
        });
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    public void dataSetChanged() {
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return chats != null ? chats.size() : 0;
    }
}
