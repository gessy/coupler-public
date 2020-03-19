package com.offgrid.coupler.core.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Chat;

public class ChatListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView message;

    private Context context;

    public ChatListItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        title = itemView.findViewById(R.id.chat_list_title);
        message = itemView.findViewById(R.id.chat_list_message);
    }

    public void update(Chat chat) {
        this.title.setText(chat.getTitle());
        this.message.setText(chat.getLastMessage());
    }

}
