package com.ominfo.hra_app.ui.my_account.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.Service;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ChangePasswordViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private Service service;


    public ChangePasswordViewModel(Service service) {
        this.service = service;
    }

    public MutableLiveData<ApiResponse> getResponse() {
        return responseLiveData;
    }

    public void executeChangePasswordAPI(RequestBody mRequestBodyType, RequestBody mRequestBodyComId
            , RequestBody mRequestBodyEmpId, RequestBody pass, RequestBody oldpass) {
        disposables.add(service.executeChangePasswordAPI(mRequestBodyType,mRequestBodyComId
        ,mRequestBodyEmpId,pass,oldpass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}

