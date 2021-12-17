/*
package com.ominfo.crm_solution.ui.lr_number;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.ui.lr_number.adapter.LrNumberAdapter;
import com.ominfo.crm_solution.ui.lr_number.fragment.PendingFragment;
import com.ominfo.crm_solution.ui.lr_number.fragment.ViewFragment;
import com.ominfo.crm_solution.ui.driver_hisab.model.DriverHisabModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectedLrNumberActivity extends BaseActivity {
    Context mContext;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    LrNumberAdapter mLrNumberAdapter;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lr_number);
        //for full screen toolbar
        mDb = BaseApplication.getInstance().getAppDatabase();
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
        setLrNumberTab();
    }
    private void setToolbar(){
        toolbarTitle.setText(R.string.scr_lbl_selected_lr_number);
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,R.id.imgLogout,R.id.imgCall);
    }

    */
/*public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }*//*



    private void setLrNumberTab(){
        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        // Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("View"); // set the Text for the first Tab
        //firstTab.setIcon(R.drawable.ic_enforcement); // set an icon for the
        // first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
        // Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Pending"); // set the Text for the second Tab
        //secondTab.setIcon(R.drawable.ic_record_ticket); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
        Fragment fragment = new ViewFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        // Create a new Tab named "Third"
       */
/* TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Third"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.ic_launcher_background); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout
       *//*


        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ViewFragment();
                        break;
                    case 1:
                        fragment = new PendingFragment();
                        break;
                    default:
                        fragment = new ViewFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    //perform click actions
    @OnClick({*/
/*R.id.imgBack,R.id.imgNotify*//*
})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
          */
/*  case R.id.imgBack:
                finish();
                break;
            case R.id.imgNotify:
                //launchScreen(mContext, NotificationsActivity.class);
                break;*//*

        }
    }
}*/
