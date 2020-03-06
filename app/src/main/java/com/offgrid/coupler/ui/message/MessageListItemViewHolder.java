package com.offgrid.coupler.ui.message;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;

class MessageListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView message;
    private Context context;

    MessageListItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        message = itemView.findViewById(R.id.message_body);
    }

    void update(Message message) {
        String text = context.getString(R.string.message_list_item, message.getUserFullName(), message.getMessage());
        this.message.setText(text);
    }

}
