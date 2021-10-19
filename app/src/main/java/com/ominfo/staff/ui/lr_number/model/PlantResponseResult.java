
package com.ominfo.staff.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PlantResponseResult {

    @SerializedName("Service_location_ID")
    private String mServiceLocationID;
    @SerializedName("Service_Location_Name")
    private String mServiceLocationName;

    public String getServiceLocationID() {
        return mServiceLocationID;
    }

    public void setServiceLocationID(String serviceLocationID) {
        mServiceLocationID = serviceLocationID;
    }

    public String getServiceLocationName() {
        return mServiceLocationName;
    }

    public void setServiceLocationName(String serviceLocationName) {
        mServiceLocationName = serviceLocationName;
    }

}
