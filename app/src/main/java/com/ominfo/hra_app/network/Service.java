package com.ominfo.hra_app.network;
import com.google.gson.JsonElement;
import com.ominfo.hra_app.ui.attendance.model.LocationPerHourRequest;
import com.ominfo.hra_app.ui.attendance.model.MarkAttendanceRequest;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceRequest;
import com.ominfo.hra_app.ui.dashboard.model.DashboardRequest;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectListRequest;
import com.ominfo.hra_app.ui.leave.model.LeaveStatusRequest;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListRequest;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.hra_app.ui.my_account.model.GetTicketRequest;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationRequest;
import com.ominfo.hra_app.ui.my_account.model.ProfileRequest;
import com.ominfo.hra_app.ui.my_account.model.RaiseTicketRequest;
import com.ominfo.hra_app.ui.my_account.model.UpdateTicketRequest;
import com.ominfo.hra_app.ui.registration.model.RegistrationRequest;
import com.ominfo.hra_app.ui.sales_credit.model.GetView360Request;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditRequest;
import com.ominfo.hra_app.ui.top_customer.model.TopCustomerRequest;
import com.ominfo.hra_app.ui.visit_report.model.AddVisitRequest;
import com.ominfo.hra_app.ui.visit_report.model.GetVisitRequest;
import com.ominfo.hra_app.ui.visit_report.model.PhpEditVisitRequest;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class Service {

    private NetworkAPIServices networkAPIServices;

    public Service(NetworkAPIServices networkAPIServices) {
        this.networkAPIServices = networkAPIServices;
    }
    public Observable<JsonElement> executeRegisterAPI(RegistrationRequest request) {
        return networkAPIServices.registration(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_REGISTRATION),
                request.getAction(),request.getName(),request.getAddress(),
                request.getPincode(),request.getContactNo(),request.getEmailId(),
                request.getStaffStrength(),request.getUserPrefix());
    }
    public Observable<JsonElement> executeCheckPrefixAPI(RequestBody action, RequestBody prefix) {
        return networkAPIServices.checkPrefix(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_CHECK_PREFIX),
                action,prefix);
    }
    public Observable<JsonElement> executeApplyCouponAPI(RequestBody action, RequestBody prefix) {
        return networkAPIServices.applyCoupon(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_CHECK_COUPON),
                action,prefix);
    }
    public Observable<JsonElement> executeSubscriptionAPI(RequestBody action, RequestBody start, RequestBody end) {
        return networkAPIServices.subCharges(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_SUB_CHARGES),
                action,start,end);
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

    public Observable<JsonElement> executeGetCompanyAPI(RequestBody action,RequestBody comId,
                                                        RequestBody pageNo,RequestBody pageS) {
        return networkAPIServices.getCompany(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_COMPANY),
                action,comId,pageNo,pageS
        );
    }

    public Observable<JsonElement> executeGetTicketNoAPI(RequestBody action) {
        return networkAPIServices.getTicketNo(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_COMPANY),
                action);
    }

    public Observable<JsonElement> executeRaiseTicketAPI(RaiseTicketRequest request) {
        return networkAPIServices.raiseTicket(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_RAISE_TICKET),
                request.getAction(), request.getCustId(), request.getSubject(),
                request.getDescription(), request.getPriority(), request.getIssueType(),request.getTicketNo()
        );
    }
    public Observable<JsonElement> executeUpdateTicketAPI(UpdateTicketRequest request) {
        return networkAPIServices.updateTicket(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_UPDATE_TICKET),
                request.getAction(), request.getSubject(), request.getDescription(),
                request.getPriority(), request.getIssueType(),request.getStatus(),request.getReason()
                ,request.getTicket_no()
        );
    }
    public Observable<JsonElement> executeMarkAttendanceAPI(MarkAttendanceRequest markAttendanceRequest) {
        return networkAPIServices.markAttendance(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_MARK_ATTENDANCE),
                markAttendanceRequest.getAction(), markAttendanceRequest.getEmpId(), markAttendanceRequest.getDate(),
                markAttendanceRequest.getStartTime(), markAttendanceRequest.getStartLatitude(), markAttendanceRequest.getStartLongitude()
        );
    }
    public Observable<JsonElement> executeUpdateAttendanceAPI(UpdateAttendanceRequest updateAttendanceRequest) {
        return networkAPIServices.updateAttendance(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_UPDATE_ATTENDANCE),
                updateAttendanceRequest.getAction()/*, updateAttendanceRequest.getStartTime()*/, updateAttendanceRequest.getEndTime(),
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

    public Observable<JsonElement> executeAddVisitAPI(AddVisitRequest request) {
        return networkAPIServices.addVisit(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_ADD_VISIT),
                request.getAction(),request.getVisitNo(),request.getCompanyId(),
                request.getStartLocationName(),request.getStartLocationAddress(),
                request.getStartLocationLatitude(),request.getStartLocationLongitute());
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

    public Observable<JsonElement> executeGetBirthDayListAPI(RequestBody action,RequestBody mon) {
        return networkAPIServices.getBirthDayList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_BIRTH_DAY_LIST),
                action,mon
               );
    }
    public Observable<JsonElement> executeCalenderHolidaysListAPI(RequestBody action,RequestBody cId,RequestBody from
            ,RequestBody to) {
        return networkAPIServices.calenderHolidays(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_CALENDER_HOLIDAY),
                action,cId,from,to
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

    public Observable<JsonElement> executeLeaveCountAPI(RequestBody action, RequestBody empId) {
        return networkAPIServices.getLeaveCount(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_LEAVE_COUNT),
                action,
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
                DynamicAPIPath.POST_CHANGE_PROFILE),
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

    public Observable<JsonElement> executeEmployeeListAPI(EmployeeListRequest employeeListRequest) {
        return networkAPIServices.employeeList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_EMPLOYEES_LIST),
                employeeListRequest.getAction(),employeeListRequest.getCompanyId(),
                employeeListRequest.getEmployee(),employeeListRequest.getToken(),
                employeeListRequest.getPageNumber(),employeeListRequest.getPageSize(),
                employeeListRequest.getFilterEmpName(),employeeListRequest.getFilterEmpPosition(),
                employeeListRequest.getFilterEmpIsActive()
        );
    }
    public Observable<JsonElement> executeAllSalaryListAPI(RequestBody action,
             RequestBody isAd, RequestBody comId, RequestBody empId,RequestBody pageNo, RequestBody pageS) {
        return networkAPIServices.salaryAllList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_SALARY_ALL_LIST),
                action,isAd,comId,empId,
                pageNo,pageS
        );
    }
    public Observable<JsonElement> executeSalarySheetAPI(RequestBody action,
                                                          RequestBody empId,RequestBody pageNo, RequestBody pageS) {
        return networkAPIServices.salarySheet(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_SALARY_ALL_LIST),
                action,empId,
                pageNo,pageS
        );
    }
    public Observable<JsonElement> executePastLeaveListAPI(PastLeaveListRequest request) {
        return networkAPIServices.getPastLeaveList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_GET_PAST_LEAVE),
                request.getAction(),request.getEmpId(),
                request.getMonth(),request.getPageNo(),request.getPageSize()
        );
    }

    public Observable<JsonElement> executeAcceptRejectListAPI(AcceptRejectListRequest request) {
        return networkAPIServices.acceptRejectList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_ACCEPT_REJECT_LIST),
                request.getAction(),request.getEmpId(),
                request.getLeaveType(),request.getFromDate(),
                request.getEndDate(),request.getPageNo(),request.getPageSize()
                ,request.getSearchedEmp()
        );
    }
    public Observable<JsonElement> executeLeaveStatusAPI(LeaveStatusRequest request) {
        return networkAPIServices.leaveStatus(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_LEAVE_STATUS),
                request.getAction(),request.getId(),
                request.getLeaveStatus(),request.getUpdatedBy()
        );
    }
    public Observable<JsonElement> executeDeactivateEmployeeAPI(RequestBody action,RequestBody updateby,RequestBody empId) {
        return networkAPIServices.deactivateEmployee(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_DEACT_EMPLOYEE),
                action,updateby,empId
        );
    }
    public Observable<JsonElement> executeAddEmployeeAPI(AddEmployeeRequest addEmployeeRequest) {
        return networkAPIServices.addEmployee(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_ADD_EMPLOYEES),
                addEmployeeRequest.getAction(), addEmployeeRequest.getEmpName(),
                addEmployeeRequest.getEmpMob(), addEmployeeRequest.getEmpEmail(),
                addEmployeeRequest.getEmpAddr(), addEmployeeRequest.getEmpDob(),
                addEmployeeRequest.getEmpGender(), addEmployeeRequest.getEmpPincode(),
                addEmployeeRequest.getEmpPosition(), addEmployeeRequest.getCreatedBy(),
                addEmployeeRequest.getCompanyID(), addEmployeeRequest.getToken(),
                addEmployeeRequest.getSalary(), addEmployeeRequest.getOtherLeaves(),
                addEmployeeRequest.getCasualLeaves(), addEmployeeRequest.getSickLeaves()
                ,addEmployeeRequest.getJoiningDate() ,addEmployeeRequest.getOfficeAddress()
                ,addEmployeeRequest.getOfficeLongitude() ,addEmployeeRequest.getOfficeLatitude()
                ,addEmployeeRequest.getMonWorking() ,addEmployeeRequest.getTueWorking()
                ,addEmployeeRequest.getWedWorking() ,addEmployeeRequest.getThursWorking()
                ,addEmployeeRequest.getFri_working() ,addEmployeeRequest.getSatWorking()
                ,addEmployeeRequest.getSunWorking() ,addEmployeeRequest.getMonStartTime()
                ,addEmployeeRequest.getTueStartTime() ,addEmployeeRequest.getWedStartTime()
                ,addEmployeeRequest.getThursStartTime() ,addEmployeeRequest.getFri_start_time()
                ,addEmployeeRequest.getSatStartTime() ,addEmployeeRequest.getSunStartTime()
                ,addEmployeeRequest.getMonEndTime() ,addEmployeeRequest.getTueEndTime()
                ,addEmployeeRequest.getWedEndTime() ,addEmployeeRequest.getThursEndTime()
                ,addEmployeeRequest.getFri_end_time() ,addEmployeeRequest.getSatEndTime()
                 ,addEmployeeRequest.getSunEndTime()
        );
    }
    public Observable<JsonElement> executeEditEmployeeAPI(EditEmployeeRequest addEmployeeRequest) {
        return networkAPIServices.editEmployee(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_EDIT_EMPLOYEES),
                addEmployeeRequest.getAction(), addEmployeeRequest.getEmpName(),
                addEmployeeRequest.getEmpMob(), addEmployeeRequest.getEmpEmail(),
                addEmployeeRequest.getEmpAddr(), addEmployeeRequest.getEmpDob(),
                addEmployeeRequest.getEmpGender(), addEmployeeRequest.getEmpPincode(),
                addEmployeeRequest.getEmpPosition(), addEmployeeRequest.getUpdatedBy(),
                addEmployeeRequest.getCompanyID(), addEmployeeRequest.getEmpId(),
                addEmployeeRequest.getSalary(), addEmployeeRequest.getOtherLeaves(),
                addEmployeeRequest.getCasualLeaves(), addEmployeeRequest.getSickLeaves()
                ,addEmployeeRequest.getJoiningDate(),addEmployeeRequest.getToken()
                        ,addEmployeeRequest.getOfficeAddress()
                        ,addEmployeeRequest.getOfficeLongitude() ,addEmployeeRequest.getOfficeLatitude()
                        ,addEmployeeRequest.getMonWorking() ,addEmployeeRequest.getTueWorking()
                        ,addEmployeeRequest.getWedWorking() ,addEmployeeRequest.getThursWorking()
                        ,addEmployeeRequest.getFri_working() ,addEmployeeRequest.getSatWorking()
                        ,addEmployeeRequest.getSunWorking() ,addEmployeeRequest.getMonStartTime()
                        ,addEmployeeRequest.getTueStartTime() ,addEmployeeRequest.getWedStartTime()
                        ,addEmployeeRequest.getThursStartTime() ,addEmployeeRequest.getFri_start_time()
                        ,addEmployeeRequest.getSatStartTime() ,addEmployeeRequest.getSunStartTime()
                        ,addEmployeeRequest.getMonEndTime() ,addEmployeeRequest.getTueEndTime()
                        ,addEmployeeRequest.getWedEndTime() ,addEmployeeRequest.getThursEndTime()
                        ,addEmployeeRequest.getFri_end_time() ,addEmployeeRequest.getSatEndTime()
                        ,addEmployeeRequest.getSunEndTime()
        );
    }

    public Observable<JsonElement> executeGetTopCustomerAPI(TopCustomerRequest topCustomerRequest) {
        return networkAPIServices.getTopCustomer(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_TOP_CUST),
                topCustomerRequest.getAction(),topCustomerRequest.getPageNo(),
                topCustomerRequest.getPageSize(),topCustomerRequest.getRmId(),
                topCustomerRequest.getCustName(), topCustomerRequest.getInvMax(), topCustomerRequest.getInvMin(),
                topCustomerRequest.getSaleMinAmt(), topCustomerRequest.getSaleMaxAmt(), topCustomerRequest.getFromDate(),
                topCustomerRequest.getEndDate());
    }

    public Observable<JsonElement> executeGetView360API(GetView360Request getView360Request) {
        return networkAPIServices.getView360(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LEAVE_COUNT),
                getView360Request.getAction(),
                getView360Request.getCompanyId(),
                getView360Request.getEmployee(), getView360Request.getCustId(),
                getView360Request.getPageSize(),getView360Request.getPageNumber()
        );
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
    public Observable<JsonElement> executeGetTicketAPI(GetTicketRequest getTicketRequest) {
        return networkAPIServices.getTicket(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_TICKET),
                getTicketRequest.getAction(),getTicketRequest.getEmpId(),
                getTicketRequest.getPageno(),getTicketRequest.getPagesize(),
                getTicketRequest.getTicketNo()/*, getTicketRequest.getPriority()*/,getTicketRequest.getStatus(),
                getTicketRequest.getFromDate(),getTicketRequest.getEndDate());
    }
    public Observable<JsonElement> executeLeaveSingleRecordAPI(RequestBody action, RequestBody id) {
        return networkAPIServices.getLeaveSingle(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_LEAVE_APP),
                action,id);
    }
    public Observable<JsonElement> executeLeaveStatusAPI(RequestBody action, RequestBody id,RequestBody leave_status,
                                                         RequestBody updated_by) {
        return networkAPIServices.getLeaveStatusUpdate(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_LEAVE_STATUS),
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
        return networkAPIServices.search(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_EMPLOYEES_LIST),request);
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
