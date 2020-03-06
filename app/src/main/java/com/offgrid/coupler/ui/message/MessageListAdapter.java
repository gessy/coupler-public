package com.offgrid.coupler.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.model.ChatType;

import java.util.List;

import static com.offgrid.coupler.data.model.ChatType.GROUP;
import static com.offgrid.coupler.ui.message.model.MessageConst.INCOME_GROUP_MESSAGE;
import static com.offgrid.coupler.ui.message.model.MessageConst.OUTCOME_GROUP_MESSAGE;
import static com.offgrid.coupler.ui.message.model.MessageConst.INCOME_PERSONAL_MESSAGE;
import static com.offgrid.coupler.ui.message.model.MessageConst.OUTCOME_PERSONAL_MESSAGE;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListItemViewHolder> {

    private List<Message> messages;
    private ChatType chatType;

    private Context context;

    MessageListAdapter(Context context, ChatType chatType) {
        this.context = context;
        this.chatType = chatType;
    }

    @Override
    public MessageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageListItemViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(layoutResId(viewType), parent, false));
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
            return GROUP.equals(chatType) ? OUTCOME_GROUP_MESSAGE : OUTCOME_PERSONAL_MESSAGE;
        }
        return GROUP.equals(chatType) ? INCOME_GROUP_MESSAGE : INCOME_PERSONAL_MESSAGE;
    }


    private int layoutResId(int viewType) {
        switch (viewType) {
            case OUTCOME_GROUP_MESSAGE:
            case OUTCOME_PERSONAL_MESSAGE:
                return R.layout.message_outcome_item;
            case INCOME_GROUP_MESSAGE:
                return R.layout.message_group_income_item;
            case INCOME_PERSONAL_MESSAGE:
                return R.layout.message_personal_income_item;
            default:
                return 0;
        }

    }

}
