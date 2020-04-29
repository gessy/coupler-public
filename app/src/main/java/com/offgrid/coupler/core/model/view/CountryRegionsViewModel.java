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

import com.offgrid.coupler.data.entity.CountryRegions;
import com.offgrid.coupler.data.repository.RegionRepository;


public class CountryRegionsViewModel extends AndroidViewModel {

    private RegionRepository regionRepository;
    private final MutableLiveData<Long> load = new MutableLiveData();
    private LiveData<CountryRegions> liveCountryRegions;

    public CountryRegionsViewModel(Application application) {
        super(application);
        regionRepository = new RegionRepository(application);
        liveCountryRegions = Transformations.switchMap(load, new Function<Long, LiveData<CountryRegions>>() {
            @Override
            public LiveData<CountryRegions> apply(Long id) {
                return regionRepository.getCountryRegions(id);
            }
        });
    }

    public void load(Long id) {
        load.setValue(id);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super CountryRegions> observer) {
        liveCountryRegions.observe(owner, observer);
    }
}
