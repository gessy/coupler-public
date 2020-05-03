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

import com.offgrid.coupler.data.entity.ListPlaces;
import com.offgrid.coupler.data.entity.Place;
import com.offgrid.coupler.data.repository.PlaceRepository;
import com.offgrid.coupler.data.repository.PlacelistRepository;

import java.util.List;


public class CollectionOfListPlacesViewModel extends AndroidViewModel {

    private PlacelistRepository placelistRepository;
    private PlaceRepository placeRepository;

    private final MutableLiveData<Boolean> load = new MutableLiveData();
    private LiveData<List<ListPlaces>> liveListPlaces;

    public CollectionOfListPlacesViewModel(Application application) {
        super(application);
        placelistRepository = new PlacelistRepository(application);
        placeRepository = new PlaceRepository(application);
        liveListPlaces = Transformations.switchMap(load, new Function<Boolean, LiveData<List<ListPlaces>>>() {
            @Override
            public LiveData<List<ListPlaces>> apply(Boolean load) {
                return placelistRepository.getVisibleListPlaces();
            }
        });
    }

    public void load() {
        load.setValue(true);
    }

    public void removePlace(Place place) {
        placeRepository.delete(place);
    }

    public void updatePlace(Place place) {
        placeRepository.update(place);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<ListPlaces>> observer) {
        liveListPlaces.observe(owner, observer);
    }
}
