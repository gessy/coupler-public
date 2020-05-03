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

import com.offgrid.coupler.data.entity.Country;
import com.offgrid.coupler.data.repository.CountryRepository;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private CountryRepository countryRepository;
    private final MutableLiveData<Boolean> load = new MutableLiveData();
    private LiveData<List<Country>> liveCountries;

    public CountryViewModel(Application application) {
        super(application);
        countryRepository = new CountryRepository(application);
        liveCountries = Transformations.switchMap(load, new Function<Boolean, LiveData<List<Country>>>() {
            @Override
            public LiveData<List<Country>> apply(Boolean load) {
                return countryRepository.getCountries();
            }
        });
    }

    public void load() {
        load.setValue(true);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Country>> observer) {
        liveCountries.observe(owner, observer);
    }
}
