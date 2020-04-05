package com.offgrid.coupler.core.model.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.repository.UserRepository;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private final MutableLiveData<Boolean> load = new MutableLiveData();
    private LiveData<List<User>> liveUsers;

    public ContactListViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);

        liveUsers = Transformations.switchMap(load, new Function<Boolean, LiveData<List<User>>>() {
            @Override
            public LiveData<List<User>> apply(Boolean load) {
                return userRepository.getOrderedUsers();
            }
        });
    }

    public void load() {
        load.setValue(true);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<User>> observer) {
        liveUsers.observe(owner, observer);
    }
}
