package com.ominfo.crm_solution.ui.notifications;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.crm_solution.ui.notifications.adapter.CallExpandableListAdapter;
import com.ominfo.crm_solution.ui.notifications.adapter.NotificationsAdapterBkup;
import com.ominfo.crm_solution.ui.notifications.model.ExpandableListDataItems;
import com.ominfo.crm_solution.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsActivitybkup extends BaseActivity {

    Context mContext;
    @BindView(R.id.tvSearchView)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.snackbar)
    ConstraintLayout snackbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    NotificationsAdapterBkup mNotificationsAdapter;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();
    @BindView(R.id.expandableListViewSample)
    ExpandableListView expandableListViewExample;
    //Expandable list
    CallExpandableListAdapter expandableListAdapter;
    List<String> expandableTitleList = new ArrayList<>();
    List<String> searchList= new ArrayList<>();
    LinkedHashMap<String, List<String>> expandableDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        //set toolbar
        setToolbar();
        setExpandableList();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    //Do something
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);

                } else {
                    LogUtil.printToastMSG(mContext,getString(R.string.err_msg_connection_was_refused));
                            mSwipeRefreshLayout.setRefreshing(false);

                }
            }
        });
    }

    private void setToolbar(){
        //set toolbar title
        toolbarTitle.setText("Notifications");
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,tvNotifyCount,0,R.id.imgCall);
    }

    private void setExpandableList(){
        expandableDetailList = ExpandableListDataItems.getData();
        expandableTitleList = new ArrayList<String>(expandableDetailList.keySet());
        expandableListAdapter = new CallExpandableListAdapter(this, expandableTitleList, expandableDetailList);
        expandableListViewExample.setAdapter(expandableListAdapter);

        // This method is called when the group is expanded
        expandableListViewExample.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the group is collapsed
        expandableListViewExample.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });

        // This method is called when the child in any group is clicked
        // via a toast method, it is shown to display the selected child item as a sample
        // we may need to add further steps according to the requirements
        expandableListViewExample.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(getApplicationContext(), expandableTitleList.get(groupPosition)
                        + " -> "
                        + expandableDetailList.get(
                        expandableTitleList.get(groupPosition)).get(
                        childPosition), Toast.LENGTH_SHORT
                ).show();*/
               /* showCallDetailsDialog(expandableDetailList.get(
                        expandableTitleList.get(groupPosition)).get(
                        childPosition));*/
                return false;
            }
        });

    }


    //perform click actions
    @OnClick({/*R.id.imgBack,R.id.imgNotify*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }
    }
}