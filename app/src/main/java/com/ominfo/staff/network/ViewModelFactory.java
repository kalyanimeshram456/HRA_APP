package com.ominfo.staff.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ominfo.staff.ui.login.model.LoginViewModel;
import com.ominfo.staff.ui.lr_number.model.GetVehicleViewModel;
import com.ominfo.staff.ui.lr_number.model.PlantViewModel;
import com.ominfo.staff.ui.lr_number.model.SearchViewModel;
import com.ominfo.staff.ui.lr_number.model.VehicalNoViewModel;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Service service;

    @Inject
    public ViewModelFactory(Service service) {
        this.service = service;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(service);
          }
         else  if (modelClass.isAssignableFrom(VehicalNoViewModel.class)) {
            return (T) new VehicalNoViewModel(service);
        }
         else  if (modelClass.isAssignableFrom(PlantViewModel.class)) {
             return (T) new PlantViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(GetVehicleViewModel.class)) {
             return (T) new GetVehicleViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(VehicleDetailsViewModel.class)) {
             return (T) new VehicleDetailsViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SearchViewModel.class)) {
             return (T) new SearchViewModel(service);
         }


        throw new IllegalArgumentException("Unknown class name");

    }
}
