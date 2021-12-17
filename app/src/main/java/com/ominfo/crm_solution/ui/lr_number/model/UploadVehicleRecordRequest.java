
package com.ominfo.crm_solution.ui.lr_number.model;

import androidx.room.ColumnInfo;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UploadVehicleRecordRequest {

    @SerializedName("Branch_ID")
    private String mBranchID;
    @SerializedName("No_Of_LR")
    private String mNoOfLR;
    @SerializedName("Photo_Xml")
    private List<UploadVehicleImage> mPhotoXml;
    @SerializedName("Plant_ID")
    private String mPlantID;
    @ColumnInfo(name = "generated_id")
    @Expose
    @SerializedName("generated_id")
    private String mGeneratedId;
    @SerializedName("Transaction_Date")
    private String mTransactionDate;
    @SerializedName("Transaction_ID")
    private String mTransactionID;
    @SerializedName("User_ID")
    private String mUserID;
    @SerializedName("userkey")
    private String mUserkey;
    @SerializedName("Vehicle_ID")
    private String mVehicleID;

    public String getBranchID() {
        return mBranchID;
    }

    public void setBranchID(String branchID) {
        mBranchID = branchID;
    }

    public String getGeneratedId() {
        return mGeneratedId;
    }

    public void setGeneratedId(String mGeneratedId) {
        this.mGeneratedId = mGeneratedId;
    }

    public String getNoOfLR() {
        return mNoOfLR;
    }

    public void setNoOfLR(String noOfLR) {
        mNoOfLR = noOfLR;
    }

    public List<UploadVehicleImage> getPhotoXml() {
        return mPhotoXml;
    }

    public void setPhotoXml(List<UploadVehicleImage> photoXml) {
        mPhotoXml = photoXml;
    }

    public String getPlantID() {
        return mPlantID;
    }

    public void setPlantID(String plantID) {
        mPlantID = plantID;
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

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String getUserkey() {
        return mUserkey;
    }

    public void setUserkey(String userkey) {
        mUserkey = userkey;
    }

    public String getVehicleID() {
        return mVehicleID;
    }

    public void setVehicleID(String vehicleID) {
        mVehicleID = vehicleID;
    }

}
