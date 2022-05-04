package com.ominfo.crm_solution.ui.sales_credit.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class PdfPrintActivity extends BaseActivity {

    Context mContext;

    @BindView(R.id.myWebView)
    WebView myWebView;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.printButton)
    AppCompatButton printButton;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    WebView printWeb;
    String url = "",tranId="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_print);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        //getDeps().inject(this);
        ButterKnife.bind(this);
        init(savedInstanceState);

    }

    private void init(Bundle savedInstanceState){
        Window window = getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,this);
        //mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        //tvPage.setText("Showing 01 to 05 of\n12 Entries");
        setToolbar();
        //webView.setWebViewClient(new MyBrowser());
        Intent intent = getIntent();
        if(intent!=null){
                url = intent.getStringExtra(Constants.URL);
                try{tranId= intent.getStringExtra(Constants.TRANSACTION_ID);}catch (Exception e){}
        }
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        myWebView.getSettings().setDomStorageEnabled(true);
       // myWebView.getSettings().setLoadWithOverviewMode(true);
        //myWebView.getSettings().setUseWideViewPort(true);
        //This will zoom out the WebView
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.setInitialScale(1);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        //myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(true);

        myWebView.getSettings().setSupportMultipleWindows(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.loadUrl(url);
        myWebView.setVerticalScrollBarEnabled(true);
        myWebView.setHorizontalScrollBarEnabled(true);
        ((BaseActivity) mContext).showSmallProgressBar(mProgressBarHolder);
//add webview client to handle event of loading
        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                //if page loaded successfully then show print button
                if(!tranId.equals("acc")) {
                    printButton.setVisibility(View.VISIBLE);
                }
            }
        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPDF(view);
            }
        });

        //prepare your html content which will be show in webview
        String htmlDocument = "<html><body>" +
                "<h1>Webview Print Test </h1>" +
                "<h2>I am Webview</h2>" +
                "<p> By PointOfAndroid</p>" +
                "<p> This is some sample content.</p>" +
                "<p> By PointOfAndroid</p>" +
                "<p> This is some sample content.</p>" +
                "<p> By PointOfAndroid</p>" +
                "<p> This is some sample content.</p>" +
                "" +
                "" +
                "" + "Put your content here" +
                "" +
                "" +
                "</body></html>";

        //load your html to webview
        //myWebView.loadData(url, "text/HTML", "UTF-8");
    }

    //create a function to create the print job
    private void createWebPrintJob(WebView webView) {

        //create object of print manager in your device
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        //create object of print adapter
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        //provide name to your newly generated pdf file
        String jobName = getString(R.string.app_name) + " Print Test";

        //open print dialog
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }

    //perform click pdf creation operation on click of print button click
    public void printPDF(View view) {
        createWebPrintJob(myWebView);
    }

    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport,tvNotifyCount, 0, R.id.imgCall);
    }

}