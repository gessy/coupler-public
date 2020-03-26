package com.offgrid.coupler.core.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.controller.chat.ChatActivity;
import com.offgrid.coupler.core.model.dto.wrapper.DtoChatWrapper;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.core.holder.ContactItemViewHolder;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactItemViewHolder> {

    private final LayoutInflater mInflater;
    protected List<User> users;
    protected Context context;

    public ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ContactItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {
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

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }
}
