package com.offgrid.coupler.model.view;

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

    public void loadByGid(String gid) {
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

    public void delete(String gid) {
        userRepository.delete(gid);
    }

    public void delete(long userId) {
        userRepository.delete(userId);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super User> observer) {
        liveUser.observe(owner, observer);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }
}
