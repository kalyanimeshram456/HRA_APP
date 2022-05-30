package com.ominfo.hra_app.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ominfo.hra_app.R;

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
    public static void printSnackBar(Context context,int colour,View id, String object) {
        //findViewById(android.R.id.content)
        if (isEnableToast && object!=null) {
            Snackbar.make(id, object, Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .setTextColor(colour)
                    .setBackgroundTint(context.getResources().getColor(R.color.black))
                    .setTextMaxLines(3)
                    .setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).show();

            //snackbar.show();
        }
    }
}
