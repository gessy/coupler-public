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

import com.offgrid.coupler.core.model.Operation;
import com.offgrid.coupler.data.entity.Place;
import com.offgrid.coupler.data.repository.PlaceRepository;


public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository placeRepository;

    private final MutableLiveData<Long> load = new MutableLiveData();
    private final MutableLiveData<Operation> liveOperation = new MutableLiveData();

    private LiveData<Place> livePlace;

    public PlaceViewModel(Application application) {
        super(application);
        placeRepository = new PlaceRepository(application);
        livePlace = Transformations.switchMap(load, new Function<Long, LiveData<Place>>() {
            @Override
            public LiveData<Place> apply(Long placeId) {
                return placeRepository.getPlace(placeId);
            }
        });
    }

    public void remove() {
        Place current = livePlace.getValue();
        if (current != null) {
            placeRepository.delete(current);
            liveOperation.setValue(Operation.DELETE);
        }
    }

    public void update(Place place) {
        Place current = livePlace.getValue();
        if (current != null) {
            current.updateWith(place);
            placeRepository.update(current);
            liveOperation.setValue(Operation.UPDATE);
        }
    }

    public void insert(Place place) {
        placeRepository.insert(place);
        liveOperation.setValue(Operation.INSERT);
    }

    public void load(Long listId) {
        load.setValue(listId);
        liveOperation.setValue(Operation.LOAD);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Place> observer) {
        livePlace.observe(owner, observer);
    }

    public void observeOperation(@NonNull LifecycleOwner owner, @NonNull Observer<? super Operation> observer) {
        liveOperation.observe(owner, observer);
    }
}
