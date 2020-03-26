package com.offgrid.coupler.core.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import com.offgrid.coupler.data.entity.User;

import java.util.List;

public class UserKeyProvider extends ItemKeyProvider {
    private final List<User> userList;

    public UserKeyProvider(List<User> userList) {
        super(SCOPE_CACHED);
        this.userList = userList;
    }

    @Nullable
    @Override
    public Object getKey(int position) {
        return userList.get(position);
    }

    @Override
    public int getPosition(@NonNull Object key) {
        return userList.indexOf(key);
    }

}
