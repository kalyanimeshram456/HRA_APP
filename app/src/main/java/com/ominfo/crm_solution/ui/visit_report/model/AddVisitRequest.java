
package com.ominfo.crm_solution.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddVisitRequest {

    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("start_location_address")
    private String mStartLocationAddress;
    @SerializedName("start_location_latitude")
    private String mStartLocationLatitude;
    @SerializedName("start_location_longitute")
    private String mStartLocationLongitute;
    @SerializedName("start_location_name")
    private String mStartLocationName;
    @SerializedName("visit_date")
    private String mVisitDate;
    @SerializedName("visit_no")
    private String mVisitNo;
    @SerializedName("visit_time")
    private String mVisitTime;

    public String getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(String companyID) {
        mCompanyID = companyID;
    }

    public String getStartLocationAddress() {
        return mStartLocationAddress;
    }

    public void setStartLocationAddress(String startLocationAddress) {
        mStartLocationAddress = startLocationAddress;
    }

    public String getStartLocationLatitude() {
        return mStartLocationLatitude;
    }

    public void setStartLocationLatitude(String startLocationLatitude) {
        mStartLocationLatitude = startLocationLatitude;
    }

    public String getStartLocationLongitute() {
        return mStartLocationLongitute;
    }

    public void setStartLocationLongitute(String startLocationLongitute) {
        mStartLocationLongitute = startLocationLongitute;
    }

    public String getStartLocationName() {
        return mStartLocationName;
    }

    public void setStartLocationName(String startLocationName) {
        mStartLocationName = startLocationName;
    }

    public String getVisitDate() {
        return mVisitDate;
    }

    public void setVisitDate(String visitDate) {
        mVisitDate = visitDate;
    }

    public String getVisitNo() {
        return mVisitNo;
    }

    public void setVisitNo(String visitNo) {
        mVisitNo = visitNo;
    }

    public String getVisitTime() {
        return mVisitTime;
    }

    public void setVisitTime(String visitTime) {
        mVisitTime = visitTime;
    }

}
