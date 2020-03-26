package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.UserLookupDetails;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.util.DateUtil;

public class ContactItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView fullName;
    private final TextView activityDate;
    private User user;

    private final CardView addContact;

    public ContactItemViewHolder(View itemView) {
        super(itemView);
        fullName = itemView.findViewById(R.id.contact_full_name);
        activityDate = itemView.findViewById(R.id.contact_activity_date);
        addContact = itemView.findViewById(R.id.add_contact);
    }

    public void update(User user) {
        update(user, false);
    }

    public void update(User user, boolean isActive) {
        this.addContact.setVisibility(isActive ? View.VISIBLE : View.INVISIBLE);
        this.user = user;
        this.fullName.setText(user.fullName());
        this.activityDate.setText(DateUtil.formatUserActivityDate(user.getLastSeen()));
    }

    public ItemDetailsLookup.ItemDetails getLookupDetails() {
        return new UserLookupDetails(getAdapterPosition(), user);
    }
}
