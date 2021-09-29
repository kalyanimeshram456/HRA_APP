
package com.ominfo.app.ui.kata_chithi.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SaveKataChitthiRequest {

    @SerializedName("Driver_ID")
    private Long mDriverID;
    @SerializedName("Photo_Xml")
    private List<String> mPhotoXml;
    @SerializedName("Transaction_Date")
    private String mTransactionDate;
    @SerializedName("Transaction_ID")
    private Long mTransactionID;
    @SerializedName("User_ID")
    private Long mUserID;
    @SerializedName("userkey")
    private String mUserkey;
    @SerializedName("VehicleID")
    private Long mVehicleID;

    public Long getDriverID() {
        return mDriverID;
    }

    public void setDriverID(Long driverID) {
        mDriverID = driverID;
    }

    public List<String> getPhotoXml() {
        return mPhotoXml;
    }

    public void setPhotoXml(List<String> photoXml) {
        mPhotoXml = photoXml;
    }

    public String getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        mTransactionDate = transactionDate;
    }

    public Long getTransactionID() {
        return mTransactionID;
    }

    public void setTransactionID(Long transactionID) {
        mTransactionID = transactionID;
    }

    public Long getUserID() {
        return mUserID;
    }

    public void setUserID(Long userID) {
        mUserID = userID;
    }

    public String getUserkey() {
        return mUserkey;
    }

    public void setUserkey(String userkey) {
        mUserkey = userkey;
    }

    public Long getVehicleID() {
        return mVehicleID;
    }

    public void setVehicleID(Long vehicleID) {
        mVehicleID = vehicleID;
    }

}
