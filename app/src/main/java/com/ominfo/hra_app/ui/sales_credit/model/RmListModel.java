
package com.ominfo.hra_app.ui.sales_credit.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RmListModel {

    @SerializedName("title")
    private String title;

    @SerializedName("value")
    private String value;

    @SerializedName("id")
    private String id;

    public RmListModel(String title, String value, String id) {
        this.title = title;
        this.value = value;
        this.id = id;
    }

    @Override
    public String toString() {
        return "RmListModel{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", id='" + id + '\'' +
                '}';
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
