package com.offgrid.coupler.ui.message.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.data.entity.Message;

public abstract class MessageViewHolder extends RecyclerView.ViewHolder {
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void update(Message message);
}
