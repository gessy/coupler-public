package com.offgrid.coupler.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.contact.ContactInfoActivity;
import com.offgrid.coupler.core.holder.ContactItemViewHolder;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.core.model.dto.wrapper.DtoUserWrapper;
import com.offgrid.coupler.data.entity.User;

import static com.offgrid.coupler.core.model.SourceActivity.GROUP_INFO;

public class GroupMembershipListAdapter extends ContactListAdapter {

    private GroupDto groupDto;

    public GroupMembershipListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {
        final User current = users != null ? users.get(position) : User.getEmpty();
        holder.update(current);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactInfoActivity.class);
                intent.putExtras(DtoUserWrapper.convertAndWrap(current, groupDto, GROUP_INFO));
                context.startActivity(intent);
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.popup_context_in, R.anim.popup_out);
            }
        });
    }

    public void setDto(GroupDto groupDto) {
        this.groupDto = groupDto;
    }

}
