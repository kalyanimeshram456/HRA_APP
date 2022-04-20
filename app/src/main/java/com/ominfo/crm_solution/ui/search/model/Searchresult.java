
package com.ominfo.crm_solution.ui.search.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Searchresult {

    @SerializedName("doc_no")
    private String mDocNo;
    @SerializedName("id")
    private String mId;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("city_name")
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDocNo() {
        return mDocNo;
    }

    public void setDocNo(String docNo) {
        mDocNo = docNo;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
