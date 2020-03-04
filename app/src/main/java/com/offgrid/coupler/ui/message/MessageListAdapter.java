package com.offgrid.coupler.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Message> messages;

    private Context context;

    MessageListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MessageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.message_list_item, parent, false);
        return new MessageListItemViewHolder(itemView);
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
}
