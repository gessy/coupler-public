package com.offgrid.coupler.ui.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<User> users;

    ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ContactListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactListItemViewHolder holder, int position) {
        final User current = users != null ? users.get(position) : User.getEmpty();
        holder.update(current);
    }

    void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }
}
