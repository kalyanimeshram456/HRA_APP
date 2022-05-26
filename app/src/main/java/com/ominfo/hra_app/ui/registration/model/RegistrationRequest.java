
package com.ominfo.hra_app.ui.registration.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;
import retrofit2.http.Part;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RegistrationRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("gst_no")
    private RequestBody gst_no;

    @SerializedName("admin_name")
    private RequestBody admin_name;

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

    @SerializedName("gst_percent")
    private RequestBody gst_percent;

    @SerializedName("sub_charge")
    private RequestBody sub_charge;

    @SerializedName("gst_amount")
    private RequestBody gst_amount;

    @SerializedName("discount_rate")
    private RequestBody discount_rate;

    @SerializedName("total_charge")
    private RequestBody total_charge;

    @SerializedName("coupon")
    private RequestBody coupon;

    @SerializedName("plan_type")
    private RequestBody plan_type;

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

    public RequestBody getGst_percent() {
        return gst_percent;
    }

    public void setGst_percent(RequestBody gst_percent) {
        this.gst_percent = gst_percent;
    }

    public RequestBody getSub_charge() {
        return sub_charge;
    }

    public void setSub_charge(RequestBody sub_charge) {
        this.sub_charge = sub_charge;
    }

    public RequestBody getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(RequestBody gst_amount) {
        this.gst_amount = gst_amount;
    }

    public RequestBody getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(RequestBody discount_rate) {
        this.discount_rate = discount_rate;
    }

    public RequestBody getTotal_charge() {
        return total_charge;
    }

    public void setTotal_charge(RequestBody total_charge) {
        this.total_charge = total_charge;
    }

    public RequestBody getCoupon() {
        return coupon;
    }

    public void setCoupon(RequestBody coupon) {
        this.coupon = coupon;
    }

    public RequestBody getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(RequestBody plan_type) {
        this.plan_type = plan_type;
    }

    public RequestBody getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(RequestBody admin_name) {
        this.admin_name = admin_name;
    }

    public RequestBody getGst_no() {
        return gst_no;
    }

    public void setGst_no(RequestBody gst_no) {
        this.gst_no = gst_no;
    }
}
