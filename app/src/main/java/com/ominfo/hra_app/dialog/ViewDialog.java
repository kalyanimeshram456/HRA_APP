package com.ominfo.hra_app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import com.ominfo.hra_app.R;

public class ViewDialog {

    private Activity activity;
    private Dialog dialog;

    //..we need the context else we can not create the dialog so get context in constructor
    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showDialog(String message) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        }
        dialog = new Dialog(activity, R.style.ThemeDialogCustomFullScreen);
        View view = activity.getLayoutInflater().inflate(R.layout.layout_progress_loader, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(view);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AppCompatTextView appCompatTextView = dialog.findViewById(R.id.mMessage);
        appCompatTextView.setText(message);
        //...initialize the imageView form infalted layout
        //  ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);

        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */
        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);

        //...now load that gif which we put inside the drawble folder here with the help of Glide

//        Glide.with(activity)
//                .load(R.drawable.loading)
//                .placeholder(R.drawable.loading)
//                .centerCrop()
//                .crossFade()
//                .into(imageViewTarget);

        //...finaly show it
        // For Full Screen Dialog Box
        if (dialog.getWindow() != null) {
            dialog.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            dialog.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorSemiTransparentWhite));
        }

        if (!activity.isDestroyed() && !activity.isFinishing() && !dialog.isShowing())
            dialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public void hideDialog() {
        if (dialog != null)
            dialog.dismiss();
    }
}