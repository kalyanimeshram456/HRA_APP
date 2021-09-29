package com.ominfo.app.ui.driver_hisab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.ui.driver_hisab.activity.DriverHisabDetailsActivity;
import com.ominfo.app.ui.driver_hisab.adapter.DriverHisabAdapter;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.app.ui.notifications.NotificationsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverHisabActivity extends BaseActivity {

    @BindView(R.id.rvDriverHisab)
    RecyclerView rvDriverHisab;
    DriverHisabAdapter mDriverHisabAdapter;
    Context mContext;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_hisab);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }

    private void init(){
        setAdapterForActivityList();
    }

    private void setAdapterForActivityList() {
        driverHisabModelList.add(new DriverHisabModel("भराई",""));
        driverHisabModelList.add(new DriverHisabModel("वराई",""));
        driverHisabModelList.add(new DriverHisabModel("चाय पानी",""));
        driverHisabModelList.add(new DriverHisabModel("टोल खर्चा",""));
        driverHisabModelList.add(new DriverHisabModel("रस्सी",""));
        driverHisabModelList.add(new DriverHisabModel("कांटा",""));

        if (driverHisabModelList.size() > 0) {
            mDriverHisabAdapter = new DriverHisabAdapter(mContext, driverHisabModelList, new DriverHisabAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DriverHisabModel mDataTicket) {

                }
            });
            rvDriverHisab.setHasFixedSize(true);
            rvDriverHisab.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvDriverHisab.setAdapter(mDriverHisabAdapter);
            rvDriverHisab.setVisibility(View.VISIBLE);
        } else {
            rvDriverHisab.setVisibility(View.GONE);
        }
    }

    private void showAddDriverHisabDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_driver_hisab);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.okayButton);
        //LinearLayoutCompat appCompatLayout = mDialog.findViewById(R.id.layPopup);
        //appCompatButton.setVisibility(View.VISIBLE);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
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

    //perform click actions
    @OnClick({R.id.imgBack,R.id.imgAddHisab,R.id.saveButton,R.id.uploadButton,R.id.imgNotify})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAddHisab:
                showAddDriverHisabDialog();
                break;
            case R.id.uploadButton:
                showUploadDialog();
                break;
            case R.id.saveButton:
                finish();
                launchScreen(mContext, DriverHisabDetailsActivity.class);
                break;
            case R.id.imgNotify:
                launchScreen(mContext, NotificationsActivity.class);
                break;
        }
    }
}