package com.offgrid.coupler.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.core.holder.ContactListItemViewHolder;
import com.offgrid.coupler.controller.chat.ChatActivity;
import com.offgrid.coupler.core.model.dto.wrapper.DtoChatWrapper;


public class NewMessageContactListAdapter extends ContactListAdapter {

    public NewMessageContactListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ContactListItemViewHolder holder, int position) {
        final User current = users != null ? users.get(position) : User.getEmpty();
        holder.update(current);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtras(DtoChatWrapper.convertAndWrap(current));
                context.startActivity(intent);
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
            }
        });
    }
}
