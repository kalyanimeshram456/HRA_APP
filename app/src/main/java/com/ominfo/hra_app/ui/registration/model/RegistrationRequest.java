
package com.ominfo.hra_app.ui.registration.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RegistrationRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("name")
    private RequestBody name;

    @SerializedName("registered_address")
    private RequestBody address;

    @SerializedName("pincode")
    private RequestBody pincode;

    @SerializedName("contact_no")
    private RequestBody contactNo;

    @SerializedName("email_id")
    private RequestBody emailId;

    @SerializedName("staff_strength")
    private RequestBody staffStrength;

    @SerializedName("user_prefix")
    private RequestBody userPrefix;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getName() {
        return name;
    }

    public void setName(RequestBody name) {
        this.name = name;
    }

    public RequestBody getAddress() {
        return address;
    }

    public void setAddress(RequestBody address) {
        this.address = address;
    }

    public RequestBody getPincode() {
        return pincode;
    }

    public void setPincode(RequestBody pincode) {
        this.pincode = pincode;
    }

    public RequestBody getContactNo() {
        return contactNo;
    }

    public void setContactNo(RequestBody contactNo) {
        this.contactNo = contactNo;
    }

    public RequestBody getEmailId() {
        return emailId;
    }

    public void setEmailId(RequestBody emailId) {
        this.emailId = emailId;
    }

    public RequestBody getStaffStrength() {
        return staffStrength;
    }

    public void setStaffStrength(RequestBody staffStrength) {
        this.staffStrength = staffStrength;
    }

    public RequestBody getUserPrefix() {
        return userPrefix;
    }

    public void setUserPrefix(RequestBody userPrefix) {
        this.userPrefix = userPrefix;
    }
}
