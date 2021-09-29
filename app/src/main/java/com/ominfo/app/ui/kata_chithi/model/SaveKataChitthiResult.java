
package com.ominfo.app.ui.kata_chithi.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SaveKataChitthiResult {

    @SerializedName("App_Document_ID")
    private String mAppDocumentID;
    @SerializedName("Driver_ID")
    private String mDriverID;
    @SerializedName("Driver_Name")
    private String mDriverName;
    @SerializedName("Transaction_Date")
    private String mTransactionDate;
    @SerializedName("Transaction_ID")
    private String mTransactionID;
    @SerializedName("urlprefix")
    private String mUrlprefix;
    @SerializedName("urls")
    private String mUrls;
    @SerializedName("Vehicle_ID")
    private String mVehicleID;
    @SerializedName("Vehicle_No")
    private String mVehicleNo;

    public String getAppDocumentID() {
        return mAppDocumentID;
    }

    public void setAppDocumentID(String appDocumentID) {
        mAppDocumentID = appDocumentID;
    }

    public String getDriverID() {
        return mDriverID;
    }

    public void setDriverID(String driverID) {
        mDriverID = driverID;
    }

    public String getDriverName() {
        return mDriverName;
    }

    public void setDriverName(String driverName) {
        mDriverName = driverName;
    }

    public String getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        mTransactionDate = transactionDate;
    }

    public String getTransactionID() {
        return mTransactionID;
    }

    public void setTransactionID(String transactionID) {
        mTransactionID = transactionID;
    }

    public String getUrlprefix() {
        return mUrlprefix;
    }

    public void setUrlprefix(String urlprefix) {
        mUrlprefix = urlprefix;
    }

    public String getUrls() {
        return mUrls;
    }

    public void setUrls(String urls) {
        mUrls = urls;
    }

    public String getVehicleID() {
        return mVehicleID;
    }

    public void setVehicleID(String vehicleID) {
        mVehicleID = vehicleID;
    }

    public String getVehicleNo() {
        return mVehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        mVehicleNo = vehicleNo;
    }

}
