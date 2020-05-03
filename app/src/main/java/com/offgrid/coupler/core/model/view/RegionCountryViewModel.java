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

import com.offgrid.coupler.data.entity.Region;
import com.offgrid.coupler.data.entity.RegionCountry;
import com.offgrid.coupler.data.model.DownloadState;
import com.offgrid.coupler.data.repository.RegionRepository;

import java.util.List;

public class RegionCountryViewModel extends AndroidViewModel {

    private RegionRepository regionRepository;
    private final MutableLiveData<Boolean> load = new MutableLiveData();
    private LiveData<List<RegionCountry>> liveRegionCountry;

    public RegionCountryViewModel(Application application) {
        super(application);
        regionRepository = new RegionRepository(application);
        liveRegionCountry = Transformations.switchMap(load, new Function<Boolean, LiveData<List<RegionCountry>>>() {
            @Override
            public LiveData<List<RegionCountry>> apply(Boolean load) {
                return regionRepository.getLoadedRegionCountry();
            }
        });
    }

    public void dropFromLoad(Region region) {
        region.setDownloadState(DownloadState.READY_TO_LOAD);
        regionRepository.update(region);
    }


    public void load() {
        load.setValue(true);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<RegionCountry>> observer) {
        liveRegionCountry.observe(owner, observer);
    }
}
