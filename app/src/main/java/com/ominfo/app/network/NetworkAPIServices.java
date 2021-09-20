package com.ominfo.app.network;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NetworkAPIServices {

    @GET()
    Observable<JsonElement> welcome(@Url String url);

    @GET()
    Observable<JsonElement> checkSetup(@Url String url);

    @GET()
    Observable<JsonElement> dashboard(@Url String url);


    @GET()
    Observable<JsonElement> getBookWithTopic(@Url String url);

    //dummy apis
    @POST()
    Observable<JsonElement> resendOTP(@Url String url);

    @POST()
    Observable<JsonElement> FetchProfileInfo(@Url String url);


    @Multipart
    @POST()
    Observable<JsonElement> updateProfileInfo(@Url String url,
                                              @Part("full_name") RequestBody firstName,
                                              @Part("date_of_birth") RequestBody dob,
                                              @Part("email") RequestBody email,
                                              @Part("mobile") RequestBody phone,
                                              @Part("address") RequestBody address,
                                              @Part("city") RequestBody city,
                                              @Part("state") RequestBody state,
                                              @Part("zip_code") RequestBody zipcode
            , @Part MultipartBody.Part image);


    @Multipart
    @POST()
    Observable<JsonElement> UploadDocToVerification(@Url String url,
                                            @Query("doc_type") RequestBody doc_type
            , @Part MultipartBody.Part front_image, @Part MultipartBody.Part back_image);

    @POST()
    Observable<JsonElement> FetchUserCharge(@Url String url);

    @Multipart
    @POST()
    Observable<JsonElement> UpdateDocToVerification(@Url String url,
                                                    @Part("doc_type") RequestBody doc_type
            , @Part MultipartBody.Part front_image, @Part MultipartBody.Part back_image);


}
