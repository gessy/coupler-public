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
import com.offgrid.coupler.data.repository.RegionRepository;


public class RegionViewModel extends AndroidViewModel {

    private RegionRepository regionRepository;
    private final MutableLiveData<Long> load = new MutableLiveData();
    private LiveData<Region> liveRegion;

    public RegionViewModel(Application application) {
        super(application);
        regionRepository = new RegionRepository(application);
        liveRegion = Transformations.switchMap(load, new Function<Long, LiveData<Region>>() {
            @Override
            public LiveData<Region> apply(Long id) {
                return regionRepository.getRegion(id);
            }
        });
    }

    public void update(Region region) {
        regionRepository.update(region);
    }


    public void load(Long id) {
        load.setValue(id);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Region> observer) {
        liveRegion.observe(owner, observer);
    }
}
