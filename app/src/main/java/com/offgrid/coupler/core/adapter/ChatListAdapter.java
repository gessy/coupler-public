package com.offgrid.coupler.core.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.chat.ChatListFragment;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.core.holder.ChatItemViewHolder;
import com.offgrid.coupler.controller.chat.ChatActivity;
import com.offgrid.coupler.core.model.dto.wrapper.DtoChatWrapper;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    private ChatListFragment fragment;

    public ChatListAdapter(ChatListFragment fragment) {
        this.fragment = fragment;
        mInflater = LayoutInflater.from(fragment.getActivity());

    }

    @Override
    public ChatItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.chat_list_item, parent, false);
        return new ChatItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatItemViewHolder holder, int position) {
        final Chat current = chats != null ? chats.get(position) : Chat.getEmpty();
        holder.update(current);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment.getActivity(), ChatActivity.class);
                intent.putExtras(DtoChatWrapper.convertAndWrap(current));
                fragment.startActivityForResult(intent, 1);
                fragment.getActivity().overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
            }
        });
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chats != null ? chats.size() : 0;
    }
}
