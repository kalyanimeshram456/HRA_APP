package com.ominfo.app.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.basecontrol.BaseApplication;
import com.ominfo.app.database.AppDatabase;
import com.ominfo.app.interfaces.Constants;
import com.ominfo.app.interfaces.SharedPrefKey;
import com.ominfo.app.ui.contacts.AllContactsActivity;
import com.ominfo.app.ui.driver_hisab.DriverHisabActivity;
import com.ominfo.app.ui.kata_chithi.KataChithiActivity;
import com.ominfo.app.ui.login.LoginActivity;
import com.ominfo.app.ui.login.model.LoginResultTable;
import com.ominfo.app.ui.notifications.NotificationsActivity;
import com.ominfo.app.ui.purana_hisab.PuranaHisabActivity;
import com.ominfo.app.ui.purana_hisab.activity.ComplaintsActivity;
import com.ominfo.app.util.LogUtil;
import com.ominfo.app.util.SharedPref;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashbooardActivity extends BaseActivity {

    @BindView(R.id.cardKataChithi)
    CardView mCardKataChithi;
    Context mContext;
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbooard);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        setToolbar();
        LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
        if(loginResultTable!=null){
            //LogUtil.printToastMSG(mContext,"Welcome "+loginResultTable.getFirstName());
        }
    }

    private void setToolbar(){
        initToolbar(0,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,R.id.imgLogout,R.id.imgCall);
    }

    //perform click actions
    @OnClick({R.id.layKataChithi,R.id.layDriverHisab,R.id.layPuranaHisab,R.id.layEwayBill
    ,R.id.tvTruckDetails,R.id.tvCleanerDetails})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layKataChithi:
                launchScreen(mContext, KataChithiActivity.class);
                break;
            case R.id.layDriverHisab:
                showDriverHisabDialog();
                break;
            case R.id.layPuranaHisab:
                launchScreen(mContext, PuranaHisabActivity.class);
                break;
            case R.id.layEwayBill:
                showEwayBillDialog();
                break;
            case R.id.tvTruckDetails:
                showTruckDetailsDialog();
                break;
            case R.id.tvCleanerDetails:
                showCleanerDetailsDialog();
                break;
        }
    }

    private void showDriverHisabDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_driver_hisab);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.okayButton);
        LinearLayoutCompat layCalender = mDialog.findViewById(R.id.layCalender);
        AppCompatTextView tvDateValue = mDialog.findViewById(R.id.tvDateValue);
        //LinearLayoutCompat appCompatLayout = mDialog.findViewById(R.id.layPopup);
        //appCompatButton.setVisibility(View.VISIBLE);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                launchScreen(mContext, DriverHisabActivity.class);
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        layCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDataPicker(tvDateValue);
            }
        });
        mDialog.show();
    }

    //set date picker view
    private void openDataPicker(AppCompatTextView datePickerField) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMM yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void showEwayBillDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_eway_bill);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        AppCompatImageView imgShare = mDialog.findViewById(R.id.imgShare);
        AppCompatTextView tvTitle = mDialog.findViewById(R.id.tvNumberTitle);
        AppCompatTextView tvTitleValue = mDialog.findViewById(R.id.tvNumberTitleValue);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                openContactSupportEmail(mContext,"Share "+tvTitle.getText().toString(),"",tvTitle.getText().toString()+"\n"+tvTitleValue.getText().toString());
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

    //show truck details popup
    public void showTruckDetailsDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_truck_details);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
        RelativeLayout relRC = mDialog.findViewById(R.id.relRC);
        RelativeLayout relPUC = mDialog.findViewById(R.id.relPUC);
        RelativeLayout relIss = mDialog.findViewById(R.id.relIss);

        relRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relPUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relIss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
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

    //show truck details popup
    public void showFullImageDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);

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

    //show cleaner details popup
    public void showCleanerDetailsDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_cleaner_details);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);

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

    //request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,

                                },
                        1000);

            } else {
                //createFolder();
            }
        } else {
            //createFolder();
        }
    }


    /*
     * ACCESS_FINE_LOCATION permission result
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    //BaseApplication.getInstance().mService.requestLocationUpdates();
                } else {
                    //Toast.makeText(mContext, getString(R.string.somthing_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


}