package com.offgrid.coupler.ui.message.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Message;

public class IncomePersonalMessageViewHolder extends MessageViewHolder {

    private final TextView message;
    private Context context;

    public IncomePersonalMessageViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        message = itemView.findViewById(R.id.message_body);
    }

    @Override
    public void update(Message message) {
        this.message.setText(message.getMessage());
    }
}
