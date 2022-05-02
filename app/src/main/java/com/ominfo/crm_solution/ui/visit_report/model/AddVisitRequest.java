
package com.ominfo.crm_solution.ui.visit_report.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddVisitRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("visit_no")
    private RequestBody visitNo;

    @SerializedName("company_ID")
    private RequestBody companyId;

    @SerializedName("start_location_name")
    private RequestBody startLocationName;

    @SerializedName("start_location_address")
    private RequestBody startLocationAddress;

    @SerializedName("start_location_latitude")
    private RequestBody startLocationLatitude;

    @SerializedName("start_location_longitute")
    private RequestBody startLocationLongitute;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(RequestBody visitNo) {
        this.visitNo = visitNo;
    }

    public RequestBody getCompanyId() {
        return companyId;
    }

    public void setCompanyId(RequestBody companyId) {
        this.companyId = companyId;
    }

    public RequestBody getStartLocationName() {
        return startLocationName;
    }

    public void setStartLocationName(RequestBody startLocationName) {
        this.startLocationName = startLocationName;
    }

    public RequestBody getStartLocationAddress() {
        return startLocationAddress;
    }

    public void setStartLocationAddress(RequestBody startLocationAddress) {
        this.startLocationAddress = startLocationAddress;
    }

    public RequestBody getStartLocationLatitude() {
        return startLocationLatitude;
    }

    public void setStartLocationLatitude(RequestBody startLocationLatitude) {
        this.startLocationLatitude = startLocationLatitude;
    }

    public RequestBody getStartLocationLongitute() {
        return startLocationLongitute;
    }

    public void setStartLocationLongitute(RequestBody startLocationLongitute) {
        this.startLocationLongitute = startLocationLongitute;
    }
}
