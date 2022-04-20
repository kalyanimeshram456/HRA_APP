
package com.ominfo.crm_solution.ui.visit_report.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VisitImageModel {

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("image_uri")
    private Uri imageType;

    @SerializedName("image_bitmap")
    private Bitmap imageBitmap;

    public VisitImageModel(String imagePath, Uri imageType, Bitmap imageBitmap) {
        this.imagePath = imagePath;
        this.imageType = imageType;
        this.imageBitmap = imageBitmap;
    }

    @Ignore
    public VisitImageModel() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Uri getImageType() {
        return imageType;
    }

    public void setImageType(Uri imageType) {
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
        return "VisitImageModel{" +
                "imagePath='" + imagePath + '\'' +
                ", imageType=" + imageType +
                ", imageBitmap=" + imageBitmap +
                '}';
    }
}
