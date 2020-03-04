package com.offgrid.coupler.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.Info;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.ui.message.MessageActivity;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    private Context context;

    ChatListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
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
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtras(
                        new ChatDto
                                .BundleBuilder()
                                .withId(current.getId())
                                .withTitle(current.getTitle())
                                .withType(ChatType.valueOf(current.getType()))
                                .build()
                );
                context.startActivity(intent);
            }
        });
    }

    void setChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chats != null ? chats.size() : 0;
    }
}
