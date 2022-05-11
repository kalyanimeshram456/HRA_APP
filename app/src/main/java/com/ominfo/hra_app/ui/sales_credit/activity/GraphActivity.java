package com.ominfo.hra_app.ui.sales_credit.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.sales_credit.adapter.SalesGraphAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GraphActivity extends BaseActivity {
    SalesGraphAdapter salesCreditAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;

    @BindView(R.id.imgRight)
    AppCompatButton imgRight;

    @BindView(R.id.imgOverall)
    AppCompatButton imgOverall;

    @BindView(R.id.imgGroup)
    AppCompatButton imgGroup;

    @BindView(R.id.imgLeft)
    AppCompatButton imgLeft;

    List<GraphModel> dashboardList = new ArrayList<>();
    List<GraphModel> dashboardListTest = new ArrayList<>();

    // variable for our bar chart
    BarChart barChart;
    Context mContext;
    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mContext = this;
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        dashboardList.removeAll(dashboardList);
        dashboardList.add(new GraphModel("State C1", "C1", "5"));
        dashboardList.add(new GraphModel("State C2", "C2", "60"));
        dashboardList.add(new GraphModel("State C3", "C3", "15"));
        dashboardList.add(new GraphModel("State C4", "C4", "70"));
        dashboardList.add(new GraphModel("State C5", "C5", "25"));
        dashboardList.add(new GraphModel("State C6", "C6", "10"));
        dashboardList.add(new GraphModel("State C7", "C7", "45"));
        dashboardList.add(new GraphModel("State C8", "C8", "90"));
        dashboardList.add(new GraphModel("State C9", "C9", "95"));
        dashboardList.add(new GraphModel("State C10", "C10", "50"));
        dashboardList.add(new GraphModel("State C11", "C11", "55"));
        dashboardList.add(new GraphModel("State C12", "C12", "60"));
        setGraphData(0);
    }

    private void setGraphData(int initStatus) {
        if(initStatus!=3) {
            DAYS = new String[6];
            DAYSY = new String[6];
        }
        if(initStatus==3){
            DAYS = new String[dashboardList.size()+1];
            DAYSY = new String[dashboardList.size()+1];
        }
        if(initStatus==1) {
            startPos++;
            barDataSet.clear();
            barChart.notifyDataSetChanged();
            barChart.invalidate();
        }
        if(initStatus==2) {
            if(startPos>0) {
                startPos--;
                imgLeft.setEnabled(true);
                imgLeft.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
            }
            else {
                imgLeft.setEnabled(false);
                imgLeft.setBackground(mContext.getResources().getDrawable(R.color.app_gray));
            }
            barDataSet.clear();
            barChart.notifyDataSetChanged();
            barChart.invalidate();
        }


        if(initStatus!=3) {
            try {
                endPos = startPos + 6;
                if (endPos <= dashboardList.size()) {
                    //if(startPos<6) {
                    imgRight.setEnabled(true);
                    imgRight.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));

                    for (int i = startPos; i < endPos; i++) {
                        //if (i < endPos) {
                            dashboardListTest.add(dashboardList.get(i));
                        //}
                    }
                    for (int i = 0; i < dashboardListTest.size(); i++) {
                        if (dashboardListTest.get(i).getxValue() != null) {
                            DAYS[i] = dashboardListTest.get(i).getxValue();
                        }
                        if (dashboardListTest.get(i).getyValue() != null) {
                            DAYSY[i] = dashboardListTest.get(i).getyValue();
                        }
                    }
                    try {
                        getGraph();
                        setAdapterForDashboardList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // }
                } else {
                    if (startPos == dashboardList.size()) {
                        imgRight.setEnabled(false);
                        imgRight.setBackground(mContext.getResources().getDrawable(R.color.app_gray));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(initStatus==3){
             for (int i = 0; i < dashboardList.size(); i++) {
                    //if(i<=dashboardList.size()) {
                        dashboardListTest.add(dashboardList.get(i));
                   // }
                }
                for (int i = 0; i < dashboardListTest.size(); i++) {
                    if(dashboardListTest.get(i).getxValue()!=null) {
                        DAYS[i] = dashboardListTest.get(i).getxValue();
                    }
                    if(dashboardListTest.get(i).getyValue()!=null) {
                        DAYSY[i] = dashboardListTest.get(i).getyValue();
                    }
                }
                try {
                    getGraph();
                    setAdapterForDashboardList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private void setAdapterForDashboardList() {
        if (dashboardListTest.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
        } else {
            rvSalesList.setVisibility(View.GONE);
        }
        salesCreditAdapter = new SalesGraphAdapter(mContext, dashboardListTest, new SalesGraphAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {

            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(salesCreditAdapter);
    }

    private void getGraph() {
        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);
        barChart.getDescription().setEnabled(false);
        barChart.getDescription().setTextAlign(Paint.Align.LEFT);
        barChart.setDrawValueAboveBar(true);
        barChart.animateY(1000);

        // calling method to get bar entries.
        BarData data = getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Test");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.

        barChart.setData(data);
        data.setBarWidth(0.6f);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        XAxis xAxis = barChart.getXAxis();
        //set labels des to bottom
        xAxis.setLabelCount(DAYSY.length, false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //set x axis label values
        //xAxis.setLabelRotationAngle(90);
        try {
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    try {
                        return DAYS[(int) Math.floor(value)];
                    }catch (Exception e){
                        return "";
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        YAxis axisLeft = barChart.getAxisLeft();
        //axisLeft.setGranularity(0.6f);
        axisLeft.setAxisMinimum((int) Math.floor(0));
        axisLeft.setAxisMaximum((int) Math.floor(100));
        //customize - y axis rows numbers
        axisLeft.setLabelCount(5, true);
        axisLeft.setDrawGridLines(true);
        //set right side
        YAxis axisRight = barChart.getAxisRight();
        //axisRight.setGranularity(0.6f);
        axisRight.setAxisMinimum(0);
        axisRight.setAxisMaximum(0);
        axisRight.setLabelCount(0, false);
        axisRight.setDrawGridLines(true);
        axisRight.setAxisMaximum(0);
        barChart.setDrawValueAboveBar(true);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getDescription().setEnabled(false);
    }

    private BarData getBarEntries() {

        barEntriesArrayList = new ArrayList<>();
        for (int i = 0; i < DAYSY.length; i++) {
            Random r = new Random();
            float x = i;
            if(DAYSY[i]!=null) {
                float y = Integer.parseInt(DAYSY[i]);
                barEntriesArrayList.add(new BarEntry(x, y));
            }
        }

        BarDataSet set1 = new BarDataSet(barEntriesArrayList, "Test");
        set1.setBarBorderColor(Color.YELLOW);
        //set1.setColor(getResources().getColor(R.color.deep_yellow));
        set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        set1.setHighlightEnabled(false);
        set1.setDrawValues(true);

        dataSets.add(set1);
        // adding color to our bar data set.

        BarData data = new BarData(dataSets);
        return data;
    }

       //perform click actions
    @OnClick({R.id.imgRight,R.id.imgLeft,R.id.imgOverall,R.id.imgGroup})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgRight:
                try {
                    imgLeft.setEnabled(true);
                    imgRight.setEnabled(true);
                    imgLeft.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
                    imgRight.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
                    dashboardListTest.removeAll(dashboardListTest);
                    setGraphData(1);
                }catch (Exception e){
                    imgRight.setEnabled(false);
                    imgRight.setBackground(mContext.getResources().getDrawable(R.color.app_gray));
                    LogUtil.printToastMSG(mContext,"You have reached the end.");
                }
                break;
            case R.id.imgLeft:
                try{
                    imgLeft.setEnabled(true);
                    imgRight.setEnabled(true);
                    imgLeft.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
                    imgRight.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
                    dashboardListTest.removeAll(dashboardListTest);
                setGraphData(2);
                }catch (Exception e){
                    imgLeft.setEnabled(false);
                    imgLeft.setBackground(mContext.getResources().getDrawable(R.color.app_gray));
                    LogUtil.printToastMSG(mContext,"You have reached the end.");
                }
                break;
            case R.id.imgOverall:
                //DAYSY = null;
                //DAYS = null;
                imgGroup.setBackground(mContext.getResources().getDrawable(R.color.app_gray));
                imgOverall.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
                dashboardListTest.removeAll(dashboardListTest);
                startPos = 0;
                endPos = 0;
                setGraphData(3);
                imgRight.setVisibility(View.GONE);
                imgLeft.setVisibility(View.GONE);
                break;
            case R.id.imgGroup:
                dashboardListTest.removeAll(dashboardListTest);
                startPos = 0;
                endPos = 0;
                //DAYSY = null;
                //DAYS = null;
                barDataSet.clear();
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                imgGroup.setBackground(mContext.getResources().getDrawable(R.color.blue_graph));
                imgOverall.setBackground(mContext.getResources().getDrawable(R.color.app_gray));
                imgRight.setVisibility(View.VISIBLE);
                imgLeft.setVisibility(View.VISIBLE);
                init();
                break;
        }
    }

}