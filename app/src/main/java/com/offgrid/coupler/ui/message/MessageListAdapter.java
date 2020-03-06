package com.offgrid.coupler.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.ui.message.model.MessageConst;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListItemViewHolder> {

    private List<Message> messages;

    private Context context;

    MessageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MessageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MessageConst.OUTCOME_MESSAGE:
                return new MessageListItemViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.outcome_message_item, parent, false)
                );
            case MessageConst.INCOME_MESSAGE:
                return new MessageListItemViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.income_message_item, parent, false)
                );
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MessageListItemViewHolder holder, int position) {
        final Message current = messages != null ? messages.get(position) : Message.getEmpty();
        holder.update(current);
    }

    void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isMine()) {
            return MessageConst.OUTCOME_MESSAGE;
        }
        return MessageConst.INCOME_MESSAGE;
    }

}
