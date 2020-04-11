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

import com.offgrid.coupler.data.entity.Placelist;
import com.offgrid.coupler.data.repository.PlacelistRepository;

import java.util.List;

public class PlacelistViewModel extends AndroidViewModel {

    private PlacelistRepository placelistRepository;
    private final MutableLiveData<Boolean> load = new MutableLiveData();
    private LiveData<List<Placelist>> livePlacelist;

    public PlacelistViewModel(Application application) {
        super(application);
        placelistRepository = new PlacelistRepository(application);
        livePlacelist = Transformations.switchMap(load, new Function<Boolean, LiveData<List<Placelist>>>() {
            @Override
            public LiveData<List<Placelist>> apply(Boolean load) {
                return placelistRepository.getLists();
            }
        });
    }

    public void upsert(Placelist placelist) {
        if (placelist.isNew()) {
            placelistRepository.insert(new Placelist(placelist.getName()));
        } else {
            placelistRepository.update(placelist);
        }
    }

    public void remove(Placelist placelist) {
        placelistRepository.delete(placelist);
    }

    public void load() {
        load.setValue(true);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Placelist>> observer) {
        livePlacelist.observe(owner, observer);
    }
}
