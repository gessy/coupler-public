package com.offgrid.coupler.core.adapter;

import android.content.Context;

import androidx.recyclerview.selection.SelectionTracker;

import com.offgrid.coupler.core.holder.ContactItemViewHolder;
import com.offgrid.coupler.data.entity.User;


public class MembershipContactListAdapter extends ContactListAdapter {

    private SelectionTracker selectionTracker;

    public MembershipContactListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {
        final User current = users != null ? users.get(position) : User.getEmpty();
        holder.update(current, selectionTracker.isSelected(current));
    }


    public void setSelectionTracker(SelectionTracker selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

}
