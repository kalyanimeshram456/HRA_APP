package com.ominfo.staff.network;

import com.google.gson.JsonElement;
import com.ominfo.staff.ui.lr_number.model.CheckLrRequest;
import com.ominfo.staff.ui.lr_number.model.CheckLrResponse;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRespoonse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NetworkAPIServices {

    @POST()
    Observable<JsonElement> login(@Url String url,@Query("jsonreq")  String request);

    @POST()
    Observable<JsonElement> vehicleNo(@Url String url,@Query("jsonreq")  String request);

    @POST()
    Observable<JsonElement> plant(@Url String url,@Query("jsonreq")  String request);

    @POST()
    Observable<JsonElement> search(@Url String url,@Query("jsonreq")  String request);

    @POST()
    Observable<JsonElement> getVehicleList(@Url String url,@Query("jsonreq")  String request);

    @POST()
    Observable<JsonElement> VehicleDetails(@Url String url,@Query("jsonreq")  String request);

   /* @POST()
    Observable<JsonElement> checkLrNo(@Url String url,@Query("jsonreq")  String request);
*/
    @POST()
    Call<CheckLrResponse> checkLrNo(@Url String url,@Query("jsonreq")  String request);


    @Multipart
    @POST()
    Call<UploadVehicleRecordRespoonse> uploadVehicleRecord(@Url String url,
                                                           @Part("action") RequestBody uploadType,
                                                           @Part("jsonreq") RequestBody uploadTypeImage);

    //

    @POST()
    Observable<JsonElement> fetchKataChitthi(@Url String url,@Query("jsonreq")  String request);

    /*@Multipart
    @POST()
    Observable<JsonElement> uploadVehicleRecord(@Url String url,
                                            @Part("userkey") RequestBody uploadType,
                                            @Part("jsonreq") RequestBody uploadTypeImage);*/

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
