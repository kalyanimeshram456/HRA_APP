
package com.ominfo.staff.ui.lr_number.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ImageDataModel {

    @SerializedName("lr_no")
    private String lrNo;

    @SerializedName("img_uri")
    private Uri imgUri;

    public String getLrNo() {
        return lrNo;
    }

    public void setLrNo(String lrNo) {
        this.lrNo = lrNo;
    }

    public ImageDataModel(String lrNo, Uri imgUri) {
        this.lrNo = lrNo;
        this.imgUri = imgUri;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "ImageDataModel{" +
                "lrNo='" + lrNo + '\'' +
                ", imgUri=" + imgUri +
                '}';
    }
}
