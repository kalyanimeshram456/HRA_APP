
package com.ominfo.crm_solution.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchLrImage {

    @SerializedName("image")
    private String mImage;
    @SerializedName("lr")
    private String mLr;

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
