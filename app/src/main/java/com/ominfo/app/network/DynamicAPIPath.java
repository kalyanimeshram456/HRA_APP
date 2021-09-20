package com.ominfo.app.network;

import com.ominfo.app.util.LogUtil;

public class DynamicAPIPath {

    public static final String regenerateCallWithCode = "/profile-services/customers/regenerate-otp-dial";
    public static final String uploadPublicKey = "/shared/encryption-handler/device-key/upload";
    public static final String POST_LOGIN = "auth/site_officer_login";
    public static final String POST_PUSH_EVENT = "push_event";
    public static final String GET_WELCOME = "welcome_page";
    public static final String GET_CHECK_SETUP = "check_setup_screen";
    public static final String GET_DASHBOARD = "dashboard_screen";
    public static final String RESEND_otp = "resend-otp";
    public static final String profile_update = "profile-update";
    public static final String user_info = "user-info";
    public static final String document_upload = "document-upload";
    public static final String USER_CHARGE = "user-charges";
    public static final String DOCUMENT_UPDATE = "document-update";
    public static final String POST_FORGOT_PASSWORD = "forgot-password";
    public static final String GET_TIMING = "get_timing_data_from_lpr";
    public static final String GET_EXEMPT = "get_exempt_data_from_lpr";
    public static final String GET_SOFFLAW = "get_scofflaw_data_from_lpr";
    public static final String GET_PERMIT = "get_permit_data_from_lpr";
    public static final String GET_DATA_FROM_LPR = "informatics/get_data_from_lpr";
    public static final String GET_METER = "get_citation_data_from_lpr";
    public static final String POST_UPDATE_SITE_OFFICER = "update_site_officer";
    public static final String POST_ADD_DATASET = "add_dataset";
    public static final String GET_BOOK_TOPIC = "books";
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
