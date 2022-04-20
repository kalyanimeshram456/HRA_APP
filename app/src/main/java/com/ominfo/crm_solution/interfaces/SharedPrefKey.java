package com.ominfo.crm_solution.interfaces;

import com.ominfo.crm_solution.util.SharedPref;

public interface SharedPrefKey {
    String DEVICE_ID = "d_id";
    String IS_ATTACHED_FINGER_PRINT = "is_finger_print_attacked";
    String IS_NOTIFICATION_ENABLED = "is_notification_disable";
    String USER_IMAGE = "user_image";
    String CHOOSE_RECEIPIENT = "choosereceipient";
    String APP_SESSION = "appsession";
    String START_VISIT_LAT = "START_VISIT_LAT";
    String END_VISIT_LAT = "END_VISIT_LAT";
    String START_VISIT_LNG = "START_VISIT_LNG";
    String END_VISIT_LNG = "END_VISIT_LNG";
    String ENTERED_VISIT_LAT = "ENTERED_VISIT_LAT";
    String ENTERED_VISIT_LNG = "ENTERED_VISIT_LNG";
    String IS_FIREBAE_TOKEN_UPDATED = "FIREBAE_TOKEN_UPDATED";
    String SHA256_CLIENTKEYHASH = "SHA256_CLIENTKEYHASH";
    String LOCATION_START = "LOCATION_START";
    String VISIT_NO = "VISIT_NO";
    String LOCATION_ENTERED = "LOCATION_ENTERED";
    String LOCATION_ENTERED_TXT = "LOCATION_ENTERED_TXT";
    String RANDOM_STRING = "random_string";
    String DATE_ALARM = "DATE_ALARM";
    String TITLE_ALARM = "TITLE_ALARM";
    String IS_LOCK_BALANCE = "is_lock_balance";
    String TIMER_STATUS = "TIMER_STATUS";
    String IS_LOGGED_IN = "is_logged_in";
    String FROM_DATE = "FROM_DATE";
    String TO_DATE = "TO_DATE";
    String CHECK_IN = "CHECK_IN";
    String VISIT_ON = "VISIT_ON";
    String ATTENDANCE_ON = "ATTENDANCE_ON";
    String IS_COMPLETE_REGISTRATION = "IS_COMPLETE_REGISTRATION";
    String IS_NOTIFY = "IS_NOTIFY";
    String IS_NOTIFY_COUNT = "IS_NOTIFY_COUNT";
}
