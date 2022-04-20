package com.ominfo.crm_solution.ui.enquiry_report.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.Service;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class SearchCustViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private Service service;


    public SearchCustViewModel(Service service) {
        this.service = service;
    }

    public MutableLiveData<ApiResponse> getResponse() {
        return responseLiveData;
    }


    public void hitSearchCustApi(RequestBody mRequestBodyTypeAction,RequestBody mRequestBodyTypeEmpID
            ,RequestBody mRequestBodyTypeCompID,RequestBody mRequestBodyTypeString) {

        disposables.add(service.executeSearchCustAPI(mRequestBodyTypeAction,mRequestBodyTypeEmpID,
                mRequestBodyTypeCompID,mRequestBodyTypeString)
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

