package com.ominfo.hra_app.network;
import com.google.gson.JsonElement;
import com.ominfo.hra_app.ui.attendance.model.LocationPerHourRequest;
import com.ominfo.hra_app.ui.attendance.model.GetAttendanceRequest;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceRequest;
import com.ominfo.hra_app.ui.dashboard.model.AddHolidayRequest;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectListRequest;
import com.ominfo.hra_app.ui.leave.model.LeaveStatusRequest;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListRequest;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.hra_app.ui.my_account.model.EditCompanyRequest;
import com.ominfo.hra_app.ui.my_account.model.GetTicketRequest;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationRequest;
import com.ominfo.hra_app.ui.my_account.model.ProfileRequest;
import com.ominfo.hra_app.ui.my_account.model.RaiseTicketRequest;
import com.ominfo.hra_app.ui.my_account.model.UpdateTicketRequest;
import com.ominfo.hra_app.ui.registration.model.RegistrationRequest;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListRequest;
import com.ominfo.hra_app.ui.salary.model.UpdateSalaryRequest;
import com.ominfo.hra_app.ui.sales_credit.model.GetView360Request;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditRequest;
import com.ominfo.hra_app.ui.top_customer.model.TopCustomerRequest;
import com.ominfo.hra_app.ui.visit_report.model.AddVisitRequest;
import com.ominfo.hra_app.ui.visit_report.model.GetVisitRequest;
import com.ominfo.hra_app.ui.visit_report.model.PhpEditVisitRequest;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class Service {

    private NetworkAPIServices networkAPIServices;

    public Service(NetworkAPIServices networkAPIServices) {
        this.networkAPIServices = networkAPIServices;
    }
    public Observable<JsonElement> executeRegisterAPI(RegistrationRequest request) {
        return networkAPIServices.registration(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_REGISTRATION),
                request.getAction(),request.getName(),request.getAddress(),
                request.getPincode(),request.getContactNo(),request.getEmailId(),
                request.getStaffStrength(),request.getUserPrefix(),request.getGst_percent(),
                request.getSub_charge(),request.getGst_amount(),request.getDiscount_rate(),
                request.getTotal_charge(),request.getCoupon(),request.getPlan_type(),request.getAdmin_name()
                ,request.getGst_no());
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
    public Observable<JsonElement> executeGetAttendanceAPI(GetAttendanceRequest markAttendanceRequest) {
        return networkAPIServices.getAttendance(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_ATTENDANCE),
                markAttendanceRequest.getAction(), markAttendanceRequest.getEmpId(), markAttendanceRequest.getFrm_date(),markAttendanceRequest.getTo_date(),
                markAttendanceRequest.getToken(), markAttendanceRequest.getCompany_ID()
        );
    }
    public Observable<JsonElement> executeUpdateAttendanceAPI(UpdateAttendanceRequest updateAttendanceRequest) {
        return networkAPIServices.updateAttendance(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_UPDATE_ATTENDANCE),
                updateAttendanceRequest.getAction(), updateAttendanceRequest.getEmp_id(),
                updateAttendanceRequest.getDate(),
                updateAttendanceRequest.getStart_time(), updateAttendanceRequest.getStart_longitude(),
                updateAttendanceRequest.getStart_latitude(), updateAttendanceRequest.getOffice_start_addr(),
                updateAttendanceRequest.getIs_late(), updateAttendanceRequest.getOffice_end_addr(),
                updateAttendanceRequest.getEnd_time(), updateAttendanceRequest.getEnd_longitude(),
                updateAttendanceRequest.getEnd_latitude()
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
    public Observable<JsonElement> executeAddHolidayAPI(AddHolidayRequest addHolidayRequest) {
        return networkAPIServices.addHoliday(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_ADD_HOLIDAY),
                addHolidayRequest.getAction(),addHolidayRequest.getCompany_id(),
                addHolidayRequest.getDate(),addHolidayRequest.getName(),
                addHolidayRequest.getDescription()
        );
    }
    public Observable<JsonElement> executeEditHolidayAPI(RequestBody action,RequestBody record_id) {
        return networkAPIServices.editHoliday(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_EDIT_HOLIDAY),
               action,record_id
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

    public Observable<JsonElement> executeLeaveCountAPI(RequestBody action, RequestBody empId
            , RequestBody comId, RequestBody mon) {
        return networkAPIServices.getLeaveCount(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_LEAVE_COUNT),
                action,
                empId,comId,mon
        );
    }

    public Observable<JsonElement> executeDeleteNotificationAPI(RequestBody action,
                                                          RequestBody comId,
                                                          RequestBody empId,
                                                          RequestBody notif_id) {
        return networkAPIServices.deleteNotification(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_DEL_NOTIFICATION),
                action,
                comId,
                empId,
                notif_id
        );
    }

    public Observable<JsonElement> executeLateMarkAPI(RequestBody action,
                                                                RequestBody empId) {
        return networkAPIServices.lateMarkCount(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_LATE_MARK_COUNT),
                action,
                empId
        );
    }

    public Observable<JsonElement> executeAbsentMarkAPI(RequestBody action,
                                                      RequestBody empId) {
        return networkAPIServices.lateMarkCount(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_LATE_ABSENT_COUNT),
                action,
                empId
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
    public Observable<JsonElement> executeAllSalaryListAPI(SalaryAllListRequest salaryAllListRequest) {
        return networkAPIServices.salaryAllList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_SALARY_ALL_LIST),salaryAllListRequest.getAction(),
                salaryAllListRequest.getIsAdmin(),
                salaryAllListRequest.getCompany_ID(),
                salaryAllListRequest.getEmp_id()/*,salaryAllListRequest.getPageNumber(),
                salaryAllListRequest.getPageSize()*/,salaryAllListRequest.getMonth(),salaryAllListRequest.getYear()
        );
    }
    public Observable<JsonElement> executeUpdateSalaryAPI(UpdateSalaryRequest salaryRequest) {
        return networkAPIServices.updateSalary(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_UPDATE_SALARY),salaryRequest.getAction(),
                salaryRequest.getAddition(),salaryRequest.getTotal(),salaryRequest.getRemark(),
                salaryRequest.getEmp_id(),salaryRequest.getDeduction(),salaryRequest.getYear(),
                salaryRequest.getMonth()

        );
    }
    public Observable<JsonElement> executeMarkPresentAPI(RequestBody act,RequestBody id) {
        return networkAPIServices.markPresent(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_MARK_PRESENT),act,id
        );
    }
    public Observable<JsonElement> executeMarkNotLateAPI(RequestBody act,RequestBody id) {
        return networkAPIServices.markPresent(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_MARK_NOT_LATE),act,id
        );
    }
    public Observable<JsonElement> executeDeductLeaveAPI(RequestBody act,RequestBody emp_id
            ,RequestBody date,RequestBody status,RequestBody leave_type,RequestBody leave_days) {
        return networkAPIServices.deductLeave(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_MARK_NOT_LATE),act,emp_id,date,status,leave_type,leave_days
        );
    }
    public Observable<JsonElement> executeSalarySlipAPI(RequestBody act,RequestBody emp_id) {
        return networkAPIServices.salarySlip(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_SALARY_SLIP),act,emp_id
        );
    }
    public Observable<JsonElement> executeUnpaidLeaveAPI(RequestBody act,RequestBody id) {
        return networkAPIServices.markPresent(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_UNPAID_LEAVE),act,id
        );
    }
    public Observable<JsonElement> executeSalaryDisburseAPI(RequestBody act,RequestBody list) {
        return networkAPIServices.salaryDisburse(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_ADD_SALARY),act,list
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
    public Observable<JsonElement> executeActiveEmployeeListAPI(RequestBody action,RequestBody comId,
                                                                RequestBody empId) {
        return networkAPIServices.activeEmployeeList(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_ACTIVE_EMP_LIST),
               action,comId,empId
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

    public Observable<JsonElement> executeEditCompanyAPI(EditCompanyRequest editCompanyRequest) {
        return networkAPIServices.editCompany(DynamicAPIPath.makeDynamicEndpointAPIGateWay("",
                DynamicAPIPath.POST_EDIT_COMPANY),
                editCompanyRequest.getAction(), editCompanyRequest.getCompany_ID(),
                editCompanyRequest.getName(), editCompanyRequest.getContact_no(),
                editCompanyRequest.getEmail_id(), editCompanyRequest.getStaff_strength(),
                editCompanyRequest.getGst_no(), editCompanyRequest.getPincode(),
                editCompanyRequest.getRegistered_address(),
                editCompanyRequest.getOfficeAddress(),editCompanyRequest.getOfficeLatitude(),
                editCompanyRequest.getOfficeLongitude(),
                editCompanyRequest.getMonWorking() ,editCompanyRequest.getTueWorking()
                ,editCompanyRequest.getWedWorking() ,editCompanyRequest.getThursWorking()
                ,editCompanyRequest.getFri_working() ,editCompanyRequest.getSatWorking()
                ,editCompanyRequest.getSunWorking() ,editCompanyRequest.getMonStartTime()
                ,editCompanyRequest.getTueStartTime() ,editCompanyRequest.getWedStartTime()
                ,editCompanyRequest.getThursStartTime() ,editCompanyRequest.getFri_start_time()
                ,editCompanyRequest.getSatStartTime() ,editCompanyRequest.getSunStartTime()
                ,editCompanyRequest.getMonEndTime() ,editCompanyRequest.getTueEndTime()
                ,editCompanyRequest.getWedEndTime() ,editCompanyRequest.getThursEndTime()
                ,editCompanyRequest.getFri_end_time() ,editCompanyRequest.getSatEndTime()
                ,editCompanyRequest.getSunEndTime()
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
    public Observable<JsonElement> executeSingleNotifyDetailsAPI(RequestBody action,
                                                                 RequestBody comId, RequestBody date) {
        return networkAPIServices.getSingleNotifyDetails(DynamicAPIPath.makeDynamicEndpointAPIGateWay("", DynamicAPIPath.POST_GET_SINGLE_NOTIFY),
                action,comId,date);
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



}
