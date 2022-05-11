
package com.ominfo.hra_app.ui.visit_report.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PhpEditVisitRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("company_ID")
    private RequestBody companyID;

    @SerializedName("employee")
    private RequestBody employee;

    @SerializedName("visit_no")
    private RequestBody visitNo;

    @SerializedName("visit_time_end")
    private RequestBody visitTimeEnd;

    @SerializedName("rm_id")
    private RequestBody rmId;

    @SerializedName("place")
    private RequestBody place;

    @SerializedName("cust_name")
    private RequestBody custName;

    @SerializedName("cust_mobile")
    private RequestBody custMobile;

    @SerializedName("visiting_card")
    private RequestBody visitingCard;

    @SerializedName("topic")
    private RequestBody topic;

    @SerializedName("result")
    private RequestBody result;

    @SerializedName("description")
    private RequestBody description;

    @SerializedName("visit_duration")
    private RequestBody visitDuration;

    @SerializedName("visit_location_address")
    private RequestBody visitLocationAddress;

    @SerializedName("visit_location_name")
    private RequestBody visitLocationName;

    @SerializedName("visit_location_latitude")
    private RequestBody visitLocationLatitude;

    @SerializedName("visit_location_longitute")
    private RequestBody visitLocationLongitute;

    @SerializedName("stop_location_name")
    private RequestBody stopLocationName;

    @SerializedName("stop_location_address")
    private RequestBody stopLocationAddress;

    @SerializedName("stop_location_latitude")
    private RequestBody stopLocationLatitude;

    @SerializedName("stop_location_longitute")
    private RequestBody stopLocationLongitute;

    @SerializedName("tour_id")
    private RequestBody tourId;

    public RequestBody getVisitLocationName() {
        return visitLocationName;
    }

    public void setVisitLocationName(RequestBody visitLocationName) {
        this.visitLocationName = visitLocationName;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getCompanyID() {
        return companyID;
    }

    public void setCompanyID(RequestBody companyID) {
        this.companyID = companyID;
    }

    public RequestBody getEmployee() {
        return employee;
    }

    public void setEmployee(RequestBody employee) {
        this.employee = employee;
    }

    public RequestBody getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(RequestBody visitNo) {
        this.visitNo = visitNo;
    }

    public RequestBody getVisitTimeEnd() {
        return visitTimeEnd;
    }

    public void setVisitTimeEnd(RequestBody visitTimeEnd) {
        this.visitTimeEnd = visitTimeEnd;
    }

    public RequestBody getRmId() {
        return rmId;
    }

    public void setRmId(RequestBody rmId) {
        this.rmId = rmId;
    }

    public RequestBody getPlace() {
        return place;
    }

    public void setPlace(RequestBody place) {
        this.place = place;
    }

    public RequestBody getCustName() {
        return custName;
    }

    public void setCustName(RequestBody custName) {
        this.custName = custName;
    }

    public RequestBody getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(RequestBody custMobile) {
        this.custMobile = custMobile;
    }

    public RequestBody getVisitingCard() {
        return visitingCard;
    }

    public void setVisitingCard(RequestBody visitingCard) {
        this.visitingCard = visitingCard;
    }

    public RequestBody getTopic() {
        return topic;
    }

    public void setTopic(RequestBody topic) {
        this.topic = topic;
    }

    public RequestBody getResult() {
        return result;
    }

    public void setResult(RequestBody result) {
        this.result = result;
    }

    public RequestBody getDescription() {
        return description;
    }

    public void setDescription(RequestBody description) {
        this.description = description;
    }

    public RequestBody getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(RequestBody visitDuration) {
        this.visitDuration = visitDuration;
    }

    public RequestBody getVisitLocationAddress() {
        return visitLocationAddress;
    }

    public void setVisitLocationAddress(RequestBody visitLocationAddress) {
        this.visitLocationAddress = visitLocationAddress;
    }

    public RequestBody getVisitLocationLatitude() {
        return visitLocationLatitude;
    }

    public void setVisitLocationLatitude(RequestBody visitLocationLatitude) {
        this.visitLocationLatitude = visitLocationLatitude;
    }

    public RequestBody getVisitLocationLongitute() {
        return visitLocationLongitute;
    }

    public void setVisitLocationLongitute(RequestBody visitLocationLongitute) {
        this.visitLocationLongitute = visitLocationLongitute;
    }

    public RequestBody getStopLocationName() {
        return stopLocationName;
    }

    public void setStopLocationName(RequestBody stopLocationName) {
        this.stopLocationName = stopLocationName;
    }

    public RequestBody getStopLocationAddress() {
        return stopLocationAddress;
    }

    public void setStopLocationAddress(RequestBody stopLocationAddress) {
        this.stopLocationAddress = stopLocationAddress;
    }

    public RequestBody getStopLocationLatitude() {
        return stopLocationLatitude;
    }

    public void setStopLocationLatitude(RequestBody stopLocationLatitude) {
        this.stopLocationLatitude = stopLocationLatitude;
    }

    public RequestBody getStopLocationLongitute() {
        return stopLocationLongitute;
    }

    public void setStopLocationLongitute(RequestBody stopLocationLongitute) {
        this.stopLocationLongitute = stopLocationLongitute;
    }

    public RequestBody getTourId() {
        return tourId;
    }

    public void setTourId(RequestBody tourId) {
        this.tourId = tourId;
    }
}
