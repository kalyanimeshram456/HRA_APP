
package com.ominfo.hra_app.ui.sales_credit.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GraphModel {

    @SerializedName("title")
    private String title;

    @SerializedName("x_value")
    private String xValue;

    @SerializedName("y_valu")
    private String yValue;

    public GraphModel(String title, String xValue, String yValue) {
        this.title = title;
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public String getyValue() {
        return yValue;
    }

    public void setyValue(String yValue) {
        this.yValue = yValue;
    }

    @Override
    public String toString() {
        return "DashModel{" +
                "title='" + title + '\'' +
                ", xValue='" + xValue + '\'' +
                ", yValue='" + yValue + '\'' +
                '}';
    }
}
