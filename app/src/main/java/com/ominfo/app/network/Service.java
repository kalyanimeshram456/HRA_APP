package com.ominfo.app.network;
import com.google.gson.JsonElement;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Service {

    private NetworkAPIServices networkAPIServices;

    public Service(NetworkAPIServices networkAPIServices) {
        this.networkAPIServices = networkAPIServices;
    }

    public Observable<JsonElement> executeWelcomeAPI() {
        return networkAPIServices.welcome(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.GET_WELCOME));
    }

    public Observable<JsonElement> executeBookWithTopicApi(String mTopicName) {
        return networkAPIServices.getBookWithTopic(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.GET_BOOK_TOPIC+mTopicName));
    }

    public Observable<JsonElement> executeUserListApi(String mLimit) {
        return networkAPIServices.getBookWithTopic(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.GET_USER_LIST+mLimit));
    }

    public Observable<JsonElement> executeDashboardAPI() {
        return networkAPIServices.dashboard(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.GET_DASHBOARD));
    }

    //dummy apis
    public Observable<JsonElement> executeResendOTPAPI() {
        return networkAPIServices.resendOTP(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.RESEND_otp));
    }

    public Observable<JsonElement> executeFetchProfileInfoAPI() {
        return networkAPIServices.FetchProfileInfo(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.user_info));
    }

    public Observable<JsonElement> executeUpdateProfileInfoAPI(RequestBody mRequestBodyFirstName, RequestBody mRequestBodyDOB, RequestBody mRequestBodyEmail,
                                                               RequestBody mRequestBodyPhone, RequestBody mRequestBodyAddress, RequestBody mRequestBodyCity, RequestBody mRequestBodyState,
                                                               RequestBody mRequestBodyZipCode
            , MultipartBody.Part image) {
        return networkAPIServices.updateProfileInfo(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL
                , DynamicAPIPath.profile_update), mRequestBodyFirstName, mRequestBodyDOB, mRequestBodyEmail, mRequestBodyPhone,
                mRequestBodyAddress, mRequestBodyCity, mRequestBodyState, mRequestBodyZipCode, image);
    }


    public Observable<JsonElement> executeUploadDocToVerification(RequestBody doc_type,MultipartBody.Part front_image,MultipartBody.Part back_image) {
        return networkAPIServices.UploadDocToVerification(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL
                , DynamicAPIPath.document_upload), doc_type,front_image,back_image);
    }

    public Observable<JsonElement> executeUserChargeAPI() {
        return networkAPIServices.FetchUserCharge(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.USER_CHARGE));
    }

    public Observable<JsonElement> executeUpdateDocToVerification(RequestBody doc_type,MultipartBody.Part front_image,MultipartBody.Part back_image) {
        return networkAPIServices.UpdateDocToVerification(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL
                , DynamicAPIPath.DOCUMENT_UPDATE), doc_type,front_image,back_image);
    }



}
