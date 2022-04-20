package com.ominfo.crm_solution.ui.attendance.ripple_effect;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.ominfo.crm_solution.R;

import java.util.ArrayList;

public class RippleBackground extends RelativeLayout {

    private static final int DEFAULT_RIPPLE_COUNT=6;
    private static final int DEFAULT_DURATION_TIME=3000;
    private static final float DEFAULT_SCALE=6.0f;
    private static final int DEFAULT_FILL_TYPE=0;

    public static int rippleColor;
    private float rippleStrokeWidth;
    private float rippleRadius;
    private int rippleDurationTime;
    private int rippleAmount;
    private int rippleDelay;
    private float rippleScale;
    private int rippleType;
    private Paint paint;
    private boolean animationRunning=false;
    private AnimatorSet animatorSet;
    private ArrayList<Animator> animatorList;
    private LayoutParams rippleParams;
    private ArrayList<RippleView> rippleViewList=new ArrayList<RippleView>();
    public static AttributeSet attrs;
    public Context context;

    public RippleBackground(Context context) {
        super(context);
    }

    public RippleBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init(context,0);
    }

    public RippleBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        init(context,0);
    }

    private void init(final Context context/*, final AttributeSet attrs*/,int status) {
        if (isInEditMode())
            return;

        if (null == attrs) {
            throw new IllegalArgumentException("Attributes should be provided to this view,");
        }

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleBackground);
        if(status==0) {
            rippleColor = typedArray.getColor(R.styleable.RippleBackground_rb_color, getResources().getColor(R.color.white));

             int sizeInPixel = context.getResources().getDimensionPixelSize(R.dimen._14dp);
            int sizeInPixel26 = context.getResources().getDimensionPixelSize(R.dimen._26dp);
            rippleStrokeWidth=typedArray.getDimension(R.styleable.RippleBackground_rb_strokeWidth,sizeInPixel/*getResources().getDimension(R.dimen.rippleStrokeWidth) */);
            rippleRadius=typedArray.getDimension(R.styleable.RippleBackground_rb_radius,sizeInPixel26/*getResources().getDimension(R.dimen.rippleRadius)*/);
            rippleDurationTime=3000;//typedArray.getInt(R.styleable.RippleBackground_rb_duration,DEFAULT_DURATION_TIME);
            rippleAmount=6;//typedArray.getInt(R.styleable.RippleBackground_rb_rippleAmount,DEFAULT_RIPPLE_COUNT);
            rippleScale=6;//typedArray.getFloat(R.styleable.RippleBackground_rb_scale,DEFAULT_SCALE);
            rippleType=typedArray.getInt(R.styleable.RippleBackground_rb_type,DEFAULT_FILL_TYPE);
        }
        if(status==1) {
            rippleColor = typedArray.getColor(R.styleable.RippleBackground_rb_color, getResources().getColor(R.color.deep_yellow));
        }
        if(status==2) {
            rippleColor = typedArray.getColor(R.styleable.RippleBackground_rb_color, getResources().getColor(R.color.light_att_grad));
        }
        if(status==3) {
            rippleColor = typedArray.getColor(R.styleable.RippleBackground_rb_color, getResources().getColor(R.color.Light_Red));
        }
        rippleAmount=6;
        rippleScale=6;
        rippleType=typedArray.getInt(R.styleable.RippleBackground_rb_type,DEFAULT_FILL_TYPE);
        typedArray.recycle();

        rippleDelay=rippleDurationTime/rippleAmount;

        paint = new Paint();
        paint.setAntiAlias(true);
        if(rippleType==DEFAULT_FILL_TYPE){
            rippleStrokeWidth=0;
            paint.setStyle(Paint.Style.FILL);
        }else
            paint.setStyle(Paint.Style.STROKE);
        paint.setColor(rippleColor);

        rippleParams=new LayoutParams((int)(2*(rippleRadius+rippleStrokeWidth)),(int)(2*(rippleRadius+rippleStrokeWidth)));
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorList=new ArrayList<Animator>();

        for(int i=0;i<rippleAmount;i++){
            RippleView rippleView=new RippleView(getContext());
            addView(rippleView,rippleParams);
            rippleViewList.add(rippleView);
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, rippleScale);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);
            scaleXAnimator.setDuration(rippleDurationTime);
            animatorList.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rippleScale);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * rippleDelay);
            scaleYAnimator.setDuration(rippleDurationTime);
            animatorList.add(scaleYAnimator);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            alphaAnimator.setDuration(rippleDurationTime);
            animatorList.add(alphaAnimator);
        }

        animatorSet.playTogether(animatorList);
    }

    private class RippleView extends View {

        public RippleView(Context context) {
            super(context);
            this.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int radius=(Math.min(getWidth(),getHeight()))/2;
            canvas.drawCircle(radius,radius,radius-rippleStrokeWidth,paint);
        }
    }

    public void startRippleAnimation(int status,Context context){
        init(context,status);
        if(!isRippleAnimationRunning()){
            for(RippleView rippleView:rippleViewList){
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning=true;
        }
    }

    public void stopRippleAnimation(){
        if(isRippleAnimationRunning()){
            animatorSet.end();
            animationRunning=false;
        }
    }

    public boolean isRippleAnimationRunning(){
        return animationRunning;
    }
}