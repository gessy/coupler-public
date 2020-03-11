package com.offgrid.coupler.ui.contact;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> liveUsers;

    public ContactListViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    void loadUsers() {
        liveUsers = userRepository.getUsers();
    }

    List<User> getUsers() {
        return liveUsers !=null ? liveUsers.getValue() : new ArrayList<User>();
    }

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<User>> observer) {
        liveUsers.observe(owner, observer);
    }

    void insertChat(User user) {
        userRepository.insert(user);
    }
}
