package com.offgrid.coupler.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.domain.Chat;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    ChatListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ChatListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.chat_list_item, parent, false);
        return new ChatListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatListItemViewHolder holder, int position) {
        if (chats != null) {
            Chat current = chats.get(position);
            holder.update(current);
        } else {
            // Covers the case of data not being ready yet.
            holder.update(Chat.getEmpty());
        }
    }

    void setWords(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chats != null)
            return chats.size();
        else return 0;
    }
}
