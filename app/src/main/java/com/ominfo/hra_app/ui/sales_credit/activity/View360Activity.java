package com.ominfo.hra_app.ui.sales_credit.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiriesAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiryPageAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.InvoiceAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.QuotationsAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.CustomerAllRecord;
import com.ominfo.hra_app.ui.sales_credit.model.CustomerData;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.ui.sales_credit.model.GetView360Request;
import com.ominfo.hra_app.ui.sales_credit.model.GetView360Response;
import com.ominfo.hra_app.ui.sales_credit.model.View30ViewModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    @BindView(R.id.tvCreditAmtColon)
    AppCompatTextView tvCreditAmtColon;
    @BindView(R.id.tvlayCredDaysColon)
    AppCompatTextView tvlayCredDaysColon;
    @BindView(R.id.imgNew)
    AppCompatButton imgNew;
    @BindView(R.id.imgWeChat)
    CircleImageView imgWeChat;
    @BindView(R.id.imgWhatsApp)
    CircleImageView imgWhatsApp;
    @BindView(R.id.imgEmail)
    CircleImageView imgEmail;
    @BindView(R.id.tvCreditAmtValue)
    AppCompatTextView tvCreditAmtValue;
    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.tvRmName)
    AppCompatTextView tvRmName;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvPage)
    AppCompatTextView tvPage;
    InvoiceAdapter invoiceAdapter;
    QuotationsAdapter quotationsAdapter;
    EnquiriesAdapter enquiriesAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.imgSales)
    CircleImageView imgSales;
    private String frmSCR = "0",number = "",email = "";
    List<CustomerAllRecord> customerAllRecordList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private View30ViewModel view30ViewModel;
    private AppDatabase mDb;
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    EnquiryPageAdapter enquiryPageAdapter;
    private String pagerClicked = "No";
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    CustomerData customerData;
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
        injectAPI();
        init();
    }

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        //tvPage.setText("Showing 01 to 05 of\n12 Entries");
        setToolbar();
        receiveData();
        setEnquiryPagerList(1);
        callGetView360Api("0");
        layInvoices.setTextColor(getResources().getColor(R.color.white));
        layInvoices.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_corners_8_blue));
        layQuotations.setTextColor(getResources().getColor(R.color.back_text_colour));
        layQuotations.setBackground(null);
        layEnquiries.setTextColor(getResources().getColor(R.color.back_text_colour));
        layEnquiries.setBackground(null);
        textViewCol3.setVisibility(View.VISIBLE);
        textViewCol1.setText("Type");
        textViewCol2.setText(getString(R.string.scr_lbl_lr_date));
        textViewCol3.setText("Doc No");
        textViewCol4.setText(getString(R.string.scr_lbl_amount));
        setAdapterForInvoiceList();
    }
    private void injectAPI() {
        view30ViewModel = ViewModelProviders.of(this, mViewModelFactory).get(View30ViewModel.class);
        view30ViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VIEW360));
    }
    /* Call Api For View 360 */
    private void callGetView360Api(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_view360);
                RequestBody mRequestBodyTypeCustId = RequestBody.create(MediaType.parse("text/plain"), frmSCR);//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId()/*"96"*/);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId()/*"135"*/);
                RequestBody mRequestBodyTypePageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);//selectedRM.getEmpId());
                RequestBody mRequestBodyTypePageSize = RequestBody.create(MediaType.parse("text/plain"), "5"/*Constants.MIN_PAG_SIZE*/);
                GetView360Request getView360Request = new GetView360Request();
                getView360Request.setAction(mRequestBodyAction);
                getView360Request.setEmployee(mRequestBodyTypeEmpId);
                getView360Request.setCompanyId(mRequestBodyTypeComId);
                getView360Request.setCustId(mRequestBodyTypeCustId);
                getView360Request.setPageNumber(mRequestBodyTypePageNo);
                getView360Request.setPageSize(mRequestBodyTypePageSize);

                view30ViewModel.hitView30Api(getView360Request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setPagerEnquiryList(long pageNo){
        for(int i=0;i<pageNo;i++) {
            if (pagerClicked.equals("No")) {
                if (i == 0) {
                    rvEnquiryPager.scrollToPosition(i+1);
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 1));
                } else {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 0));
                }
            } else {
                if (i == Integer.parseInt(pagerClicked)) {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 1));
                } else {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 0));
                }
            }
        }
    }

    private void setEnquiryPagerList(long pageNo) {
        enquiryPageList.clear();
        if(pageNo==0) {
            pageNo = 1;
        }
        setPagerEnquiryList(pageNo);
        if (enquiryPageList.size() > 0) {
            rvEnquiryPager.setVisibility(View.VISIBLE);
        } else {
            rvEnquiryPager.setVisibility(View.GONE);
        }
        rvEnquiryPager.setHasFixedSize(true);
        //rvEnquiryPager.setLayoutManager(new GridLayoutManager(mContext, 3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rvEnquiryPager.setLayoutManager(layoutManager);
        rvEnquiryPager.setItemAnimator(new DefaultItemAnimator());
        rvEnquiryPager.setAdapter(enquiryPageAdapter);
        try{
            rvEnquiryPager.scrollToPosition(Integer.parseInt(pagerClicked));}catch (Exception e){e.printStackTrace();}

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
        if(i!=null) {
            frmSCR = i.getStringExtra(Constants.TRANSACTION_ID);
        }
    }


    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        Window window = getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,this);
        initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport,tvNotifyCount, 0, R.id.imgCall);
    }

    //show company info 360 view popup
    public void show360iewDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_company_info);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);
        CircleImageView imgPopCust = mDialog.findViewById(R.id.imgPopCust);
        AppCompatTextView tvCustName = mDialog.findViewById(R.id.tvCustName);
        CircleImageView imgWeChat =  mDialog.findViewById(R.id.imgWeChat);
        CircleImageView imgWhatsApp =  mDialog.findViewById(R.id.imgWhatsApp);
        CircleImageView imgEmail =  mDialog.findViewById(R.id.imgEmail);
        AppCompatTextView tvRmValue = mDialog.findViewById(R.id.tvRmValue);
        AppCompatTextView tvAddressValue = mDialog.findViewById(R.id.tvAddressValue);
        AppCompatTextView tvGSTValue = mDialog.findViewById(R.id.tvGSTValue);
        AppCompatTextView tvCreditAmtColon = mDialog.findViewById(R.id.tvCreditAmtColon);
        AppCompatTextView tvCreditAmtValue = mDialog.findViewById(R.id.tvCreditAmtValue);
        AppCompatTextView tvlayCredDaysColon = mDialog.findViewById(R.id.tvlayCredDaysColon);
        AppCompatTextView tvEmployeeColon = mDialog.findViewById(R.id.tvEmployeeColon);
        AppCompatImageView imgEmployeeValue = mDialog.findViewById(R.id.tvEmployeeValue);
        AppCompatTextView tvEmployee2Colon = mDialog.findViewById(R.id.tvEmployee2Colon);
        AppCompatImageView imgEmployee2Value = mDialog.findViewById(R.id.tvEmployee2Value);
        AppCompatButton imgGold = mDialog.findViewById(R.id.imgGold);
        AppCompatButton imgNew = mDialog.findViewById(R.id.imgNew);

        AppUtils.loadImageURL(mContext,"https://ominfo.in/crm/"+customerData.getCustomerDetails().getEmpProfilePic(),
                imgPopCust, null);
        tvCustName.setText(customerData.getCustomerDetails().getCompanyName());
        tvRmValue.setText(customerData.getCustomerDetails().getEmpName());
        tvCreditAmtColon.setText(customerData.getCustomerCreditBalance());
        tvlayCredDaysColon.setText(customerData.getCustomerDetails().getCreditLimitDays()+" Days");
        tvCreditAmtValue.setText("("+getString(R.string.scr_lbl_rs)+customerData.getCustomerDetails().getCreditLimitMoney()+")");
        if(customerData.getCustomerDetails().getIsnew().equals("YES")) {
            imgNew.setVisibility(View.VISIBLE);
        }else{ imgNew.setVisibility(View.INVISIBLE);}
        String[] str = customerData.getCustomerCreditBalance().split("₹");
        try{
            double amount = Double.parseDouble(str[1]);
            if(amount>0){
                tvCreditAmtColon.setTextColor(getResources().getColor(R.color.Dark_Red));
            }
            else{
                tvCreditAmtColon.setTextColor(getResources().getColor(R.color.green));
            }
        }catch (Exception e){}
        tvAddressValue.setText(customerData.getCustomerDetails().getRegisteredAddress());
        tvGSTValue.setText(customerData.getCustomerDetails().getGstNO());
        String emp1N = "",emp1D= "",emp2N = "",emp2D= "";
        emp1N = customerData.getCustomerDetails().getEmpName1()
                ==null || customerData.getCustomerDetails().getEmpName1().equals("")?
                getString(R.string.scr_lbl_unavailable):customerData.getCustomerDetails().getEmpName1();
        emp1D = customerData.getCustomerDetails().getEmpDesignation1()==null
                || customerData.getCustomerDetails().getEmpDesignation1().equals("")?
                getString(R.string.scr_lbl_unavailable):customerData.getCustomerDetails().getEmpDesignation1();
        emp2N = customerData.getCustomerDetails().getEmpName2()==null
                || customerData.getCustomerDetails().getEmpName2().equals("")?getString(R.string.scr_lbl_unavailable):customerData.getCustomerDetails().getEmpName2();
       emp2D =  customerData.getCustomerDetails().getEmpDesignation2()==null
               || customerData.getCustomerDetails().getEmpDesignation2().equals("")?getString(R.string.scr_lbl_unavailable):customerData.getCustomerDetails().getEmpDesignation2();

        tvEmployeeColon.setText(emp1N+" ("+emp1D+")");
        tvEmployee2Colon.setText(emp2N+" ("+emp2D+")");

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        imgWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                setCallDialor(number);
            }
        });
        imgEmployeeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                setCallDialor(customerData.getCustomerDetails().getEmpMob1());
            }
        });
        imgEmployee2Value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                setCallDialor(customerData.getCustomerDetails().getEmpMob2());
            }
        });
        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                openMail(email);
            }
        });
        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                openWhatsApp(number);
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
              public void composeEmail(String[] addresses, String subject) {
                  Intent intent = new Intent(Intent.ACTION_SENDTO);
                  intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                  intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                  intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                  if (intent.resolveActivity(getPackageManager()) != null) {
                      startActivity(intent);
                  }
              }

              private void openWhatsApp(String number){
                  String url = "https://api.whatsapp.com/send?phone="+"+91 "+number;
                  Intent i = new Intent(Intent.ACTION_VIEW);
                  i.setData(Uri.parse(url));
                  startActivity(i);
              }

              private void openMail(String email){
                  Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                  emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TuranthBiz Services");
                  emailIntent.putExtra(Intent.EXTRA_TEXT, "body");
                  //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
                  startActivity(Intent.createChooser(emailIntent, "Mail to "+tvCompanyName.getText().toString()+" via"));

              }

    //perform click actions
    @OnClick({R.id.layEnquiries,R.id.layQuotations,R.id.layInvoices,R.id.imgInfoBtn,R.id.tvCompanyName,
            R.id.imgWeChat,R.id.imgWhatsApp,R.id.imgEmail})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgWeChat:
                setCallDialor(number);
                break;
            case R.id.imgWhatsApp:
                openWhatsApp(number);
                break;
            case R.id.imgEmail:
                openMail(email);
                 break;
            case R.id.tvCompanyName:
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
                if(customerData!=null) {
                    show360iewDialog();
                }else{
                    LogUtil.printToastMSG(mContext,"No data available!");
                }
                break;
        }
    }

    //set invoice list
    private void setAdapterForInvoiceList() {
       /* List<CustomerAllRecord> tempList = new ArrayList<>();
        for(int i=0;i<customerAllRecordList.size();i++){
            if(customerAllRecordList.get(i).getDoctype().equals("SALES ORDER")){
                tempList.add(customerAllRecordList.get(i));
            }
        }*/
        if (customerAllRecordList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            for(int i=0;i<customerAllRecordList.size();i++){

            }
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
        invoiceAdapter = new InvoiceAdapter(mContext, customerAllRecordList, new InvoiceAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(CustomerAllRecord mDataTicket) {
                //showReceiptDetailsDialog();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(invoiceAdapter);
        final boolean[] check = {false};
    }

    //set Quotations list
    private void setAdapterForQuotationsList() {
        if (customerAllRecordList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
        quotationsAdapter = new QuotationsAdapter(mContext, customerAllRecordList, new QuotationsAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(CustomerAllRecord mDataTicket) {
            //showReceiptDetailsDialog();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(quotationsAdapter);
        final boolean[] check = {false};
    }

    //set Enquiries list
    private void setAdapterForEnquiriesList() {
        /*dashboardList.removeAll(dashboardList);
        dashboardList.add(new DashModel("CRM/20-21/EQ/6773","23/10/2021",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("CRM/20-21/EQ/6773","23/10/2021",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("CRM/20-21/EQ/6773","23/10/2021",getDrawable(R.drawable.ic_om_rating)));
*/
        if (customerAllRecordList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
        enquiriesAdapter = new EnquiriesAdapter(mContext, customerAllRecordList, new EnquiriesAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(CustomerAllRecord mDataTicket) {
               // showReceiptDetailsDialog();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(enquiriesAdapter);
        final boolean[] check = {false};
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

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                ((BaseActivity) mContext).showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_VIEW360)) {
                            GetView360Response responseModel = new Gson().fromJson(apiResponse.data.toString(), GetView360Response.class);
                            if (responseModel != null/* && responseModel.getResult().getStatus().equals("success")*/) {
                                customerData = responseModel.getResult().getCustomerData();
                                tvCompanyName.setText(customerData.getCustomerDetails().getCompanyName());
                                number = customerData.getCustomerDetails().getContactNo();
                                email = customerData.getCustomerDetails().getEmailId();
                                tvRmName.setText(customerData.getCustomerDetails().getEmpName());
                                tvCreditAmtColon.setText(customerData.getCustomerCreditBalance());
                                tvlayCredDaysColon.setText(customerData.getCustomerDetails().getCreditLimitDays()+" Days");
                                tvCreditAmtValue.setText("("+getString(R.string.scr_lbl_rs)+customerData.getCustomerDetails().getCreditLimitMoney()+")");
                                if(customerData.getCustomerDetails().getIsnew().equals("YES")) {
                                    imgNew.setVisibility(View.VISIBLE);
                                }else{ imgNew.setVisibility(View.INVISIBLE);}
                                String[] str = customerData.getCustomerCreditBalance().split("₹");
                                try{
                                    double amount = Double.parseDouble(str[1]);
                                    if(amount>0){
                                        tvCreditAmtColon.setTextColor(getResources().getColor(R.color.Dark_Red));
                                    }
                                    else{
                                        tvCreditAmtColon.setTextColor(getResources().getColor(R.color.green));
                                    }
                                }catch (Exception e){}
                                //LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                long totalPage = 0;
                                AppUtils.loadImageURL(mContext,"https://ominfo.in/crm/"+customerData.getCustomerDetails().getEmpProfilePic(),imgSales, null);

                                //tvTotalCount.setText(getString(R.string.scr_lbl_rs)+String.valueOf(responseModel.getResult().getTotalenquiries()));
                                try {
                                    if (customerData.getAllRecords() != null && customerData.getAllRecords().size()>0) {
                                        customerAllRecordList.clear();
                                        customerAllRecordList = customerData.getAllRecords();
                                        totalPage=responseModel.getResult().getTotalpages();

                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + customerAllRecordList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalenquiries()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*5)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*5)+ customerAllRecordList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalenquiries()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage=0;
                                        customerAllRecordList.clear();
                                    }

                                }catch(Exception e){
                                    e.printStackTrace();
                                    totalPage=0;
                                    customerAllRecordList.clear();
                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForInvoiceList();
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}

                }
                break;
            case ERROR:
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}