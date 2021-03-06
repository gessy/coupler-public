package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.util.DateUtil;

public class ChatItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView message;
    private final TextView messageDate;
    private final ImageView messageStatus;

    public ChatItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.chat_list_title);
        message = itemView.findViewById(R.id.chat_list_message);
        messageDate = itemView.findViewById(R.id.last_message_date);
        messageStatus = itemView.findViewById(R.id.message_status);
    }

    public void update(Chat chat) {
        title.setText(chat.getTitle());
        message.setText(chat.getLastMessage());
        messageDate.setText(DateUtil.formatMessageDate(chat.getLastModificationDate()));
        messageStatus.setVisibility(chat.isMineLastMessage() ? View.VISIBLE : View.INVISIBLE);
    }

}
