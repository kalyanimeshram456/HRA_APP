package com.ominfo.crm_solution.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.common.SwipeToDeleteCallback;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.crm_solution.ui.notifications.adapter.NotificationsAdapter;
import com.ominfo.crm_solution.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.rvPuranaHisab)
    RecyclerView rvPuranaHisab;
    @BindView(R.id.tvSearchView)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.snackbar)
    ConstraintLayout snackbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    NotificationsAdapter mNotificationsAdapter;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();

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
        setAdapterForPuranaHisabList();
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
        enableSwipeToDeleteAndUndo();
    }

    private void setToolbar(){
        //set toolbar title
        toolbarTitle.setText("Notifications");
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,0,R.id.imgCall);
    }

    private void setAdapterForPuranaHisabList() {
        //driverHisabModelList.add(new DriverHisabModel("भराई","1"));
        //driverHisabModelList.add(new DriverHisabModel("वराई","0"));
        //driverHisabModelList.add(new DriverHisabModel("चाय पानी","0"));
        //driverHisabModelList.add(new DriverHisabModel("टोल खर्चा","1"));
        //driverHisabModelList.add(new DriverHisabModel("रस्सी","1"));
        //driverHisabModelList.add(new DriverHisabModel("कांटा","1"));

        if (driverHisabModelList.size() > 0) {
            mNotificationsAdapter = new NotificationsAdapter(mContext, driverHisabModelList, new NotificationsAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DriverHisabModel mDataTicket) {
                    if(mDataTicket.getDriverHisabValue().equals("1")){
                        LogUtil.printToastMSG(mContext,"Please swipe left to remove.");
                    }
                    if(mDataTicket.getDriverHisabValue().equals("0")) {
                       /* Intent intent = new Intent(mContext,HisabDetailsActivity.class);
                        intent.putExtra(Constants.FROM_SCREEN, Constants.OKAY);
                        startActivity(intent);*/
                    }
                    //mNotificationsAdapter.notifyDataSetChanged();
                }
            });
            rvPuranaHisab.setHasFixedSize(true);
            rvPuranaHisab.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvPuranaHisab.setAdapter(mNotificationsAdapter);
            rvPuranaHisab.setVisibility(View.VISIBLE);
        } else {
            rvPuranaHisab.setVisibility(View.GONE);
        }
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this,mNotificationsAdapter) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final String item = mNotificationsAdapter.getData().get(position).getDriverHisabValue();
                //if(item.equals("1")) {
                   //mNotificationsAdapter.removeItem(position);
                //}

                /*Snackbar snackbar = Snackbar
                        .make(snackbar, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mNotificationsAdapter.restoreItem(item, position);
                        rvPuranaHisab.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
*/
            }
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final int swipeFlags = ItemTouchHelper.END; // or RIGHT
                final int position = viewHolder.getAdapterPosition();
                final String item = mNotificationsAdapter.getData().get(position).getDriverHisabValue();
                if(item.equals("1")) {
                    mNotificationsAdapter.removeItem(position);
                }
                if(item.equals("0")) {
                    mNotificationsAdapter.notifyDataSetChanged();
                    //mNotificationsAdapter.removeItem(position);
                }
                return makeMovementFlags(0, swipeFlags);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvPuranaHisab);
    }


    //perform click actions
    @OnClick({/*R.id.imgBack,R.id.imgNotify*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }
    }
}