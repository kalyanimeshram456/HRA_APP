package com.ominfo.hra_app.network;

import com.ominfo.hra_app.util.LogUtil;

public class DynamicAPIPath {

    public static final String regenerateCallWithCode = "/profile-services/customers/regenerate-otp-dial";
    public static final String uploadPublicKey = "/shared/encryption-handler/device-key/upload";
    //http://123.252.197.10/elixiatms-om/modules/api/TMSVendor/index_omtrucking.php
    public static final String BASE = "https://ominfo.in/o_hr/OHR_app_api.php";//"index_omtrucking.php"
    public static final String BASE_NODEJS = "http://ominfo.in:3000/";//"index_omtrucking.php"// ;
    public static final String POST_LOGIN = BASE+"?action=login";
    public static final String POST_REGISTRATION= BASE+"?action=add_company";
    public static final String POST_LOGOUT = BASE+"?action=logout";
    public static final String POST_PROFILE= BASE_NODEJS+"getEmployee_profile";
    public static final String POST_CHECK_PREFIX=  BASE+"?action=check_userprefix";
    public static final String POST_SALES= BASE+"?action=get_all_product";
    public static final String POST_SUB_CHARGES= BASE+"?action=get_subs_price";
    public static final String POST_ACCEPT_REJECT_LIST=  BASE+"?action=get_leave_records";
    public static final String POST_LEAVE_STATUS= BASE+"?action=apply_leave_updated";
    public static final String POST_GET_PAST_LEAVE= BASE+"?action=get_record";
    public static final String POST_CALENDER_HOLIDAY=BASE+"?action=get_company_holiday";
    public static final String POST_ACTIVE_EMP_LIST=BASE+"?action=get_emp_list";
    public static final String POST_UPDATE_REMINDER= BASE_NODEJS+"updatereminder";
    public static final String POST_RECEIPT= BASE+"?action=get_receipt_records";
    public static final String POST_ADD_VISIT= BASE+"?action=add_visit";
    public static final String POST_EDIT_VISIT= BASE+"?action=editVisit";
    public static final String POST_GET_PROFILE = BASE+"?action=getcrmempprofilepic";
    public static final String POST_NOTIFICATION = BASE+"?action=getempnotification";
    public static final String POST_DEL_NOTIFICATION = BASE+"?action=marknotifread";
    public static final String POST_LATE_MARK_COUNT = BASE+"?action=late_mark_count_months";
    public static final String POST_LATE_ABSENT_COUNT = BASE+"?action=absent_mark_count_months";
    public static final String POST_CHECK_COUPON= BASE+"?action=checkcoupon_validity";
    public static final String POST_CHANGE_PROFILE = BASE+"?action=updatecrmempprofilepic";
    public static final String POST_CHANGE_PASS = BASE+"?action=changecrmemppassword";
    public static final String POST_PLANT= BASE+"?action=getBranchList";
    public static final String POST_GET_VEHICLE= BASE+"?action=getVehWiseLRs";
    public static final String POST_VEHICLE_DETAILS= BASE+"?action=getVehWiseLRTransaction";
    public static final String POST_EMPLOYEES_LIST = BASE+"?action=get_employee_list";
    public static final String POST_SALARY_ALL_LIST = BASE+"?action=get_emplist_sal";
    public static final String POST_ADD_SALARY = BASE+"?action=add_sal_disbursment";
    public static final String POST_SALARY_SHEET = BASE+"?action=emp_monthly_sal_crd";
    public static final String POST_GET_RM= BASE+"?action=rmget";
    public static final String POST_UPDATE_SALARY= BASE+"?action=salary_edit";
    public static final String POST_MARK_PRESENT= BASE+"?action=mark_as_persent";
    public static final String POST_DEDUCT_LEAVE= BASE+"?action=leave_deduct";
    public static final String POST_MARK_NOT_LATE= BASE+"?action=mark_as_not_late";
    public static final String POST_SALARY_SLIP = BASE+"?action=emp_sal_slip";
    public static final String POST_UNPAID_LEAVE= BASE+"?action=unpaid_leave";
    public static final String POST_ADD_EMPLOYEES= BASE+"?action=add_employee";
    public static final String POST_EDIT_EMPLOYEES= BASE+"?action=edit_employee";
    public static final String POST_DEACT_EMPLOYEE= BASE+"?action=deactivate_employee";
    public static final String POST_LEAVE_COUNT = BASE+"?action=get_leave_count";
    public static final String POST_GET_SALES_CREDIT= BASE+"?action=salescreditreport";
    public static final String POST_GET_LEAVE_APP= BASE+"?action=get_leave_records";
    public static final String POST_GET_SINGLE_NOTIFY = BASE+"?action=get_late_nofitydetails";
    public static final String POST_EDIT_COMPANY= BASE+"?action=edit_company";
    public static final String POST_GET_PAST_EMP_LIST= BASE+"?action=get_pastleave_record";
    public static final String POST_ENQUIRY_STATUS= BASE+"?action=statusget";
    public static final String POST_GET_TOUR= BASE+"?action=tourget";
    public static final String POST_GET_VISIT_NO= BASE_NODEJS+"generatevisit_id";
    public static final String POST_GET_VISIT= BASE+"?action=visitget";
    public static final String POST_GET_DASHBOARD= BASE+"?action=getDashboard";
    public static final String POST_APPLY_LEAVE = BASE+"?action=apply_leave";
    public static final String POST_GET_COMPANY = BASE+"?action=get_company_details";
    public static final String POST_GET_ATTENDANCE = BASE+"?action=get_today_attendance";
    public static final String POST_UPDATE_ATTENDANCE= BASE+"?action=mark_attendance_new";
    public static final String POST_LOCATION_PER_HOUR= BASE+"?action=emp_long_lati_tracking";
    public static final String POST_TOP_CUST= BASE+"?action=top_customer";
    public static final String POST_BIRTH_DAY_LIST = BASE+"?action=getbirthdaydata";
    public static final String POST_RAISE_TICKET = BASE+"?action=raise_ticket";
    public static final String POST_GET_TICKET = BASE+"?action=get_filterraised_record";
    public static final String POST_UPDATE_TICKET = BASE+"?action=ticket_updated";
    public static final String POST_ADD_HOLIDAY = BASE+"?action=add_company_holiday";
    public static final String POST_EDIT_HOLIDAY = BASE+"?action=update_active_holiday";


    //action string
    public static final String POST_FETCH_KATA_CHITTI = "index_with_DRapp.php?action=getKantaChitthi";
    public static final String action_login = "login";
    public static final String action_get_tour = "tourget";
    public static final String action_logout = "logout";
    public static final String action_add_holiday = "add_company_holiday";
    public static final String action_get_single_notify = "get_late_nofitydetails";
    public static final String action_location_per_hour = "emp_long_lati_tracking";
    public static final String action_add_employee = "add_employee";
    public static final String action_edit_employee = "edit_employee";
    public static final String action_get_attendance = "get_today_attendance";
    public static final String action_update_attendance = "mark_attendance_new";
    public static final String action_get_past_leave = "get_record";
    public static final String action_edit_company = "edit_company";
    public static final String action_get_subs_price = "get_subs_price";
    public static final String action_get_visit = "visitget";
    public static final String action_get_top_customer = "top_customer";
    public static final String action_deactivate_employee = "deactivate_employee";
    public static final String action_check_coupon_validity = "checkcoupon_validity";
    public static final String action_get_leave_app = "get_leave_records";
    public static final String action_update_active_holiday = "update_active_holiday";
    public static final String action_get_view360 = "custOverAllData";
    public static final String action_get_quotation = "get_quotation_record";
    public static final String action_get_sales_credit = "salescreditreport";
    public static final String action_get_dispatch_pend = "dispatchpendingdets";
    public static final String action_unpaid_leave= "unpaid_leave";
    public static final String action_get_profile_img = "getcrmempprofilepic";
    public static final String action_change_profile_img = "updatecrmempprofilepic";
    public static final String action_change_pass = "changecrmemppassword";
    public static final String action_apply_leave = "apply_leave";
    public static final String action_get_company_details = "get_company_details";
    public static final String action_employee_list = "get_employee_list";
    public static final String action_salary_all_list = "get_emplist_sal";
    public static final String action_salary_sheet = "emp_monthly_sal_crd";
    public static final String action_notification = "getempnotification";
    public static final String action_delete_notification = "marknotifread";
    public static final String action_late_mark_count_months = "late_mark_count_months";
    public static final String action_absent_mark_count_months = "absent_mark_count_months";
    public static final String action_add_Salary = "add_sal_disbursment";
    public static final String action_update_Salary = "salary_edit";
    public static final String action_mark_as_persent = "mark_as_persent";
    public static final String action_leave_deduct = "leave_deduct";
    public static final String action_mark_as_not_late = "mark_as_not_late";
    public static final String action_emp_sal_slip = "emp_sal_slip";
    public static final String action_accept_reject_List = "get_leave_records";
    public static final String action_apply_leave_updated = "apply_leave_updated";
    public static final String action_leave_count = "get_leave_count";
    public static final String action_get_active_emp_list = "get_emp_list";
    public static final String action_get_company_holiday = "get_company_holiday";
    public static final String action_get_birth_day = "getbirthdaydata";
    public static final String action_check_prefix = "check_userprefix";
    public static final String action_register = "add_company";


    /**
     * Make dynamic url method
     * there are some of the api is no need to dynamic url but rest of the api are dynamic url
     * This method add country code between the base url and rest path
     **/
    public static String makeDynamicEndpointAPIGateWay(String baseUrl, String path) {
        String finalEndPoint = "";
        String tempBaseURL = "";
        try {
            finalEndPoint =  baseUrl.concat(path);
            LogUtil.printLog("Base URL : ", tempBaseURL);
            LogUtil.printLog("path : ", path);
            LogUtil.printLog(" final end point : ", finalEndPoint);

        } catch (Exception e) {
            e.printStackTrace();
            return finalEndPoint;
        }
        return finalEndPoint;
    }
}
