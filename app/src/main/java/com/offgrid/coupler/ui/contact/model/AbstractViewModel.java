package com.offgrid.coupler.ui.contact.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.repository.UserRepository;


public abstract class AbstractViewModel extends AndroidViewModel {

    protected UserRepository userRepository;

    protected AbstractViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void delete(String gid) {
        userRepository.delete(gid);
    }

    public void delete(long id) {
        userRepository.delete(id);
    }

}
