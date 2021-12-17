package com.ominfo.crm_solution.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {

    /**
     * this method check internet available or not in app
     *
     * @param context context
     * @return true if internet available else return false
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isActiveNetworkConnected = false;

        if (connectivity != null) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED || info.getState() == NetworkInfo.State.CONNECTING) {
                    isActiveNetworkConnected = true;
                } else {
                    isActiveNetworkConnected = false;
                }
            }

        } else {
            isActiveNetworkConnected = false;
        }

        return isActiveNetworkConnected;

    }


}