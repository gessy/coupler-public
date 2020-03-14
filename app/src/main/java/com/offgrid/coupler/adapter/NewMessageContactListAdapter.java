package com.offgrid.coupler.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.ui.contact.ContactListItemViewHolder;
import com.offgrid.coupler.ui.message.MessageActivity;


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
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtras(
                        new ChatDto
                                .BundleBuilder()
                                .withReference(current.getId())
                                .withTitle(current.getFirstName() + " " + current.getLastName())
                                .withType(ChatType.PERSONAL)
                                .build()
                );
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

}
