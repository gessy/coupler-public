package com.offgrid.coupler.ui.contact;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.repository.UserRepository;


public class ContactViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<User> liveUser;

    public ContactViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    void loadUser(String gid) {
        liveUser = userRepository.getUserByGid(gid);
    }

    User getUser() {
        return liveUser != null ? liveUser.getValue() : null;
    }

    void insertUser(User user) {
        userRepository.insert(user);
    }

    void delete(long id) {
        userRepository.delete(id);
    }

    void delete(String gid) {
        userRepository.delete(gid);
    }

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super User> observer) {
        liveUser.observe(owner, observer);
    }


}
