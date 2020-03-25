package com.offgrid.coupler.core.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.UserLookupDetails;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.util.DateUtil;

public class ContactListItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView message;
    private User user;

    private final CardView addItem;

    private Context context;

    public ContactListItemViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        title = itemView.findViewById(R.id.chat_list_title);
        message = itemView.findViewById(R.id.chat_list_message);
        addItem = itemView.findViewById(R.id.add_item);
    }

    public void update(User user) {
        this.user = user;
        this.title.setText(user.fullName());
        this.message.setText(DateUtil.formatUserActivityDate(user.getLastSeen()));
    }

    public void update(User user, boolean isActive) {
        this.addItem.setVisibility(isActive ? View.VISIBLE : View.INVISIBLE);
        this.user = user;
        this.title.setText(user.fullName() + isActive);
        this.message.setText(DateUtil.formatUserActivityDate(user.getLastSeen()));
    }

    public ItemDetailsLookup.ItemDetails getItemDetails() {
        return new UserLookupDetails(getAdapterPosition(), user);
    }

}
