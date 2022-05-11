package com.ominfo.hra_app.ui.sales_credit.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatTextView;

import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends BaseActivity {

    Context mContext;

    List<DashModel> dashboardList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        init();

    }

    private void init(){
        //mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        //tvPage.setText("Showing 01 to 05 of\n12 Entries");
        setToolbar();
        dashboardList.add(new DashModel("Sales Credit","₹13245647",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹13245647",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","₹13245647",getDrawable(R.drawable.ic_om_rating)));
        dashboardList.add(new DashModel("Total Quotation Amount","₹13245647",getDrawable(R.drawable.ic_om_total_quotation)));
        dashboardList.add(new DashModel("Dispatch Pending","₹13245647",getDrawable(R.drawable.ic_om_dispatch_pending)));
        dashboardList.add(new DashModel("Enquiry Report","₹13245647",getDrawable(R.drawable.ic_om_enquiry_report)));
        dashboardList.add(new DashModel("Visit Report","₹13245647",getDrawable(R.drawable.ic_om_visit_report)));
        dashboardList.add(new DashModel("Products","₹13245647",getDrawable(R.drawable.ic_om_product)));
        dashboardList.add(new DashModel("Sales Credit","₹13245647",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹13245647",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","₹13245647",getDrawable(R.drawable.ic_om_rating)));

        //setAdapterForDashboardList();
    }


    private void deleteDir(){
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        //File oldFile = new File(myDir);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }


    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport,tvNotifyCount, 0, R.id.imgCall);
    }


   /* //perform click actions
    @OnClick({R.id.fromDate,R.id.toDate})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fromDate:
                 openDataPicker(0,fromDate);
                break;
            case R.id.toDate:
                openDataPicker(1,toDate);
                break;
        }
    }*/

    //set date picker view
    private void openDataPicker(int val , AppCompatTextView datePickerField) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if(val==1){
                datePickerField.setText("-"+sdf.format(myCalendar.getTime()));
                }
                else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }



}