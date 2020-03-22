package com.offgrid.coupler.core.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.util.DateUtil;

public class ContactListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView message;

    private Context context;

    public ContactListItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        title = itemView.findViewById(R.id.chat_list_title);
        message = itemView.findViewById(R.id.chat_list_message);
    }

    public void update(User user) {
        this.title.setText(user.getFirstName() + " " + user.getLastName());
        this.message.setText(DateUtil.formatUserActivityDate(user.getLastSeen()));
    }

}
