
package com.ominfo.crm_solution.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationResult {

    @SerializedName("customer")
    private String mCustomer;
    @SerializedName("heading")
    private String mHeading;
    @SerializedName("id")
    private String mId;
    @SerializedName("text")
    private String mText;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;

    public NotificationResult(String mHeading, String mText) {
        this.mHeading = mHeading;
        this.mText = mText;
    }

    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String customer) {
        mCustomer = customer;
    }

    public String getHeading() {
        return mHeading;
    }

    public void setHeading(String heading) {
        mHeading = heading;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
