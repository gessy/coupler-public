package com.offgrid.coupler.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.ui.message.holder.IncomeGroupMessageViewHolder;
import com.offgrid.coupler.ui.message.holder.IncomePersonalMessageViewHolder;
import com.offgrid.coupler.ui.message.holder.MessageViewHolder;
import com.offgrid.coupler.ui.message.holder.OutcomeMessageViewHolder;

import java.util.List;

import static com.offgrid.coupler.data.model.ChatType.GROUP;
import static com.offgrid.coupler.ui.message.model.MessageConst.INCOME_GROUP_MESSAGE;
import static com.offgrid.coupler.ui.message.model.MessageConst.OUTCOME_GROUP_MESSAGE;
import static com.offgrid.coupler.ui.message.model.MessageConst.INCOME_PERSONAL_MESSAGE;
import static com.offgrid.coupler.ui.message.model.MessageConst.OUTCOME_PERSONAL_MESSAGE;

public class MessageListAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<Message> messages;
    private ChatType chatType;

    private Context context;

    MessageListAdapter(Context context, ChatType chatType) {
        this.context = context;
        this.chatType = chatType;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case OUTCOME_GROUP_MESSAGE:
            case OUTCOME_PERSONAL_MESSAGE:
                return new OutcomeMessageViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.message_outcome_item, parent, false));
            case INCOME_GROUP_MESSAGE:
                return new IncomeGroupMessageViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.message_group_income_item, parent, false));
            case INCOME_PERSONAL_MESSAGE:
                return new IncomePersonalMessageViewHolder(
                        LayoutInflater
                                .from(parent.getContext())
                                .inflate(R.layout.message_personal_income_item, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
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
}
