package com.ominfo.crm_solution.network;

import com.ominfo.crm_solution.util.LogUtil;

public class DynamicAPIPath {

    public static final String regenerateCallWithCode = "/profile-services/customers/regenerate-otp-dial";
    public static final String uploadPublicKey = "/shared/encryption-handler/device-key/upload";
    //http://123.252.197.10/elixiatms-om/modules/api/TMSVendor/index_omtrucking.php
    public static final String BASE = "https://ominfo.in/crm/app_api.php";//"index_omtrucking.php"
    public static final String BASE_NODEJS = "http://ominfo.in:3000/";//"index_omtrucking.php"// ;
    public static final String POST_LOGIN = BASE+"?action=login";
    public static final String POST_PROFILE= BASE_NODEJS+"getEmployee_profile";
    public static final String POST_PRODUCT= BASE_NODEJS+"allproducts";
    public static final String POST_SALES= BASE_NODEJS+"getinvoice";
    public static final String POST_QUOTATION= BASE_NODEJS+"getquotations";
    public static final String POST_DISPATCH= BASE_NODEJS+"billpendinglist";
    public static final String POST_SALES_CREDIT= BASE_NODEJS+"sales_unpaid";
    public static final String POST_REMINDER= BASE_NODEJS+"getreminder";
    public static final String POST_EMP_LIST= BASE_NODEJS+"getemployee_list";
    public static final String POST_ADD_REMINDER= BASE_NODEJS+"addreminder";
    public static final String POST_UPDATE_REMINDER= BASE_NODEJS+"updatereminder";
    public static final String POST_RECEIPT= BASE_NODEJS+"getreceipts";
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
    public static final String POST_ENQUIRY_STATUS= BASE+"?action=statusget";
    public static final String POST_GET_TOUR= BASE+"?action=tourget";
    public static final String POST_GET_VISIT_NO= BASE_NODEJS+"generatevisit_id";
    public static final String POST_GET_VISIT= BASE+"?action=visitget";
    public static final String POST_GET_DASHBOARD= BASE+"?action=getDashboard";

    //action string
    public static final String POST_FETCH_KATA_CHITTI = "index_with_DRapp.php?action=getKantaChitthi";
    public static final String action_get_rm = "rmget";
    public static final String action_get_tour = "tourget";
    public static final String action_get_visit_no = "visitnogenerate";
    public static final String action_enquiry_status = "statusget";
    public static final String action_search_cust = "customerfind";
    public static final String action_save_enquiry = "enquirysave";
    public static final String action_get_enquiry = "enquiryget";
    public static final String action_get_lost_apportunity = "getlostopportunity";
    public static final String action_get_edit_visit = "editVisit";
    public static final String action_get_visits = "visitget";
    public static final String action_get_view360 = "custOverAllData";
    public static final String action_get_sales_credit = "salescreditreport";
    public static final String action_dashboard = "getDashboard";
    public static final String action_get_profile_img = "getcrmempprofilepic";
    public static final String action_change_profile_img = "changecrmempprofilepic";
    public static final String action_change_pass = "changecrmemppassword";
    public static final String action_search = "getcrmsearchresult";
    public static final String action_notification = "getcrmempnotif";
    public static final String action_delete_notification = "marknotifread";
    public static final String GET_USER_LIST = "user";


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