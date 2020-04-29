package com.offgrid.coupler.controller.offline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.dto.CountryDto;
import com.offgrid.coupler.core.model.dto.wrapper.DtoCountryWrapper;
import com.offgrid.coupler.core.model.view.CountryRegionsViewModel;
import com.offgrid.coupler.data.entity.Country;
import com.offgrid.coupler.data.entity.CountryRegions;


public class CountryRegionsFragment extends Fragment implements Observer<CountryRegions> {
    private CountryRegionsViewModel regionsViewModel;
    private TextView textView;

    private CountryDto countryDto;


    public static CountryRegionsFragment newInstance(Country country) {
        CountryRegionsFragment fragment = new CountryRegionsFragment();
        fragment.setArguments(DtoCountryWrapper.convertAndWrap(country));
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_region_list, container, false);

        countryDto = CountryDto.getInstance(getArguments());

        textView = root.findViewById(R.id.section_label);

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
            textView.setText(countryRegions.country.getName());
        }
    }

}