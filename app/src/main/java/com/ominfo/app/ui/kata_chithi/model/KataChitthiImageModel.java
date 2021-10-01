
package com.ominfo.app.ui.kata_chithi.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class KataChitthiImageModel {

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("image_type")
    private int imageType;

    @SerializedName("image_bitmap")
    private Bitmap imageBitmap;

    public KataChitthiImageModel(String imagePath, int imageType, Bitmap imageBitmap) {
        this.imagePath = imagePath;
        this.imageType = imageType;
        this.imageBitmap = imageBitmap;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    @Override
    public String toString() {
        return "KataChitthiImageModel{" +
                "imagePath='" + imagePath + '\'' +
                ", imageType=" + imageType +
                ", imageBitmap=" + imageBitmap +
                '}';
    }
}
