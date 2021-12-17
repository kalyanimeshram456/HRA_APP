package com.ominfo.crm_solution.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class DialogUtil {
    private static final String TAG = DialogUtil.class.getSimpleName();
    public static Context mContext;
    public static Dialog dialog;

    public DialogUtil(Context context) {
        this.mContext = context;
        if (dialog == null) {
            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    public interface UnRegisterDialogListener {
        void onPositiveClick();

        void onNegativeClick();
    }

    public interface DialogListener {
        void onPositiveClick();

        void onPositiveClick(String value);

        void onPositiveClick(String value, String key);

        void onNegativeClick();
    }

   /* *//*select doc type options*//*
    public void selectOptions(List<DocumentTypeOptionsModel> documentTypeOptionsModelsList
            , String mTitle, final DialogListener dialogListener) {
        if (((Activity) mContext).isFinishing()) {
            return;
        }
        try {
            dialog.setContentView(R.layout.custom_dialog_select_options);
            if (!((Activity) mContext).isFinishing() && !((Activity) mContext).isDestroyed()) {
                dialog.show();
            }
            AppCompatTextView mTextViewTitle = dialog.findViewById(R.id.tv_title);
            AppCompatTextView mTextViewCancel = dialog.findViewById(R.id.tv_cancel);
            RecyclerView mRecycleitems = dialog.findViewById(R.id.recyclerView_SelectLeaves);
            mTextViewTitle.setText(mTitle);
            mRecycleitems.setHasFixedSize(true);
            mRecycleitems.setLayoutManager(new LinearLayoutManager(mContext));
            DocTypeDownOptionsAdapter rvAdapter = new DocTypeDownOptionsAdapter(mContext, documentTypeOptionsModelsList);
            mRecycleitems.setAdapter(rvAdapter);
            mTextViewCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialogListener.onNegativeClick();
                }
            });

            rvAdapter.setListener(new DocTypeDownOptionsAdapter.AdapterListener() {
                @Override
                public void itemClick(int position, String key, String value) {
                    dialog.dismiss();
                    dialogListener.onPositiveClick(key, value);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    /*select support contact list options*/
   /* public void selectSupportContactOptions(List<ContactOptionsModel> supportContactOptionsModels
            , String mTitle, final DialogListener dialogListener) {
        if (((Activity) mContext).isFinishing()) {
            return;
        }
        try {
            dialog.setContentView(R.layout.custom_dialog_select_options);
            if (!((Activity) mContext).isFinishing() && !((Activity) mContext).isDestroyed()) {
                dialog.show();
            }
            AppCompatTextView mTextViewTitle = dialog.findViewById(R.id.tv_title);
            AppCompatTextView mTextViewCancel = dialog.findViewById(R.id.tv_cancel);
            RecyclerView mRecycleitems = dialog.findViewById(R.id.recyclerView_SelectLeaves);
            mTextViewTitle.setText(mTitle);
            mRecycleitems.setHasFixedSize(true);
            mRecycleitems.setLayoutManager(new LinearLayoutManager(mContext));
            SupportContactDropDownItemAdapter rvAdapter = new SupportContactDropDownItemAdapter(mContext, supportContactOptionsModels);
            mRecycleitems.setAdapter(rvAdapter);
            mTextViewCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialogListener.onNegativeClick();
                }
            });

            rvAdapter.setListener(new SupportContactDropDownItemAdapter.AdapterListener() {
                @Override
                public void itemClick(int position, String key, String value) {
                    dialog.dismiss();
                    dialogListener.onPositiveClick(key, value);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*dismisss dialog*/
    public static void dismissDialog(Context context) {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = null;
            }
        } catch (Exception e) {
            LogUtil.printLog(TAG, "dismissDialog: " + e.toString());
        }
    }
}
