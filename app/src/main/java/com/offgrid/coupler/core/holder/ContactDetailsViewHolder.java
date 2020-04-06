package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.dto.UserDto;

public class ContactDetailsViewHolder {

    private final TextView fullName;

    public ContactDetailsViewHolder(View itemView) {
        fullName = itemView.findViewById(R.id.contact_full_name);
    }

    public void update(UserDto dto) {
        fullName.setText(dto.getFullName());
    }
}
