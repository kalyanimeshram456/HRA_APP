package com.ominfo.crm_solution.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.interfaces.DialogCallbacks;


public class DialogUtils {

    /**
     * common error dialog for every request failure
     *
     * @param activity     context
     * @param title        title
     * @param message      message
     * @param isCancelable true or false
     * @param onclick      click event
     */
    public static void showErrorDialog(final Context activity, final String title,
                                       final String message, boolean isCancelable,
                                       DialogCallbacks onclick) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            if (title != null)
                builder.setTitle(title);
            if (message != null)
                builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onclick.onPositiveClick();
                }
            })
            ;
            builder.setCancelable(isCancelable);
            AlertDialog msg = builder.create();
            msg.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void showExitDialog(Context context, String title, String message, DialogCallbacks dialogInterface) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                dialogInterface.onNegativeClick();
            }
        });

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialogInterface.onPositiveClick();
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showDialog(final Context activity, final String title,
                                  final String message, boolean isCancelable,
                                  DialogCallbacks onclick) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            if (message != null)
                builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onclick.onPositiveClick();
                }
            })
            ;
            builder.setCancelable(isCancelable);
            AlertDialog msg = builder.create();
            msg.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
