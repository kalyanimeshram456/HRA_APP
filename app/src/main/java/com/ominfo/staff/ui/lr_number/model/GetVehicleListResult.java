
package com.ominfo.staff.ui.lr_number.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "vehicle_List")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetVehicleListResult {

    @ColumnInfo(name = "No_Of_LR")
    @Expose
    @SerializedName("No_Of_LR")
    private String mNoOfLR;

    @ColumnInfo(name = "Plant")
    @Expose
    @SerializedName("Plant")
    private String mPlant;

    @ColumnInfo(name = "Transaction_Date")
    @Expose
    @SerializedName("Transaction_Date")
    private String mTransactionDate;

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "Transaction_ID")
    @Expose
    @SerializedName("Transaction_ID")
    private String mTransactionID;

    @ColumnInfo(name = "Vehicle_No")
    @Expose
    @SerializedName("Vehicle_No")
    private String mVehicleNo;

    public GetVehicleListResult(String mNoOfLR, String mPlant, String mTransactionDate, String mTransactionID, String mVehicleNo) {
        this.mNoOfLR = mNoOfLR;
        this.mPlant = mPlant;
        this.mTransactionDate = mTransactionDate;
        this.mTransactionID = mTransactionID;
        this.mVehicleNo = mVehicleNo;
    }

    public GetVehicleListResult() {
    }

    public String getNoOfLR() {
        return mNoOfLR;
    }

    public void setNoOfLR(String noOfLR) {
        mNoOfLR = noOfLR;
    }

    public String getPlant() {
        return mPlant;
    }

    public void setPlant(String plant) {
        mPlant = plant;
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

    public String getVehicleNo() {
        return mVehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        mVehicleNo = vehicleNo;
    }

}
