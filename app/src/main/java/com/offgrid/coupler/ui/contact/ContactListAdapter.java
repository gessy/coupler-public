package com.offgrid.coupler.ui.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.dto.ChatDto;
import com.offgrid.coupler.model.dto.UserDto;
import com.offgrid.coupler.ui.message.MessageActivity;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<User> users;
    private Context context;

    ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactInfoActivity.class);
                intent.putExtras(
                        new UserDto
                                .BundleBuilder()
                                .withId(current.getId())
                                .withFirstName(current.getFirstName())
                                .withLastName(current.getLastName())
                                .withGid(current.getGid())
                                .build()
                );
                context.startActivity(intent);
            }
        });

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
