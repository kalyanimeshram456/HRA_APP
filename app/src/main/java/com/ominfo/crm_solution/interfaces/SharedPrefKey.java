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
    String ATTENTION_LOC_TITLE = "ATTENTION_LOC_TITLE";
    String ATTENTION_LOC_LAT = "ATTENTION_LOC_LAT";
    String ATTENTION_LOC_LONG = "ATTENTION_LOC_LONG";
    String TIMER_STATUS = "TIMER_STATUS";
    String IS_LOGGED_IN = "is_logged_in";
    String FROM_DATE = "FROM_DATE";
    String ATTENDANCE_START_TIME = "ATTENDANCE_START_TIME";
    String CHECK_IN_BUTTON = "CHECK_IN_BUTTON";
    String CHECK_OUT_TIME = "CHECK_OUT_TIME";
    String CHECK_OUT_ENABLED = "CHECK_OUT_ENABLED";
    String VISIT_ON = "VISIT_ON";
    String ATTENDANCE_ON = "ATTENDANCE_ON";
    String ATTENDANCE_ID = "ATTENDANCE_ID";
    String ATTENDANCE_CHECKIN_TIME = "ATTENDANCE_CHECKIN_TIME";
    String RATE_US_COUNT = "RATE_US_COUNT";
    String RATED = "RATED";
    String RATED_DATE = "RATED_DATE";
    String IS_NOTIFY = "IS_NOTIFY";
    String IS_NOTIFY_COUNT = "IS_NOTIFY_COUNT";
}
