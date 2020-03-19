package com.offgrid.coupler.core.model.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.repository.UserRepository;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> liveUsers;

    public ContactListViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void load() {
        liveUsers = userRepository.getUsers();
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<User>> observer) {
        liveUsers.observe(owner, observer);
    }

}
