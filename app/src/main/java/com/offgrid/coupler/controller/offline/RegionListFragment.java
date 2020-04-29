package com.offgrid.coupler.controller.offline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.adapter.RegionAdapter;
import com.offgrid.coupler.core.model.dto.CountryDto;
import com.offgrid.coupler.core.model.dto.wrapper.DtoCountryWrapper;
import com.offgrid.coupler.core.model.view.CountryRegionsViewModel;
import com.offgrid.coupler.data.entity.Country;
import com.offgrid.coupler.data.entity.CountryRegions;


public class RegionListFragment extends Fragment implements Observer<CountryRegions> {
    private CountryRegionsViewModel regionsViewModel;

    private RegionAdapter regionAdapter;

    private CountryDto countryDto;


    public static RegionListFragment newInstance(Country country) {
        RegionListFragment fragment = new RegionListFragment();
        fragment.setArguments(DtoCountryWrapper.convertAndWrap(country));
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_region_list, container, false);

        countryDto = CountryDto.getInstance(getArguments());

        regionAdapter = new RegionAdapter(getContext());

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_country_regions);
        recyclerView.setAdapter(regionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        initViewModel();

        return root;
    }


    private void initViewModel() {
        regionsViewModel = new ViewModelProvider(this).get(CountryRegionsViewModel.class);
        regionsViewModel.observe(this, this);
        regionsViewModel.load(countryDto.getId());
    }


    @Override
    public void onChanged(CountryRegions countryRegions) {
        if (countryRegions != null) {
            regionAdapter.setRegionList(countryRegions.regions);
        }
    }

}