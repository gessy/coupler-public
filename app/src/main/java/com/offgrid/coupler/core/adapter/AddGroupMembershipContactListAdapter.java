package com.offgrid.coupler.core.adapter;

import android.content.Context;

import com.offgrid.coupler.core.holder.ContactListItemViewHolder;
import com.offgrid.coupler.data.entity.User;


public class AddGroupMembershipContactListAdapter extends ContactListAdapter {

    public AddGroupMembershipContactListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ContactListItemViewHolder holder, int position) {
        final User current = users != null ? users.get(position) : User.getEmpty();
        holder.update(current);
    }
}
