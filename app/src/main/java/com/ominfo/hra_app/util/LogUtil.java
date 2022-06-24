package com.ominfo.hra_app.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ominfo.hra_app.R;

public class LogUtil {

    public static boolean isEnableLogs = false;
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
    public static void printToastMSGCenter(Context mContext, String object) {
        if (isEnableToast && object!=null) {
            Toast toast = Toast.makeText(mContext, object, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
            toast.show();
            //Toast.makeText(mContext,object, Toast.LENGTH_SHORT).show();
        }
    }
    public static void printSnackBar(Context context,int colour,View id, String object) {
        //findViewById(android.R.id.content)
        if (isEnableToast && object!=null) {
            Snackbar.make(id, object.trim(), Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .setTextColor(context.getResources().getColor(R.color.white))
                    .setBackgroundTint(context.getResources().getColor(R.color.color_main))
                    .setTextMaxLines(3)
                    .setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).show();

            //snackbar.show();
        }
    }
    public static Snackbar printSnackBarTest(Context context,int colour,View id, String object) {
        //findViewById(android.R.id.content)
        if (isEnableToast && object!=null) {
            final Snackbar snackBar = Snackbar.make(id, object.trim(), Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .setTextColor(context.getResources().getColor(R.color.white))
                    .setBackgroundTint(context.getResources().getColor(R.color.color_main))
                    .setTextMaxLines(3);
            snackBar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call your action method here
                    snackBar.dismiss();
                }
            });
            snackBar.show();
            return snackBar;
        }
        return null;
            //snackbar.show();
        }

}
