
package com.ominfo.staff.ui.lr_number.model;

import android.net.Uri;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VehicleDetailsLrImage {

    @SerializedName("image")
    private String mImage;
    @SerializedName("lr")
    private String mLr;
    @SerializedName("image_uri")
    private String imageUri;
    @SerializedName("image_path")
    private String imagePath;

    public VehicleDetailsLrImage(String mLr,String mImage, String imageUri,String imagePath) {
        this.mImage = mImage;
        this.mLr = mLr;
        this.imageUri = imageUri;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public VehicleDetailsLrImage() {

    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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

    @Override
    public String toString() {
        return "VehicleDetailsLrImage{" +
                "mImage='" + mImage + '\'' +
                ", mLr='" + mLr + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
