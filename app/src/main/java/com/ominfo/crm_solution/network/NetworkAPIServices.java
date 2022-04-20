package com.ominfo.crm_solution.network;

import com.google.gson.JsonElement;
import com.ominfo.crm_solution.ui.dispatch_pending.model.DispatchRequest;
import com.ominfo.crm_solution.ui.lr_number.model.CheckLrResponse;
import com.ominfo.crm_solution.ui.lr_number.model.UploadVehicleRecordRespoonse;
import com.ominfo.crm_solution.ui.my_account.model.ProfileRequest;
import com.ominfo.crm_solution.ui.product.model.ProductRequest;
import com.ominfo.crm_solution.ui.quotation_amount.model.QuotationRequest;
import com.ominfo.crm_solution.ui.receipt.model.ReceiptRequest;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderRequest;
import com.ominfo.crm_solution.ui.reminders.model.EmployeeListRequest;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListRequest;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderRequest;
import com.ominfo.crm_solution.ui.sale.model.SalesRequest;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.EditVisitRequest;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NetworkAPIServices {

    /* @POST()
     Observable<JsonElement> login(@Url String url,@Query("jsonreq")  String request);*/
    @Multipart
    @POST()
    Observable<JsonElement> login(@Url String url,
                                  @Part("action") RequestBody uploadType,
                                  @Part("username") RequestBody uploadTypeImage,
                                  @Part("password") RequestBody uploadTypeImage1);

    @POST()
    Observable<JsonElement> profile(@Url String url,@Body ProfileRequest request);

    @POST()
    Observable<JsonElement> sales(@Url String url,@Body SalesRequest request);

    @POST()
    Observable<JsonElement> quotation(@Url String url,@Body QuotationRequest request);

    @POST()
    Observable<JsonElement> lostApportunity(@Url String url,@Body QuotationRequest request);

    @POST()
    Observable<JsonElement> dispatch(@Url String url,@Body DispatchRequest request);

    @POST()
    Observable<JsonElement> product(@Url String url,@Body ProductRequest request);

    @POST()
    Observable<JsonElement> reminderList(@Url String url,@Body ReminderListRequest request);

    @POST()
    Observable<JsonElement> empList(@Url String url,@Body EmployeeListRequest request);

    @POST()
    Observable<JsonElement> addReminder(@Url String url,@Body AddReminderRequest request);

    @PUT()
    Observable<JsonElement> updateReminder(@Url String url,@Body UpdateReminderRequest request);

    @POST()
    Observable<JsonElement> receipt(@Url String url,@Body ReceiptRequest request);

    @POST()
    Observable<JsonElement> addVisit(@Url String url,@Body AddVisitRequest request);

    @Multipart
    @POST()
    Observable<JsonElement> editVisit(@Url String url,
                                      @Part("action") RequestBody uploadType,
                                      @Part("company_ID") RequestBody uploadTypecompany_ID,
                                      @Part("employee") RequestBody uploadTypeemployee,
                                      @Part("visit_no") RequestBody uploadTypvisit_no,
                                      @Part("visit_time_end") RequestBody uploadTypvisit_time_end,
                                      @Part("rm_id") RequestBody uploadTyprm_id,
                                      @Part("place") RequestBody uploadTypplace,
                                      @Part("cust_name") RequestBody uploadTypcust_name,
                                      @Part("cust_mobile") RequestBody uploadTypcust_mobile,
                                      @Part("visiting_card") RequestBody uploadTypvisiting_card,
                                      @Part("topic") RequestBody uploadTyptopic,
                                      @Part("result") RequestBody uploadTypresult,
                                      @Part("description") RequestBody uploadTypdescription,
                                      @Part("visit_duration") RequestBody uploadTypvisit_duration,
                                      @Part("visit_location_name") RequestBody uploadTypvisit_location_name,
                                      @Part("visit_location_address") RequestBody uploadTypvisit_location_address,
                                      @Part("visit_location_latitude") RequestBody uploadTypvisit_location_latitude,
                                      @Part("visit_location_longitute") RequestBody uploadTypvisit_location_longitute,
                                      @Part("stop_location_name") RequestBody uploadTypstop_location_name,
                                      @Part("stop_location_address") RequestBody uploadTypstop_location_address,
                                      @Part("stop_location_latitude") RequestBody uploadTypstop_location_latitude,
                                      @Part("stop_location_longitute") RequestBody uploadTypstop_location_longitute,
                                      @Part("tour_id") RequestBody uploadTyptour_id);

    @Multipart
    @POST()
    Observable<JsonElement> getRM(@Url String url,
                                  @Part("action") RequestBody uploadType,
                                  @Part("employee") RequestBody uploadTypeImage,
                                  @Part("company_id") RequestBody uploadTypeImage1);

    @Multipart
    @POST()
    Observable<JsonElement> searchCust(@Url String url,
                                       @Part("action") RequestBody uploadType,
                                       @Part("employee") RequestBody uploadTypeEmp,
                                       @Part("company_id") RequestBody uploadTypeCompId,
                                       @Part("text") RequestBody uploadTypeText);

    @Multipart
    @POST()
    Observable<JsonElement> enquiryStatus(@Url String url,
                                          @Part("action") RequestBody uploadType,
                                          @Part("stage") RequestBody uploadTypeStage);


    @GET()
    Observable<JsonElement> getVisitNo(@Url String url);


    @Multipart
    @POST()
    Observable<JsonElement> getTour(@Url String url,
                                    @Part("action") RequestBody uploadType,
                                    @Part("company_id") RequestBody uploadTypeCompanyId);

    @Multipart
    @POST()
    Observable<JsonElement> getDashboard(@Url String url,
                                        @Part("action") RequestBody uploadType,
                                        @Part("employee") RequestBody uploadTypeEmployee,
                                        @Part("company_id") RequestBody companyId,
                                        @Part("start_date") RequestBody startDate,
                                        @Part("end_date") RequestBody endDate);

    @Multipart
    @POST()
    Observable<JsonElement> getProfileImage(@Url String url,
                                         @Part("action") RequestBody uploadType,
                                         @Part("company_id") RequestBody uploadTypeEmployee,
                                         @Part("employee_id") RequestBody companyId
    );

    @Multipart
    @POST()
    Observable<JsonElement> getNotification(@Url String url,
                                            @Part("action") RequestBody uploadType,
                                            @Part("company_id") RequestBody uploadTypeEmployee,
                                            @Part("employee_id") RequestBody companyId
    );

    @Multipart
    @POST()
    Observable<JsonElement> deleteNotification(@Url String url,
                                            @Part("action") RequestBody uploadType,
                                            @Part("company_id") RequestBody uploadTypeEmployee,
                                            @Part("employee_id") RequestBody companyId,
                                               @Part("notif_id") RequestBody notifId
    );

    @Multipart
    @POST()
    Observable<JsonElement> changeProfileImage(@Url String url,
                                            @Part("action") RequestBody uploadType,
                                            @Part("company_id") RequestBody uploadTypeEmployee,
                                            @Part("employee_id") RequestBody companyId
            , @Part("photoData") RequestBody image);

    @Multipart
    @POST()
    Observable<JsonElement> changePassword(@Url String url,
                                               @Part("action") RequestBody uploadType,
                                               @Part("company_id") RequestBody uploadTypeEmployee,
                                               @Part("employee_id") RequestBody companyId
                                              , @Part("password") RequestBody pass
                                              , @Part("oldpassword") RequestBody oldPass);

    @Multipart
    @POST()
    Observable<JsonElement> searchCrm(@Url String url,
                                           @Part("action") RequestBody uploadType,
                                           @Part("company_id") RequestBody uploadTypeEmployee,
                                           @Part("employee_id") RequestBody companyId,
                                          @Part("getresultdata") RequestBody pass
           );

    @Multipart
    @POST()
    Observable<JsonElement> saveEnquiry(@Url String url,
                                        @Part("action") RequestBody uploadType,
                                        @Part("enquiry") RequestBody enquiry,
                                        @Part("customer_id") RequestBody customer_id,
                                        @Part("customer_name") RequestBody customer_name,
                                        @Part("customer_mobile") RequestBody customer_mobile,
                                        @Part("product") RequestBody product,
                                        @Part("rm") RequestBody rm,
                                        @Part("description") RequestBody description,
                                        @Part("source") RequestBody source,
                                        @Part("company_id") RequestBody uploadTypeCompId,
                                        @Part("employee_id") RequestBody uploadTypeEmp);

    @Multipart
    @POST()
    Observable<JsonElement> getVisit(@Url String url,
                                     @Part("action") RequestBody uploadType,
                                     @Part("visit") RequestBody enquiry,
                                     @Part("company_id") RequestBody uploadTypeCompId,
                                     @Part("employee") RequestBody uploadTypeEmp,
                                     @Part("from_date") RequestBody uploadTypeFromDate,
                                     @Part("to_date") RequestBody uploadTypeToDate,
                                     @Part("page_number") RequestBody pageNumber,
                                     @Part("page_size") RequestBody pageSize,
                                     @Part("filter_visit_no") RequestBody filter_enquiry_no,
                                     @Part("filter_customer_name") RequestBody filter_customer_name,
                                     @Part("filter_topic") RequestBody filter_topic,
                                     @Part("filter_visit_result") RequestBody filter_visit_result,
                                     @Part("filter_rm") RequestBody filter_rm,
                                     @Part("filter_tour") RequestBody filter_tour);

    @Multipart
    @POST()
    Observable<JsonElement> getEnquiry(@Url String url,
                                       @Part("action") RequestBody uploadType,
                                       @Part("enquiry") RequestBody enquiry,
                                       @Part("company_id") RequestBody uploadTypeCompId,
                                       @Part("employee") RequestBody uploadTypeEmp,
                                       @Part("from_date") RequestBody uploadTypeFromDate,
                                       @Part("to_date") RequestBody uploadTypeToDate,
                                       @Part("page_number") RequestBody pageNumber,
                                       @Part("page_size") RequestBody pageSize,
                                       @Part("filter_enquiry_no") RequestBody filter_enquiry_no,
                                       @Part("filter_customer_name") RequestBody filter_customer_name,
                                       @Part("filter_enquiry_status") RequestBody filter_enquiry_status,
                                       @Part("filter_close_reason") RequestBody filter_close_reason,
                                       @Part("filter_rm") RequestBody filter_rm);
    @Multipart
    @POST()
    Observable<JsonElement> getLostApportunity(@Url String url,
                                       @Part("action") RequestBody uploadType,
                                       @Part("company_id") RequestBody uploadTypeCompId,
                                       @Part("from_date") RequestBody uploadTypeFromDate,
                                       @Part("to_date") RequestBody uploadTypeToDate,
                                       @Part("page_number") RequestBody pageNumber,
                                       @Part("page_size") RequestBody pageSize,
                                       @Part("filter_customer_name") RequestBody filter_customer_name,
                                       @Part("filter_reason") RequestBody filter_reason,
                                       @Part("filter_rm") RequestBody filter_rm);

    @Multipart
    @POST()
    Observable<JsonElement> getView360(@Url String url,
                                               @Part("action") RequestBody uploadType,
                                               @Part("company_id") RequestBody uploadTypeCompId,
                                               @Part("employee_id") RequestBody uploadTypeFromDate,
                                               @Part("cust_id") RequestBody uploadTypeToDate,
                                               @Part("page_size") RequestBody pageSize,
                                               @Part("page_number") RequestBody pageNumber);


    @Multipart
    @POST()
    Observable<JsonElement> getSalesCredit(@Url String url,
                                       @Part("action") RequestBody uploadType,
                                       @Part("isAdmin") RequestBody isAdmin,
                                       @Part("company_id") RequestBody uploadTypeCompId,
                                       @Part("employee_id") RequestBody uploadTypeEmp,
                                       @Part("page_number") RequestBody pageNumber,
                                       @Part("page_size") RequestBody pageSize,
                                       @Part("filter_customer_name") RequestBody filter_customer_name,
                                       @Part("filter_rm") RequestBody filter_rm);



    @POST()
    Observable<JsonElement> vehicleNo(@Url String url, @Query("jsonreq") String request);

    @POST()
    Observable<JsonElement> plant(@Url String url, @Query("jsonreq") String request);

    @POST()
    Observable<JsonElement> search(@Url String url, @Query("jsonreq") String request);

    @POST()
    Observable<JsonElement> getVehicleList(@Url String url, @Query("jsonreq") String request);

    @POST()
    Observable<JsonElement> VehicleDetails(@Url String url, @Query("jsonreq") String request);


    /* @POST()
     Observable<JsonElement> checkLrNo(@Url String url,@Query("jsonreq")  String request);
 */
    @POST()
    Call<CheckLrResponse> checkLrNo(@Url String url, @Query("jsonreq") String request);


    @Multipart
    @POST()
    Call<UploadVehicleRecordRespoonse> uploadVehicleRecord(@Url String url,
                                                           @Part("action") RequestBody uploadType,
                                                           @Part("jsonreq") RequestBody uploadTypeImage);

    //

    @POST()
    Observable<JsonElement> fetchKataChitthi(@Url String url, @Query("jsonreq") String request);

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
