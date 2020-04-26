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
import com.offgrid.coupler.data.entity.CountryRegions;
import com.offgrid.coupler.data.entity.Region;
import com.offgrid.coupler.data.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;

public class CountryRegionViewModel extends AndroidViewModel {

    private CountryRepository countryRepository;
    private final MutableLiveData<Boolean> load = new MutableLiveData();
    private LiveData<List<CountryRegions>> liveCountryRegion;

    public CountryRegionViewModel(Application application) {
        super(application);
        countryRepository = new CountryRepository(application);
        liveCountryRegion = Transformations.switchMap(load, new Function<Boolean, LiveData<List<CountryRegions>>>() {
            @Override
            public LiveData<List<CountryRegions>> apply(Boolean load) {
                return countryRepository.getCountryRegions();
            }
        });
    }

    public List<CountryRegions> get() {
        return liveCountryRegion.getValue();
    }

    public List<Country> getCountries() {
        List<Country> list = new ArrayList<>();
        if (liveCountryRegion.getValue() != null) {
            for (CountryRegions countryRegions : liveCountryRegion.getValue()) {
                list.add(countryRegions.country);
            }
        }
        return list;
    }

    public List<Region> getRegions(Long countryId) {
        if (liveCountryRegion.getValue() != null) {
            for (CountryRegions countryRegions : liveCountryRegion.getValue()) {
                if (countryRegions.countryId.equals(countryId)) {
                    return countryRegions.regions;
                }
            }
        }
        return new ArrayList<>();
    }


    public void load() {
        load.setValue(true);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<CountryRegions>> observer) {
        liveCountryRegion.observe(owner, observer);
    }
}
