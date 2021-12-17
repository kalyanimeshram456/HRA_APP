package com.ominfo.crm_solution.network;
import com.google.gson.JsonElement;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class Service {

    private NetworkAPIServices networkAPIServices;

    public Service(NetworkAPIServices networkAPIServices) {
        this.networkAPIServices = networkAPIServices;
    }

    public Observable<JsonElement> executeLoginAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyType1,RequestBody mRequestBodyType2) {
        return networkAPIServices.login(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_LOGIN),mRequestBodyType,mRequestBodyType1,mRequestBodyType2);
    }

    public Observable<JsonElement> executeVehicleNoAPI(String request) {
        return networkAPIServices.vehicleNo(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_VEHICLE_NO),request);
    }

    public Observable<JsonElement> executePlantAPI(String request) {
        return networkAPIServices.plant(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_PLANT),request);
    }

    public Observable<JsonElement> executeSearchAPI(String request) {
        return networkAPIServices.search(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_SEARCH),request);
    }

    public Observable<JsonElement> executeGetVehicleAPI(String request) {
        return networkAPIServices.getVehicleList(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_GET_VEHICLE),request);
    }

    public Observable<JsonElement> executeVehicleDetailsAPI(String request) {
        return networkAPIServices.VehicleDetails(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_VEHICLE_DETAILS),request);
    }

   /* public Observable<JsonElement> executeCEWBDetailsAPI(String request) {
        return networkAPIServices.cewbDetails(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_CEWB_DETAILS),request);
    }*/

    /*public Observable<JsonElement> executeUploadVehicleAPI( RequestBody mRequestBodyType, RequestBody mRequestBodyTypeImage) {
        return networkAPIServices.uploadVehicleRecord(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_UPLOAD_VEHICLE),mRequestBodyType,mRequestBodyTypeImage);
    }*/

    //
    public Observable<JsonElement> executeFetchKataChitthiAPI(String request) {
        return networkAPIServices.fetchKataChitthi(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.POST_FETCH_KATA_CHITTI),request);
    }

    public Observable<JsonElement> executeUserListApi(String mLimit) {
        return networkAPIServices.getBookWithTopic(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL, DynamicAPIPath.GET_USER_LIST+mLimit));
    }

}
