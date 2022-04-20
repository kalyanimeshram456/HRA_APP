package com.ominfo.crm_solution.interfaces;

import android.os.Build;

public interface Constants {
    /*date  format*/
    public String DATE_FORMAT_YYYY_MM_DD = "dd MMM yyyy";
    public String DATE_FORMAT_dd_MM_yyyy = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public String DATE_FORMAT_dd_mm_yyyy_ = "dd MMM yyyy";
    String SCANNER  = "/ScannerImages";
    String CAMERA  = "/CameraImages";
    String OTP = "otp";
    String RESEND_OTP = "resend_otp";
    String UPDATE_PROFILE_INFO = "update_profile_info";
    String FILE_NAME = "OM_CRM";
    int SPLAHS_TIME_OUT = 1000;
    String ANDROID = "Android";
    String DEVICE_OS_VALUE = Build.VERSION.RELEASE;
    String TABLET = "Tablet";
    String PHONE = "Phone";
    //For decrypt Token
    String SECRET_KEY = "TheBestSecretKey";
    String INTERNAL_FOLDER_NAME = "send_bits";
    int DEFAULT_IMAGE_SIZE = 240;
    String FROM_TAB_CHANGE = "from_tab_change";
    String FROM_SCREEN = "FROM_SCREEN";
    String LIST = "LIST";
    String ADD = "ADD";
    String TRANSACTION_ID = "TRANSACTION_ID";
    String URL = "URL";
    String SEARCH = "SEARCH";
    String ACCEPTED = "ACCEPTED";
    String TITLE = "title";
    String MESSAGE = "MESSAGE";

    String PAG_SIZE = "7";
    String MIN_PAG_SIZE = "4";
    String KEY_OBJECT = "KEY_Object";

    /*SCREEEN NAME */
    String DASHBOARD = "DASHBOARD";
    String LOGIN = "LOGIN";
    String REGISTRATION = "REGISTRATION";
    String SETTING = "Setting";
    String MY_PROFILE = "MY_PROFILE";
    String KEY_TITLE = "title";
    String KEY_FROM_SCREEN = "from_screen";
    String CREATE_PIN = "create_pin";
    String PAYMENT_INFO = "payment_info";
    String SCANNED_RESULT = "scannedResult";

    public static final float APP_BAR_ELEVATION = 7;

    String CreatePIN = "create_pin";
    String REGISTRATION_MY_PROFILE = "register_myProfile";
    String TOPIC = "topic";
    String USER_ID = "USER_ID";
    //String URL = "url";
    String FETCH_PROFILE_INFO = "fetch_profile_info";
    String FETCH_HISTORY_INFO = "fetch_history_info";
    String FETCH_HISTORY_INFO_More = "fetch_history_info_More";
    String UPLOAD_DOCUMENT = "upload_document";
    String UPDATE_DOCUMENT = "update_document";
    String FETCH_USER_CHARGE = "fetch_charge";
    String UPDATE_CARD = "update_card";
    String BANK_LIST = "bank_list";
    String HISTORY_ITEM = "history_item";
    String CALL = "call";
    String SEND_TEXT = "send_text";
    int afterAlarm = 1; //min
    int  IS_BACK_IMAGE = 1;
    String FORGOT_PASSWORD = "forgot_password";
    String CONFIRM_PIN = "confirm_pin";
    String SEND_MONEY = "send_money";
    long INTERVAL = 5 * 1000;
    long FASTEST_INTERVAL = 2 * 1000;
    long INTERVAL_ATTENDENCE = 5 * 1000;
    long FASTEST_INTERVAL_ATTENDENCE = 2 * 1000;

}
