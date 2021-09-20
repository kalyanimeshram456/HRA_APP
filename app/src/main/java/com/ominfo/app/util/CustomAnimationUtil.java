package com.ominfo.app.util;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by neha on 26/9/18.
 */

public class CustomAnimationUtil {
    private Vibrator vib;
    Animation animShake;
    private Context context;

    public CustomAnimationUtil(Context context) {
        this.context = context;
        vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void showErrorEditTextAnimation(View view, int resID) {
        Animation shake = AnimationUtils.loadAnimation(context, resID);
        view.startAnimation(shake);
        vib.vibrate(120);
    }
}
