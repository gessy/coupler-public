package com.offgrid.coupler.ui.contact.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.User;


public class ContactViewModel extends AbstractViewModel {

    private LiveData<User> liveUser;

    public ContactViewModel(Application application) {
        super(application);
    }

    public void load(String gid) {
        liveUser = userRepository.getUserByGid(gid);
    }

    public User get() {
        return liveUser != null ? liveUser.getValue() : null;
    }

    public void delete() {
        if (liveUser != null && liveUser.getValue() != null) {
            delete(liveUser.getValue().getId());
        }
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super User> observer) {
        liveUser.observe(owner, observer);
    }

}
