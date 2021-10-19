
package com.ominfo.staff.ui.lr_number.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Entity(tableName = "vehicle_details")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VehicleDetailsResultTable {

    @ColumnInfo(name = "lr_images")
    @Expose
    @SerializedName("lr_images")
    private List<UploadVehicleImage> mLrImages;

    @ColumnInfo(name = "No_Of_LR")
    @Expose
    @SerializedName("No_Of_LR")
    private String mNoOfLR;

    @ColumnInfo(name = "Plant_ID")
    @Expose
    @SerializedName("Plant_ID")
    private String mPlantID;

    @ColumnInfo(name = "Transaction_Date")
    @Expose
    @SerializedName("Transaction_Date")
    private String mTransactionDate;

    @ColumnInfo(name = "generated_id")
    @Expose
    @SerializedName("generated_id")
    private String mGeneratedId;

    @ColumnInfo(name = "User_ID")
    @Expose
    @SerializedName("User_ID")
    private String mUserID;

    @ColumnInfo(name = "Transaction_ID")
    @Expose
    @SerializedName("Transaction_ID")
    private String mTransactionID;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    @Expose
    @SerializedName("ID")
    private int id;

    @ColumnInfo(name = "Branch_ID")
    @Expose
    @SerializedName("Branch_ID")
    private String branchID;

    @ColumnInfo(name = "Vehicle_ID")
    @Expose
    @SerializedName("Vehicle_ID")
    private String mVehicleID;

    @ColumnInfo(name = "Vehicle_No")
    @Expose
    @SerializedName("Vehicle_No")
    private String mVehicleNo;

    @ColumnInfo(name = "upload_status")
    @Expose
    @SerializedName("upload_status")
    private int uploadStatus;

    @SerializedName("userkey")
    private String mUserkey;

    public String getUserkey() {
        return mUserkey;
    }

    public void setUserkey(String mUserkey) {
        this.mUserkey = mUserkey;
    }


    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getGeneratedId() {
        return mGeneratedId;
    }

    public void setGeneratedId(String mGeneratedId) {
        this.mGeneratedId = mGeneratedId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public List<UploadVehicleImage> getLrImages() {
        return mLrImages;
    }

    public void setLrImages(List<UploadVehicleImage> lrImages) {
        mLrImages = lrImages;
    }

    public String getNoOfLR() {
        return mNoOfLR;
    }

    public void setNoOfLR(String noOfLR) {
        mNoOfLR = noOfLR;
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

    @Override
    public String toString() {
        return "VehicleDetailsResultTable{" +
                "mLrImages=" + mLrImages +
                ", mNoOfLR='" + mNoOfLR + '\'' +
                ", mPlantID='" + mPlantID + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                ", mGeneratedId='" + mGeneratedId + '\'' +
                ", mUserID='" + mUserID + '\'' +
                ", mTransactionID='" + mTransactionID + '\'' +
                ", id=" + id +
                ", branchID='" + branchID + '\'' +
                ", mVehicleID='" + mVehicleID + '\'' +
                ", mVehicleNo='" + mVehicleNo + '\'' +
                ", uploadStatus=" + uploadStatus +
                ", mUserkey='" + mUserkey + '\'' +
                '}';
    }
}
