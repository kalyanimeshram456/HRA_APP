
package com.ominfo.staff.ui.lr_number.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchLrResult {

    @SerializedName("lr_images")
    private List<VehicleDetailsLrImage> mLrImages;
    @SerializedName("No_Of_LR")
    private String mNoOfLR;
    @SerializedName("Plant_ID")
    private String mPlantID;
    @SerializedName("Transaction_Date")
    private String mTransactionDate;
    @SerializedName("Transaction_ID")
    private String mTransactionID;
    @SerializedName("Vehicle_ID")
    private String mVehicleID;
    @SerializedName("Vehicle_No")
    private String mVehicleNo;

    public List<VehicleDetailsLrImage> getLrImages() {
        return mLrImages;
    }

    public void setLrImages(List<VehicleDetailsLrImage> lrImages) {
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

}
