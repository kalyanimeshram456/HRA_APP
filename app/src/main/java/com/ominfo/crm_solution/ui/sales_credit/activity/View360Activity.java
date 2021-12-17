package com.ominfo.crm_solution.ui.sales_credit.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.sales_credit.adapter.EnquiriesAdapter;
import com.ominfo.crm_solution.ui.sales_credit.adapter.InvoiceAdapter;
import com.ominfo.crm_solution.ui.sales_credit.adapter.QuotationsAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class View360Activity extends BaseActivity {

    Context mContext;

    @BindView(R.id.layInvoices)
    AppCompatTextView layInvoices;

    @BindView(R.id.layQuotations)
    AppCompatTextView layQuotations;

    @BindView(R.id.layEnquiries)
    AppCompatTextView layEnquiries;

    @BindView(R.id.tvCol1)
    AppCompatTextView textViewCol1;

    @BindView(R.id.tvCol2)
    AppCompatTextView textViewCol2;

    @BindView(R.id.tvCol3)
    AppCompatTextView textViewCol3;

    @BindView(R.id.tvCol4)
    AppCompatTextView textViewCol4;

    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;


    //showReceiptDetailsDialog

    InvoiceAdapter invoiceAdapter;
    QuotationsAdapter quotationsAdapter;
    EnquiriesAdapter enquiriesAdapter;

    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    private String frmSCR = "1";
    List<DashModel> dashboardList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_360);
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
        receiveData();
        layInvoices.setTextColor(getResources().getColor(R.color.white));
        layInvoices.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_corners_8_blue));
        layQuotations.setTextColor(getResources().getColor(R.color.back_text_colour));
        layQuotations.setBackground(null);
        layEnquiries.setTextColor(getResources().getColor(R.color.back_text_colour));
        layEnquiries.setBackground(null);
        textViewCol3.setVisibility(View.VISIBLE);
        textViewCol1.setText(getString(R.string.scr_lbl_lr_date));
        textViewCol2.setText(getString(R.string.scr_lbl_invoice_no));
        textViewCol3.setText(getString(R.string.scr_lbl_quoted_no));
        textViewCol4.setText(getString(R.string.scr_lbl_billed_amt));

        dashboardList.add(new DashModel("Negotiation","23/10/2021",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Tender","23/10/2021",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("PI","23/10/2021",getDrawable(R.drawable.ic_om_rating)));

        setAdapterForInvoiceList();
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

    private void receiveData()
    {
        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();
        frmSCR = i.getStringExtra(Constants.TRANSACTION_ID);
    }


    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport, 0, R.id.imgCall);
    }

    //show company info 360 view popup
    public void show360iewDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_company_info);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
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
    @OnClick({R.id.layEnquiries,R.id.layQuotations,R.id.layInvoices,R.id.imgInfoBtn,R.id.tvCompanyName})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvCompanyName:
                if(frmSCR.equals("0")) {
                    //showReceiptDetailsDialog();
                }
                break;
            case R.id.layInvoices:
                layInvoices.setTextColor(getResources().getColor(R.color.white));
                layInvoices.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_corners_8_blue));
                layQuotations.setTextColor(getResources().getColor(R.color.back_text_colour));
                layQuotations.setBackground(null);
                layEnquiries.setTextColor(getResources().getColor(R.color.back_text_colour));
                layEnquiries.setBackground(null);
                textViewCol3.setVisibility(View.VISIBLE);
                textViewCol1.setText(getString(R.string.scr_lbl_lr_date));
                textViewCol2.setText(getString(R.string.scr_lbl_invoice_no));
                textViewCol3.setText(getString(R.string.scr_lbl_quoted_no));
                textViewCol4.setText(getString(R.string.scr_lbl_billed_amt));
                setAdapterForInvoiceList();
                break;
            case R.id.layQuotations:
                layQuotations.setTextColor(getResources().getColor(R.color.white));
                layQuotations.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_corners_8_blue));
                layInvoices.setTextColor(getResources().getColor(R.color.back_text_colour));
                layInvoices.setBackground(null);
                layEnquiries.setTextColor(getResources().getColor(R.color.back_text_colour));
                layEnquiries.setBackground(null);
                textViewCol3.setVisibility(View.VISIBLE);
                textViewCol1.setText(R.string.scr_lbl_sent_date);
                textViewCol2.setText(getString(R.string.scr_lbl_quoted_no));
                textViewCol3.setText(R.string.scr_lbl_quoted_amt);
                textViewCol4.setText(getString(R.string.scr_lbl_status));
                setAdapterForQuotationsList();
                break;
            case R.id.layEnquiries:
                layEnquiries.setTextColor(getResources().getColor(R.color.white));
                layEnquiries.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_corners_8_blue));
                layInvoices.setTextColor(getResources().getColor(R.color.back_text_colour));
                layInvoices.setBackground(null);
                layQuotations.setTextColor(getResources().getColor(R.color.back_text_colour));
                layQuotations.setBackground(null);
                textViewCol1.setText(R.string.scr_lbl_lr_date);
                textViewCol2.setText(R.string.scr_lbl_enquiry);
                textViewCol3.setVisibility(View.GONE);
                textViewCol4.setText(getString(R.string.scr_lbl_status));
                setAdapterForEnquiriesList();
                break;

            case R.id.imgInfoBtn:
                show360iewDialog();
                break;
        }
    }

    //set invoice list
    private void setAdapterForInvoiceList() {
        if (dashboardList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
        } else {
            rvSalesList.setVisibility(View.GONE);
        }
        invoiceAdapter = new InvoiceAdapter(mContext, dashboardList, new InvoiceAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {
                //showReceiptDetailsDialog();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(invoiceAdapter);
        final boolean[] check = {false};
        if(frmSCR.equals("0")) {
            rvSalesList.addOnItemTouchListener(
                    new RecyclerView.OnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            if (!check[0]) {
                                //showReceiptDetailsDialog();
                                check[0] = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        check[0] = false;
                                    }
                                }, 50);
                            }
                            return false;

                        }

                        @Override
                        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            // Toast.makeText(mContext, "View where A: " + rv.getAdapter().getItemCount() + " is Clicked", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                        }
                    }
            );
        }
    }

    //set Quotations list
    private void setAdapterForQuotationsList() {
        if (dashboardList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
        } else {
            rvSalesList.setVisibility(View.GONE);
        }
        quotationsAdapter = new QuotationsAdapter(mContext, dashboardList, new QuotationsAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {
            //showReceiptDetailsDialog();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(quotationsAdapter);
        final boolean[] check = {false};
        if(frmSCR.equals("0")) {
            rvSalesList.addOnItemTouchListener(
                    new RecyclerView.OnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            if (!check[0]) {
                                //showReceiptDetailsDialog();
                                check[0] = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        check[0] = false;
                                    }
                                }, 50);
                            }
                            return false;

                        }

                        @Override
                        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            // Toast.makeText(mContext, "View where A: " + rv.getAdapter().getItemCount() + " is Clicked", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                        }
                    }
            );
        }
    }

    //set Enquiries list
    private void setAdapterForEnquiriesList() {
        /*dashboardList.removeAll(dashboardList);
        dashboardList.add(new DashModel("CRM/20-21/EQ/6773","23/10/2021",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("CRM/20-21/EQ/6773","23/10/2021",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("CRM/20-21/EQ/6773","23/10/2021",getDrawable(R.drawable.ic_om_rating)));
*/
        if (dashboardList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
        } else {
            rvSalesList.setVisibility(View.GONE);
        }
        enquiriesAdapter = new EnquiriesAdapter(mContext, dashboardList, new EnquiriesAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {
               // showReceiptDetailsDialog();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(enquiriesAdapter);
        final boolean[] check = {false};
        if(frmSCR.equals("0")) {
            rvSalesList.addOnItemTouchListener(
                    new RecyclerView.OnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            if (!check[0]) {
                                //showReceiptDetailsDialog();
                                check[0] = true;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        check[0] = false;
                                    }
                                }, 50);
                            }
                            return false;

                        }

                        @Override
                        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                            // Toast.makeText(mContext, "View where A: " + rv.getAdapter().getItemCount() + " is Clicked", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                        }
                    }
            );
        }
    }

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