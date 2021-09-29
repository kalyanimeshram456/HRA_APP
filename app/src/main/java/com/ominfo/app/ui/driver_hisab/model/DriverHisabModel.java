
package com.ominfo.app.ui.driver_hisab.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DriverHisabModel {

    @SerializedName("driver_hisab_title")
    private String driverHisabTitle;

    @SerializedName("driver_hisab_value")
    private String driverHisabValue;

    @SerializedName("img_bitmap")
    private Bitmap imgBitmap;

    public DriverHisabModel(String driverHisabTitle, String driverHisabValue) {
        this.driverHisabTitle = driverHisabTitle;
        this.driverHisabValue = driverHisabValue;
    }

    public DriverHisabModel(String driverHisabTitle, String driverHisabValue, Bitmap imgBitmap) {
        this.driverHisabTitle = driverHisabTitle;
        this.driverHisabValue = driverHisabValue;
        this.imgBitmap = imgBitmap;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public String getDriverHisabTitle() {
        return driverHisabTitle;
    }

    public void setDriverHisabTitle(String driverHisabTitle) {
        this.driverHisabTitle = driverHisabTitle;
    }

    public String getDriverHisabValue() {
        return driverHisabValue;
    }

    public void setDriverHisabValue(String driverHisabValue) {
        this.driverHisabValue = driverHisabValue;
    }

    @Override
    public String toString() {
        return "DiverHisabModel{" +
                "driverHisabTitle='" + driverHisabTitle + '\'' +
                ", driverHisabValue='" + driverHisabValue + '\'' +
                '}';
    }
}
