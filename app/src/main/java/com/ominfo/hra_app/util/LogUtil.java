package com.ominfo.hra_app.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtil {

    public static boolean isEnableLogs = true;
    public static boolean isEnableToast = true;

    public static void printLog(String tag, Object object) {
        if (isEnableLogs && object!=null) {
//            String json = new Gson().toJson(object);
            Log.d(tag, "" + object);
        }

    }

    public static void printLog(String tag, String object) {
        if (isEnableLogs && object!=null) {
            Log.d(tag, "" + object);
        }
    }

    public static void printLog(String tag, String object, Throwable tr) {
        if (isEnableLogs && object!=null) {
            LogUtil.printLog(tag, "" + object);
        }
    }

    public static void printToastMSG(Context mContext, String object) {
        if (isEnableToast && object!=null) {
            Toast.makeText(mContext,object, Toast.LENGTH_SHORT).show();

        }
    }
}
