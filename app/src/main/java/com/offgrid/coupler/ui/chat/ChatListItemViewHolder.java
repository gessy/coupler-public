package com.offgrid.coupler.ui.chat;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.domain.Chat;

public class ChatListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView message;

    ChatListItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.chat_list_title);
        message = itemView.findViewById(R.id.chat_list_message);
    }

    public void update(Chat chat) {
        this.title.setText(chat.getTitle());
        this.message.setText(chat.getMessage());
    }



}
