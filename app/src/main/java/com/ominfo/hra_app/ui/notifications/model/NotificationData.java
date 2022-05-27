
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationData {

    @SerializedName("heading")
    private String mHeading;
    @SerializedName("id")
    private String mId;
    @SerializedName("related_id")
    private String mRelatedId;
    @SerializedName("text")
    private String mText;
    @SerializedName("type")
    private String mType;

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

    public String getRelatedId() {
        return mRelatedId;
    }

    public void setRelatedId(String relatedId) {
        mRelatedId = relatedId;
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

}
