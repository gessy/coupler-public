package com.offgrid.coupler.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.core.holder.IncomeGroupMessageViewHolder;
import com.offgrid.coupler.core.holder.IncomePersonalMessageViewHolder;
import com.offgrid.coupler.core.holder.MessageViewHolder;
import com.offgrid.coupler.core.holder.OutcomeMessageViewHolder;

import java.util.List;

import static com.offgrid.coupler.core.adapter.MessageListAdapter.Const.INCOME_GROUP_MESSAGE;
import static com.offgrid.coupler.core.adapter.MessageListAdapter.Const.INCOME_PERSONAL_MESSAGE;
import static com.offgrid.coupler.core.adapter.MessageListAdapter.Const.OUTCOME_GROUP_MESSAGE;
import static com.offgrid.coupler.core.adapter.MessageListAdapter.Const.OUTCOME_PERSONAL_MESSAGE;
import static com.offgrid.coupler.data.model.ChatType.GROUP;

public class MessageListAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    class Const {
        static final int UNDEFINED = 0;
        static final int INCOME_PERSONAL_MESSAGE = 1;
        static final int INCOME_GROUP_MESSAGE = 2;
        static final int OUTCOME_PERSONAL_MESSAGE = 3;
        static final int OUTCOME_GROUP_MESSAGE = 4;
    }


    private List<Message> messages;
    private ChatType chatType;

    private Context context;

    public MessageListAdapter(Context context, ChatType chatType) {
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

    public void setMessages(List<Message> messages) {
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
