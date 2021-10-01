package com.ominfo.app.ui.purana_hisab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.interfaces.Constants;
import com.ominfo.app.ui.driver_hisab.adapter.DriverHisabAdapter;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.app.ui.kata_chithi.KataChithiActivity;
import com.ominfo.app.ui.notifications.NotificationsActivity;
import com.ominfo.app.ui.purana_hisab.PuranaHisabActivity;
import com.ominfo.app.ui.purana_hisab.adapter.HisabDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HisabDetailsActivity extends BaseActivity {

    @BindView(R.id.tvAurJaniye)
    AppCompatTextView mTextViewAurJaniye;
    Context mContext;
    @BindView(R.id.rvDriverHisab)
    RecyclerView rvDriverHisab;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imgExpandSheet)
    AppCompatImageView imgExpandSheet;
    @BindView(R.id.layTotalBhatta)
    LinearLayoutCompat layTotalBhatta;
    @BindView(R.id.layTotalHisab)
    LinearLayoutCompat layTotalHisab;
    @BindView(R.id.complaintButton)
    AppCompatButton complaintButton;

    HisabDetailsAdapter mHisabDetailsAdapter;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();
    private boolean mSheetExpandStatus = false;
    private String fromScr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisab_details);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        //set toolbar title
        setToolbar();
        getIntentData();
        setAdapterForActivityList();
    }

    private void setToolbar(){
        //set toolbar title
        toolbarTitle.setText(getString(R.string.scr_title_purana_hisab));
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,0,R.id.imgCall);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if(intent!=null) {
            fromScr = intent.getStringExtra(Constants.FROM_SCREEN);
            complaintButton.setText(fromScr);
        }
        else {
            complaintButton.setText(getString(R.string.scr_lbl_complaint));
        }
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    private void setAdapterForActivityList() {
        driverHisabModelList.add(new DriverHisabModel("भराई","1"));
        driverHisabModelList.add(new DriverHisabModel("वराई","1"));
        driverHisabModelList.add(new DriverHisabModel("चाय पानी","1"));
        driverHisabModelList.add(new DriverHisabModel("टोल खर्चा","0"));
        driverHisabModelList.add(new DriverHisabModel("रस्सी","1"));
        driverHisabModelList.add(new DriverHisabModel("कांटा","0"));

        if (driverHisabModelList.size() > 0) {
            mHisabDetailsAdapter = new HisabDetailsAdapter(mContext, driverHisabModelList, new HisabDetailsAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DriverHisabModel mDataTicket) {

                }
            });
            rvDriverHisab.setHasFixedSize(true);
            rvDriverHisab.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvDriverHisab.setAdapter(mHisabDetailsAdapter);
            rvDriverHisab.setVisibility(View.VISIBLE);
        } else {
            rvDriverHisab.setVisibility(View.GONE);
        }
    }

    private void showComplaintDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_check_issue);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.okayButton);
        //LinearLayoutCompat appCompatLayout = mDialog.findViewById(R.id.layPopup);
        //appCompatButton.setVisibility(View.VISIBLE);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showRaiseIssueDialog();
                //launchScreen(mContext, DriverHisabActivity.class);
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void showIssueRecordingDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_issue_recording);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.okayButton);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void showRaiseIssueDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_raise_issue);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatImageView imgRecord = mDialog.findViewById(R.id.imgRecord);
        AppCompatImageView imgStop = mDialog.findViewById(R.id.imgStop);

        AppCompatButton okayButton = mDialog.findViewById(R.id.okayButton);
        //LinearLayoutCompat appCompatLayout = mDialog.findViewById(R.id.layPopup);
        //appCompatButton.setVisibility(View.VISIBLE);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showSuccessDialog(getString(R.string.msg_issue_submitted));
            }
        });
        imgRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgRecord.setVisibility(View.GONE);
                imgStop.setVisibility(View.VISIBLE);
            }
        });
        imgStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgRecord.setVisibility(View.VISIBLE);
                imgStop.setVisibility(View.GONE);
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    //perform click actions
    @OnClick({R.id.complaintButton,R.id.tvHisabName,R.id.layAurJaniye})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.complaintButton:
                if(fromScr.equals(getString(R.string.scr_lbl_complaint))) {
                    showComplaintDialog();
                }else {
                    finish();
                }
                break;
            case R.id.tvHisabName:
                //showIssueRecordingDialog();
                break;
            case R.id.layAurJaniye:
                if(mSheetExpandStatus){
                    //clicked
                    mSheetExpandStatus = false;

                    int marginInDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 110, getResources()
                                    .getDisplayMetrics());
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 35, getResources()
                                    .getDisplayMetrics());
                    setMargins(layTotalHisab,0,marginInDp,0,0);
                    setMargins(rvDriverHisab,0,marginInDp40,0,0);
                    imgExpandSheet.setBackgroundDrawable(getDrawable(R.drawable.ic_down));
                    layTotalBhatta.setVisibility(View.GONE);

                }
                else {
                    mSheetExpandStatus = true;
                    int marginInDp230 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 190, getResources()
                                    .getDisplayMetrics());
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 85, getResources()
                                    .getDisplayMetrics());
                    setMargins(layTotalHisab,0,marginInDp230,0,0);
                    setMargins(rvDriverHisab,0,marginInDp40,0,0);
                    imgExpandSheet.setBackgroundDrawable(getDrawable(R.drawable.ic_up));
                    layTotalBhatta.setVisibility(View.VISIBLE);

                }
                break;

        }
    }
}