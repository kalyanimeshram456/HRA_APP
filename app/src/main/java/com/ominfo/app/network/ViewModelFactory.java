package com.ominfo.app.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ominfo.app.ui.kata_chithi.model.FetchKataChitthiViewModel;
import com.ominfo.app.ui.kata_chithi.model.SaveKataChitthiViewModel;
import com.ominfo.app.ui.login.model.LoginViewModel;

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
         else  if (modelClass.isAssignableFrom(FetchKataChitthiViewModel.class)) {
             return (T) new FetchKataChitthiViewModel(service);
         }
         else  if (modelClass.isAssignableFrom(SaveKataChitthiViewModel.class)) {
             return (T) new SaveKataChitthiViewModel(service);
         }

        throw new IllegalArgumentException("Unknown class name");

    }
}
