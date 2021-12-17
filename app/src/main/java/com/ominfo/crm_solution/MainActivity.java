package com.ominfo.crm_solution;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.my_account.MyAccountFragment;
import com.ominfo.crm_solution.ui.reminders.ReminderFragment;
import com.ominfo.crm_solution.ui.reminders.adapter.AddTagAdapter;
import com.ominfo.crm_solution.ui.search.SearchFragment;
import com.ominfo.crm_solution.ui.visit_report.activity.StartEndVisitActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    BottomNavigationView bottomNavigationView;
    @BindView(R.id.layOptions)
    LinearLayoutCompat layOptions;

    @BindView(R.id.btnFab)
    FloatingActionButton btnFab;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    AddTagAdapter addTagAdapter;
    private boolean status = false;
    Context mContext;
    List<DashModel> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        //getDeps().inject(this);
        ButterKnife.bind(this);
        btnFab.setImageResource(R.drawable.ic_add_dashboard);
        layOptions.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = coordinator.getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                        .getDisplayMetrics());
        params.height = marginInDp;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        coordinator.setLayoutParams(params);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationbar);

        bottomNavigationView.setBackground(null);

        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new DashboardFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;

                switch (item.getItemId())
                {
                    case R.id.home : temp = new DashboardFragment();
                        break;
                    case R.id.Search : temp = new SearchFragment();
                        break;

                    case R.id.Profile : temp = new ReminderFragment();
                        break;

                    case R.id.Settings : temp = new MyAccountFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,temp).commit();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnFab.setImageResource(R.drawable.ic_add_dashboard);
        layOptions.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = coordinator.getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                        .getDisplayMetrics());
        params.height = marginInDp;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        coordinator.setLayoutParams(params);
    }

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    //show Add Reminder popup
    public void showAddReminderDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_reminder);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addReminderButton = mDialog.findViewById(R.id.addReminderButton);
        RecyclerView rvImages = mDialog.findViewById(R.id.rvImages);
        //LinearLayoutCompat layRecyclerView = mDialog.findViewById(R.id.layRecyclerView);
        setAddTagList(rvImages);
        rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(tagList.size()==1||tagList.size()==2){
                    addTagAdapter.updateList(tagList,1);
                }
                return false;
            }
        });
        addReminderButton.setOnClickListener(new View.OnClickListener() {
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

    private void setAddTagList(RecyclerView rvImages) {
        tagList.removeAll(tagList);
        tagList.add(new DashModel("","1",null));
        if (tagList.size() > 0) {
            rvImages.setVisibility(View.VISIBLE);
        } else {
            rvImages.setVisibility(View.GONE);
        }
        addTagAdapter = new AddTagAdapter(mContext, tagList, new AddTagAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(List<DashModel> mDataTicket) {
                tagList =  mDataTicket;
                addTagAdapter.updateList(tagList,0);
                if(tagList.size()>3){
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 90, getResources()
                                    .getDisplayMetrics());
                    rvImages.setMinimumHeight(marginInDp40);
                    //setMargins(rvImages, 0, marginInDp40, 0, 0);
                }
            }
        });
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvImages.setItemAnimator(new DefaultItemAnimator());
        rvImages.setAdapter(addTagAdapter);
        final boolean[] check = {false};

    }


    //show Add Reminder popup
    public void showAddVisitDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_visit);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addVisitButton = mDialog.findViewById(R.id.addVisitButton);

        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        addVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent i = new Intent(mContext, StartEndVisitActivity.class);
                i.putExtra(Constants.TRANSACTION_ID, "1");
                startActivity(i);
                //((Activity) mContext).overridePendingTransition(0, 0);
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



    //show Add Reminder popup
    public void showAddReceiptDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_receipt);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialog.findViewById(R.id.addReceiptButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        addReceiptButton.setOnClickListener(new View.OnClickListener() {
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


    //perform click actions
    @OnClick({R.id.btnFab,R.id.imgReminder,R.id.imgVisit,R.id.imgReceipt})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnFab:
                if(status) {
                    status = false;
                    btnFab.setImageResource(R.drawable.ic_add_dashboard);
                    layOptions.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = coordinator.getLayoutParams();
                    // Changes the height and width to the specified *pixels*
                    int marginInDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                                    .getDisplayMetrics());
                    params.height = marginInDp;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    coordinator.setLayoutParams(params);

                }
                else {
                    status = true;
                    btnFab.setImageResource(R.drawable.ic_close_dashboard);
                    layOptions.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = coordinator.getLayoutParams();
                    // Changes the height and width to the specified *pixels*
                    int marginInDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 140, getResources()
                                    .getDisplayMetrics());
                    params.height = marginInDp;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    coordinator.setLayoutParams(params);
                    params.height = marginInDp;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    coordinator.setLayoutParams(params);

                }
                break;
            case R.id.imgReminder:
                showAddReminderDialog();
                break;
                case R.id.imgVisit:
                Intent i = new Intent(mContext, StartEndVisitActivity.class);
                i.putExtra(Constants.TRANSACTION_ID, "1");
                startActivity(i);
                //showAddVisitDialog();
                break;
            case R.id.imgReceipt:
                showAddReceiptDialog();
                break;

        }
    }

}