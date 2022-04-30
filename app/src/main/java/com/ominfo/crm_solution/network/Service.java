package com.ominfo.crm_solution.network;
import com.google.gson.JsonElement;
import com.ominfo.crm_solution.ui.attendance.model.LocationPerHourRequest;
import com.ominfo.crm_solution.ui.attendance.model.MarkAttendanceRequest;
import com.ominfo.crm_solution.ui.attendance.model.UpdateAttendanceRequest;
import com.ominfo.crm_solution.ui.dashboard.model.DashboardRequest;
import com.ominfo.crm_solution.ui.dispatch_pending.model.DispatchRequest;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiryRequest;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryRequest;
import com.ominfo.crm_solution.ui.lost_apportunity.model.GetLostApportunityRequest;
import com.ominfo.crm_solution.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.crm_solution.ui.my_account.model.LeaveApplicationRequest;
import com.ominfo.crm_solution.ui.my_account.model.ProfileRequest;
import com.ominfo.crm_solution.ui.product.model.ProductRequest;
import com.ominfo.crm_solution.ui.quotation_amount.model.QuotationRequest;
import com.ominfo.crm_solution.ui.receipt.model.ReceiptRequest;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderRequest;
import com.ominfo.crm_solution.ui.reminders.model.EmployeeListRequest;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListRequest;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderRequest;
import com.ominfo.crm_solution.ui.sale.model.SalesRequest;
import com.ominfo.crm_solution.ui.sales_credit.model.GetView360Request;
import com.ominfo.crm_solution.ui.sales_credit.model.SalesCreditRequest;
import com.ominfo.crm_solution.ui.top_customer.model.TopCustomerRequest;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.EditVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.PhpEditVisitRequest;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class Service {

    private NetworkAPIServices networkAPIServices;

    public Service(NetworkAPIServices networkAPIServices) {
        this.networkAPIServices = networkAPIServices;
    }

    public Observable<JsonElement> executeLoginAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyType1,RequestBody mRequestBodyType2,RequestBody mRequestBodyToken) {
        return networkAPIServices.login(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LOGIN),mRequestBodyType,mRequestBodyType1,mRequestBodyType2,mRequestBodyToken);
    }

    public Observable<JsonElement> executeLogoutAPI(RequestBody action,RequestBody id) {
        return networkAPIServices.logout(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LOGOUT),action,id);
    }

    public Observable<JsonElement> executeApplyLeaveAPI(ApplyLeaveRequest applyLeaveRequest) {
        return networkAPIServices.applyLeave(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_APPLY_LEAVE),
                applyLeaveRequest.getAction(), applyLeaveRequest.getEmpId(), applyLeaveRequest.getDuration(),
                applyLeaveRequest.getStartTime(), applyLeaveRequest.getEndTime(), applyLeaveRequest.getLeaveType(),
                applyLeaveRequest.getComment()/*, applyLeaveRequest.getLeaveStatus(), applyLeaveRequest.getUpdatedBy()*/
                );
    }
    public Observable<JsonElement> executeRaiseTicketAPI(RequestBody action) {
        return networkAPIServices.raiseTicket(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_RAISE_TICKET),
                action);
    }
    public Observable<JsonElement> executeMarkAttendanceAPI(MarkAttendanceRequest markAttendanceRequest) {
        return networkAPIServices.markAttendance(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_APPLY_LEAVE),
                markAttendanceRequest.getAction(), markAttendanceRequest.getEmpId(), markAttendanceRequest.getDate(),
                markAttendanceRequest.getStartTime(), markAttendanceRequest.getStartLatitude(), markAttendanceRequest.getStartLongitude()
        );
    }
    public Observable<JsonElement> executeUpdateAttendanceAPI(UpdateAttendanceRequest updateAttendanceRequest) {
        return networkAPIServices.updateAttendance(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_UPDATE_ATTENDANCE),
                updateAttendanceRequest.getAction(), updateAttendanceRequest.getStartTime(), updateAttendanceRequest.getEndTime(),
                updateAttendanceRequest.getId(), updateAttendanceRequest.getEndLatitude(), updateAttendanceRequest.getEndLongitude()
        );
    }
    public Observable<JsonElement> executeLocationPerHourAPI(LocationPerHourRequest locationPerHourRequest) {
        return networkAPIServices.locationPerHour(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LOCATION_PER_HOUR),
                locationPerHourRequest.getAction(), locationPerHourRequest.getEmpId(), locationPerHourRequest.getDate(),
                locationPerHourRequest.getLatitude(), locationPerHourRequest.getLongitude(),  locationPerHourRequest.getStartTime()
                , locationPerHourRequest.getRequestedToken());
    }
    public Observable<JsonElement> executeProfileAPI(ProfileRequest profileRequest) {
        return networkAPIServices.profile(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_PROFILE),profileRequest);
    }

    public Observable<JsonElement> executeSalesAPI(SalesRequest salesRequest) {
        return networkAPIServices.sales(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_SALES),salesRequest);
    }

    public Observable<JsonElement> executeQuotationAPI(QuotationRequest request) {
        return networkAPIServices.quotation(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_QUOTATION),request);
    }

 /*   public Observable<JsonElement> executeLostApportunityAPI(GetLostApportunityRequest request) {
        return networkAPIServices.lostApportunity(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LOST_APPORTUNITY),request);
    }*/

    public Observable<JsonElement> executeDispatchAPI(DispatchRequest request) {
        return networkAPIServices.dispatch(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_DISPATCH),request);
    }

    public Observable<JsonElement> executeProductAPI(ProductRequest request) {
        return networkAPIServices.product(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_PRODUCT),
                request.getAction(),request.getPageno(),request.getPagesize(),request.getProdCode()
                ,request.getProdName());
    }

    public Observable<JsonElement> executeReminderListAPI(ReminderListRequest request) {
        return networkAPIServices.reminderList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_REMINDER),request);
    }

    public Observable<JsonElement> executeEmployeeListAPI(EmployeeListRequest request) {
        return networkAPIServices.empList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_EMP_LIST),request);
    }

    public Observable<JsonElement> executeAddReminderAPI(AddReminderRequest request) {
        return networkAPIServices.addReminder(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_ADD_REMINDER),request);
    }

    public Observable<JsonElement> executeUpdateReminderAPI(UpdateReminderRequest request) {
        return networkAPIServices.updateReminder(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_UPDATE_REMINDER),request);
    }

    public Observable<JsonElement> executeReceiptAPI(ReceiptRequest request) {
        return networkAPIServices.receipt(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_RECEIPT),
                request.getAction(),request.getStartDate(),request.getEndDate(),
                request.getMinAmount(),request.getMaxAmount(),request.getPageno(),
                request.getPagesize(),request.getCustName(),request.getTicketNo());
    }

    public Observable<JsonElement> executeAddVisitAPI(AddVisitRequest request) {
        return networkAPIServices.addVisit(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_ADD_VISIT),request);
    }

    public Observable<JsonElement> executeEditVisitAPI(PhpEditVisitRequest request) {
        return networkAPIServices.editVisit(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_EDIT_VISIT),
                request.getAction(),request.getCompanyID(),
                request.getEmployee(),request.getVisitNo(),
                request.getVisitTimeEnd(), request.getRmId(),
                request.getPlace(), request.getCustName(),
                request.getCustMobile(),request.getVisitingCard(),
                request.getTopic(),request.getResult(),
                request.getDescription(),request.getVisitDuration(),
                request.getVisitLocationName(),request.getVisitLocationAddress(),
                request.getVisitLocationLatitude(),request.getVisitLocationLongitute()
                ,request.getStopLocationName(),request.getStopLocationAddress(),
                request.getStopLocationLatitude(),request.getStopLocationLongitute()
                ,request.getTourId());

    }

    public Observable<JsonElement> executeGetRMAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyType1,RequestBody mRequestBodyType2) {
        return networkAPIServices.getRM(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_RM),mRequestBodyType,mRequestBodyType1,mRequestBodyType2);
    }

    public Observable<JsonElement> executesaveEnquiryAPI(SaveEnquiryRequest saveEnquiryRequest) {
        return networkAPIServices.saveEnquiry(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_SAVE_ENQUIRY),
                saveEnquiryRequest.getRequestBodyTypeAction(),saveEnquiryRequest.getRequestBodyTypeEnquiry(),
                saveEnquiryRequest.getRequestBodyTypeCustID(),saveEnquiryRequest.getRequestBodyTypeCustName(),
                saveEnquiryRequest.getRequestBodyTypeCustMobile(), saveEnquiryRequest.getRequestBodyTypeProduct(),
                saveEnquiryRequest.getRequestBodyTypeRm(), saveEnquiryRequest.getRequestBodyTypeDescription(),
                saveEnquiryRequest.getRequestBodyTypeSource(),saveEnquiryRequest.getRequestBodyTypeCompID(),
                saveEnquiryRequest.getRequestBodyTypeEmpID());
    }

    public Observable<JsonElement> executeDashboardAPI(DashboardRequest dashboardRequest) {
        return networkAPIServices.getDashboard(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_GET_DASHBOARD),
                dashboardRequest.getAction(),
                dashboardRequest.getEmployee(),
                dashboardRequest.getCompanyId(),
                dashboardRequest.getStartDate(),
                dashboardRequest.getEndDate()
               );
    }

    public Observable<JsonElement> executeGetProfileImageAPI(RequestBody action,
                                                             RequestBody comId, RequestBody empId) {
        return networkAPIServices.getProfileImage(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_GET_PROFILE),
                action,
                comId,
                empId
        );
    }

    public Observable<JsonElement> executeNotificationAPI(RequestBody action,
                                                             RequestBody comId, RequestBody empId) {
        return networkAPIServices.getNotification(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_NOTIFICATION),
                action,
                comId,
                empId
        );
    }

    public Observable<JsonElement> executeDeleteNotificationAPI(RequestBody action,
                                                          RequestBody comId,
                                                          RequestBody empId,
                                                          RequestBody notif_id) {
        return networkAPIServices.deleteNotification(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_NOTIFICATION),
                action,
                comId,
                empId,
                notif_id
        );
    }

    public Observable<JsonElement> executeChangeProfileImageAPI(RequestBody action,
                                                                RequestBody comId, RequestBody empId,
                                                                RequestBody image) {
        return networkAPIServices.changeProfileImage(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_GET_PROFILE),
                action,
                comId,
                empId,image
        );
    }

    public Observable<JsonElement> executeChangePasswordAPI(RequestBody action,
                                                                RequestBody comId, RequestBody empId,
                                                                RequestBody pass,RequestBody oldPass) {
        return networkAPIServices.changePassword(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_GET_PROFILE),
                action,
                comId,
                empId,
                pass,
                oldPass
        );
    }

    public Observable<JsonElement> executeSearchCrmAPI(RequestBody action,
                                                            RequestBody comId, RequestBody empId,
                                                            RequestBody search) {
        return networkAPIServices.searchCrm(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_SEARCH_CRM),
                action,
                comId,
                empId,
                search
        );
    }

    public Observable<JsonElement> executeGetEnquiryAPI(GetEnquiryRequest getEnquiryRequest) {
        return networkAPIServices.getEnquiry(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_ENQUIRY),
                getEnquiryRequest.getAction(),getEnquiryRequest.getEnquiry(),
                getEnquiryRequest.getCompanyId(),getEnquiryRequest.getEmployee(),
                getEnquiryRequest.getFromDate(), getEnquiryRequest.getToDate(),
                getEnquiryRequest.getPageNumber(), getEnquiryRequest.getPageSize(),
                getEnquiryRequest.getFilterEnquiryNo(), getEnquiryRequest.getFilterCustomerName(),
                getEnquiryRequest.getFilterEnquiryStatus(), getEnquiryRequest.getFilterCloseReason(),
                getEnquiryRequest.getFilterRm());
    }

    public Observable<JsonElement> executeGetTopCustomerAPI(TopCustomerRequest topCustomerRequest) {
        return networkAPIServices.getTopCustomer(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_TOP_CUST),
                topCustomerRequest.getAction(),topCustomerRequest.getPageNo(),
                topCustomerRequest.getPageSize(),topCustomerRequest.getRmId(),
                topCustomerRequest.getCustName());
    }

    public Observable<JsonElement> executeGetView360API(GetView360Request getView360Request) {
        return networkAPIServices.getView360(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_VIEW360),
                getView360Request.getAction(),
                getView360Request.getCompanyId(),
                getView360Request.getEmployee(), getView360Request.getCustId(),
                getView360Request.getPageSize(),getView360Request.getPageNumber()
        );
    }

    public Observable<JsonElement> executeGetLostApportunityAPI(GetLostApportunityRequest getLostApportunityRequest) {
        return networkAPIServices.getLostApportunity(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LOST_APPORTUNITY),
                getLostApportunityRequest.getAction(),
                getLostApportunityRequest.getCompanyId(),
                getLostApportunityRequest.getFromDate(), getLostApportunityRequest.getToDate(),
                getLostApportunityRequest.getPageNumber(), getLostApportunityRequest.getPageSize(),
                getLostApportunityRequest.getFilterCustomerName(),
                getLostApportunityRequest.getFilterReason(),
                getLostApportunityRequest.getFilterRm());
    }


    public Observable<JsonElement> executeGetSalesCreditAPI(SalesCreditRequest salesCreditRequest) {
        return networkAPIServices.getSalesCredit(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_SALES_CREDIT),
                salesCreditRequest.getAction(),salesCreditRequest.getIsAdmin(),
                salesCreditRequest.getCompanyId(),salesCreditRequest.getEmployeeId(),
                salesCreditRequest.getPageNumber(), salesCreditRequest.getPageSize(),
                salesCreditRequest.getFilterCustomerName(),
                salesCreditRequest.getFilterRm());
    }
    public Observable<JsonElement> executeGetLeaveAppAPI(LeaveApplicationRequest leaveApplicationRequest) {
        return networkAPIServices.getLeaveApplication(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_LEAVE_APP),
                leaveApplicationRequest.getAction(),leaveApplicationRequest.getEmpId(),
                leaveApplicationRequest.getPageno(),leaveApplicationRequest.getPagesize(),
                leaveApplicationRequest.getLeaveType(),leaveApplicationRequest.getStatus(),
                leaveApplicationRequest.getFromDate(),leaveApplicationRequest.getEndDate());
    }
    public Observable<JsonElement> executeLeaveSingleRecordAPI(RequestBody action, RequestBody id) {
        return networkAPIServices.getLeaveSingle(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_LEAVE_APP),
                action,id);
    }
    public Observable<JsonElement> executeLeaveStatusAPI(RequestBody action, RequestBody id,RequestBody leave_status,
                                                         RequestBody updated_by) {
        return networkAPIServices.getLeaveStatusUpdate(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_LEAVE_STATUS),
                action,id,leave_status,updated_by);
    }
    public Observable<JsonElement> executeGetVisitAPI(GetVisitRequest getVisitRequest) {
        return networkAPIServices.getVisit(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_VISIT),
                getVisitRequest.getAction(),getVisitRequest.getVisit(),
                getVisitRequest.getCompanyId(),getVisitRequest.getEmployee(),
                getVisitRequest.getFromDate(), getVisitRequest.getToDate(),
                getVisitRequest.getPageNumber(), getVisitRequest.getPageSize(),
                getVisitRequest.getFilterVisitNo(), getVisitRequest.getFilterCustomerName(),
                getVisitRequest.getFilterTopic(), getVisitRequest.getFilterVisitResult(),
                getVisitRequest.getFilterRm(),getVisitRequest.getFilterTour());
    }

    public Observable<JsonElement> executeSearchCustAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyType1,RequestBody mRequestBodyType2,RequestBody mRequestBodyType3) {
        return networkAPIServices.searchCust(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_SEARCH_CUST),mRequestBodyType,mRequestBodyType1,mRequestBodyType2,mRequestBodyType3);
    }

    public Observable<JsonElement> executeEnquiryStatusAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyTypeStatus) {
        return networkAPIServices.enquiryStatus(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_ENQUIRY_STATUS),mRequestBodyType,mRequestBodyTypeStatus);
    }

    public Observable<JsonElement> executeGetTourAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyTypeStatus) {
        return networkAPIServices.getTour(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_TOUR),mRequestBodyType,mRequestBodyTypeStatus);
    }

    public Observable<JsonElement> executeGetVisitNoAPI(RequestBody mRequestBodyType,RequestBody mRequestBodyTypeStatus) {
        return networkAPIServices.enquiryStatus(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_ENQUIRY_STATUS),mRequestBodyType,mRequestBodyTypeStatus);
    }

    public Observable<JsonElement> executeVehicleNoAPI() {
        return networkAPIServices.getVisitNo(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_VISIT_NO));
    }
    public Observable<JsonElement> executePlantAPI(String request) {
        return networkAPIServices.plant(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_PLANT),request);
    }

    public Observable<JsonElement> executeSearchAPI(String request) {
        return networkAPIServices.search(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_SEARCH_CRM),request);
    }

    public Observable<JsonElement> executeGetVehicleAPI(String request) {
        return networkAPIServices.getVehicleList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_VEHICLE),request);
    }

    public Observable<JsonElement> executeVehicleDetailsAPI(String request) {
        return networkAPIServices.VehicleDetails(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_VEHICLE_DETAILS),request);
    }

   /* public Observable<JsonElement> executeCEWBDetailsAPI(String request) {
        return networkAPIServices.cewbDetails(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_CEWB_DETAILS),request);
    }*/

    /*public Observable<JsonElement> executeUploadVehicleAPI( RequestBody mRequestBodyType, RequestBody mRequestBodyTypeImage) {
        return networkAPIServices.uploadVehicleRecord(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_UPLOAD_VEHICLE),mRequestBodyType,mRequestBodyTypeImage);
    }*/

    //
    public Observable<JsonElement> executeFetchKataChitthiAPI(String request) {
        return networkAPIServices.fetchKataChitthi(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_FETCH_KATA_CHITTI),request);
    }

    public Observable<JsonElement> executeUserListApi(String mLimit) {
        return networkAPIServices.getBookWithTopic(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.GET_USER_LIST+mLimit));
    }

}
