
package com.ominfo.crm_solution.ui.dashboard.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DashModel {

    @SerializedName("title")
    private String title;

    @SerializedName("value")
    private String value;

    @SerializedName("img")
    private Drawable img;

    public DashModel(String title, String value, Drawable img) {
        this.title = title;
        this.value = value;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "DashModel{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", img=" + img +
                '}';
    }
}
