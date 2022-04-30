package com.ominfo.crm_solution.network;

import com.ominfo.crm_solution.util.LogUtil;

public class DynamicAPIPath {

    public static final String regenerateCallWithCode = "/profile-services/customers/regenerate-otp-dial";
    public static final String uploadPublicKey = "/shared/encryption-handler/device-key/upload";
    //http://123.252.197.10/elixiatms-om/modules/api/TMSVendor/index_omtrucking.php
    public static final String BASE = "https://ominfo.in/crm/app_api.php";//"index_omtrucking.php"
    public static final String BASE_NODEJS = "http://ominfo.in:3000/";//"index_omtrucking.php"// ;
    public static final String POST_LOGIN = BASE+"?action=login";
    public static final String POST_LOGOUT = BASE+"?action=logout_mt_update";
    public static final String POST_PROFILE= BASE_NODEJS+"getEmployee_profile";
    public static final String POST_PRODUCT=  BASE+"?action=get_product_records";
    public static final String POST_SALES= BASE_NODEJS+"getinvoice";
    public static final String POST_QUOTATION= BASE_NODEJS+"getquotations";
    public static final String POST_DISPATCH= BASE_NODEJS+"billpendinglist";
    public static final String POST_SALES_CREDIT= BASE_NODEJS+"sales_unpaid";
    public static final String POST_REMINDER= BASE_NODEJS+"getreminder";
    public static final String POST_EMP_LIST= BASE_NODEJS+"getemployee_list";
    public static final String POST_ADD_REMINDER= BASE_NODEJS+"addreminder";
    public static final String POST_UPDATE_REMINDER= BASE_NODEJS+"updatereminder";
    public static final String POST_RECEIPT= BASE+"?action=get_receipt_records";
    public static final String POST_ADD_VISIT= BASE_NODEJS+"addvisit";
    public static final String POST_EDIT_VISIT= BASE+"?action=editVisit";
    public static final String POST_GET_PROFILE = BASE+"?action=getcrmempprofilepic";
    public static final String POST_NOTIFICATION = BASE+"?action=getcrmempnotif";
    public static final String POST_DEL_NOTIFICATION = BASE+"?action=marknotifread";
    public static final String POST_LOST_APPORTUNITY= BASE+"?getlostopportunity";
    public static final String POST_CHANGE_PROFILE = BASE+"?action=changecrmempprofilepic";
    public static final String POST_CHANGE_PASS = BASE+"?action=changecrmemppassword";
    public static final String POST_PLANT= BASE+"?action=getBranchList";
    public static final String POST_GET_VEHICLE= BASE+"?action=getVehWiseLRs";
    public static final String POST_VEHICLE_DETAILS= BASE+"?action=getVehWiseLRTransaction";
    public static final String POST_SEARCH_CRM= BASE+"?action=getcrmsearchresult";
    public static final String POST_GET_RM= BASE+"?action=rmget";
    public static final String POST_SEARCH_CUST= BASE+"?action=customerfind";
    public static final String POST_SAVE_ENQUIRY= BASE+"?action=enquirysave";
    public static final String POST_GET_ENQUIRY= BASE+"?action=enquiryget";
    public static final String POST_GET_VIEW360= BASE+"?action=custOverAllData";
    public static final String POST_GET_SALES_CREDIT= BASE+"?action=salescreditreport";
    public static final String POST_GET_LEAVE_APP= BASE+"?action=get_leave_records";
    public static final String POST_GET_LEAVE_STATUS= BASE+"?action=apply_leave_updated";
    public static final String POST_GET_LEAVE_SINGLE= BASE+"?action=get_record";
    public static final String POST_ENQUIRY_STATUS= BASE+"?action=statusget";
    public static final String POST_GET_TOUR= BASE+"?action=tourget";
    public static final String POST_GET_VISIT_NO= BASE_NODEJS+"generatevisit_id";
    public static final String POST_GET_VISIT= BASE+"?action=visitget";
    public static final String POST_GET_DASHBOARD= BASE+"?action=getDashboard";
    public static final String POST_APPLY_LEAVE = BASE+"?action=apply_leave";
    public static final String POST_MARK_ATTENDANCE= BASE+"?action=mark_attendance";
    public static final String POST_UPDATE_ATTENDANCE= BASE+"?action=attendance_updated";
    public static final String POST_LOCATION_PER_HOUR= BASE+"?action=emp_long_lati_tracking";
    public static final String POST_TOP_CUST= BASE+"?action=top_customer";
    public static final String POST_RAISE_TICKET = BASE+"?action=getnewticketno";

    //action string
    public static final String POST_FETCH_KATA_CHITTI = "index_with_DRapp.php?action=getKantaChitthi";
    public static final String action_get_rm = "rmget";
    public static final String action_get_tour = "tourget";
    public static final String action_logout = "logout_mt_update";
    public static final String action_get_leave_single = "get_record";
    public static final String action_location_per_hour = "emp_long_lati_tracking";
    public static final String action_enquiry_status = "statusget";
    public static final String action_search_cust = "customerfind";
    public static final String action_mark_attendance = "mark_attendance";
    public static final String action_update_attendance = "attendance_updated";
    public static final String action_save_enquiry = "enquirysave";
    public static final String action_get_enquiry = "enquiryget";
    public static final String action_get_top_customer = "top_customer";
    public static final String action_update_leave_status = "apply_leave_updated";
    public static final String action_get_lost_apportunity = "getlostopportunity";
    public static final String action_get_edit_visit = "editVisit";
    public static final String action_get_leave_app = "get_leave_records";
     public static final String action_get_view360 = "custOverAllData";
    public static final String action_get_sales_credit = "salescreditreport";
    public static final String action_dashboard = "getDashboard";
    public static final String action_get_profile_img = "getcrmempprofilepic";
    public static final String action_change_profile_img = "changecrmempprofilepic";
    public static final String action_change_pass = "changecrmemppassword";
    public static final String action_apply_leave = "apply_leave";
    public static final String action_search = "getcrmsearchresult";
    public static final String action_notification = "getcrmempnotif";
    public static final String action_delete_notification = "marknotifread";
    public static final String GET_USER_LIST = "user";
    public static final String action_products = "get_product_records";
    public static final String action_raise_ticket = "getnewticketno";
    public static final String action_receipt = "get_receipt_records";

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
