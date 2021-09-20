package com.ominfo.app.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
         /*if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(service);
          }*/

        throw new IllegalArgumentException("Unknown class name");

    }
}
