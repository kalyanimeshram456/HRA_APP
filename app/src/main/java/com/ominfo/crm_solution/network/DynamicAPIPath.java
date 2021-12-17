package com.ominfo.crm_solution.network;

import com.ominfo.crm_solution.util.LogUtil;

public class DynamicAPIPath {

    public static final String regenerateCallWithCode = "/profile-services/customers/regenerate-otp-dial";
    public static final String uploadPublicKey = "/shared/encryption-handler/device-key/upload";
    //http://123.252.197.10/elixiatms-om/modules/api/TMSVendor/index_omtrucking.php
    public static final String BASE = "app_api.php";//"index_omtrucking.php";
    public static final String POST_LOGIN = BASE+"?action=login";
    public static final String POST_VEHICLE_NO= BASE+"?action=getVehicles";
    public static final String POST_PLANT= BASE+"?action=getBranchList";
    public static final String POST_GET_VEHICLE= BASE+"?action=getVehWiseLRs";
    public static final String POST_VEHICLE_DETAILS= BASE+"?action=getVehWiseLRTransaction";
    public static final String POST_SEARCH= BASE+"?action=searchLRTransaction";
    public static final String POST_CHECK_NO= BASE+"?action=checkLRno";
    public static final String POST_UPLOAD_VEHICLE= BASE;

    //
    public static final String POST_FETCH_KATA_CHITTI = "index_with_DRapp.php?action=getKantaChitthi";
    public static final String POST_SAVE_KATA_CHITTI = "index_with_DRapp.php";
    public static final String GET_CHECK_SETUP = "check_setup_screen";
    public static final String GET_DASHBOARD = "dashboard_screen";
    public static final String RESEND_otp = "resend-otp";
    public static final String profile_update = "profile-update";
    public static final String user_info = "user-info";
    public static final String document_upload = "document-upload";
    public static final String USER_CHARGE = "user-charges";
    public static final String DOCUMENT_UPDATE = "document-update";
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
