package com.offgrid.coupler.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.holder.MessageViewHolder;

public class IncomeGroupMessageViewHolder extends MessageViewHolder {

    private final TextView message;
    private final TextView fullName;
    private final View avatar;
    private Context context;

    public IncomeGroupMessageViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        message = itemView.findViewById(R.id.message_body);
        fullName = itemView.findViewById(R.id.user_full_name);
        avatar = itemView.findViewById(R.id.avatar);
    }

    @Override
    public void update(Message message) {
        this.message.setText(message.getMessage());
        this.fullName.setText(message.getUserFullName());
    }
}
