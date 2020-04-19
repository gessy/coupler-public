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
import com.offgrid.coupler.data.entity.Placelist;
import com.offgrid.coupler.data.repository.PlaceRepository;
import com.offgrid.coupler.data.repository.PlacelistRepository;


public class ListPlacesViewModel extends AndroidViewModel {

    private PlacelistRepository placelistRepository;
    private PlaceRepository placeRepository;

    private final MutableLiveData<Long> load = new MutableLiveData();
    private LiveData<ListPlaces> liveListPlaces;

    public ListPlacesViewModel(Application application) {
        super(application);
        placelistRepository = new PlacelistRepository(application);
        placeRepository = new PlaceRepository(application);
        liveListPlaces = Transformations.switchMap(load, new Function<Long, LiveData<ListPlaces>>() {
            @Override
            public LiveData<ListPlaces> apply(Long listId) {
                return placelistRepository.getListPlaces(listId);
            }
        });
    }

    public void visibility(boolean state) {
        ListPlaces listPlaces = liveListPlaces.getValue();
        if (listPlaces != null && listPlaces.placelist != null) {
            Placelist placelist = listPlaces.placelist;
            placelist.setShowOnMap(state);
            placelistRepository.update(placelist);
        }
    }

    public void remove() {
        ListPlaces listPlaces = liveListPlaces.getValue();
        if (listPlaces != null && listPlaces.placelist != null) {
            placelistRepository.delete(listPlaces.placelist);
        }
    }

    public void removePlace(Place place) {
        placeRepository.delete(place);
    }

    public void updatePlace(Place place) {
        placeRepository.update(place);
    }

    public void load(Long listId) {
        load.setValue(listId);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super ListPlaces> observer) {
        liveListPlaces.observe(owner, observer);
    }
}
