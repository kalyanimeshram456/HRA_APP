
package com.ominfo.crm_solution.ui.lr_number.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UploadVehicleImage {

    @SerializedName("image")
    private String mImage;
    @SerializedName("lr")
    private String mLr;

    public UploadVehicleImage(String mLr,String mImage) {
        this.mImage = mImage;
        this.mLr = mLr;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getLr() {
        return mLr;
    }

    public void setLr(String lr) {
        mLr = lr;
    }

}
