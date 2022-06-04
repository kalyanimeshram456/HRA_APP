
package com.ominfo.hra_app.ui.payment.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddUserRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("company_id")
    private RequestBody company_id;

    @SerializedName("add_user")
    private RequestBody add_user;

    @SerializedName("emp_id")
    private RequestBody emp_id;

    @SerializedName("gst_percent")
    private RequestBody gst_percent;

    @SerializedName("sub_charge")
    private RequestBody sub_charge;

    @SerializedName("gst_amount")
    private RequestBody gst_amount;

    @SerializedName("total_charge")
    private RequestBody total_charge;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getCompany_id() {
        return company_id;
    }

    public void setCompany_id(RequestBody company_id) {
        this.company_id = company_id;
    }

    public RequestBody getAdd_user() {
        return add_user;
    }

    public void setAdd_user(RequestBody add_user) {
        this.add_user = add_user;
    }

    public RequestBody getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(RequestBody emp_id) {
        this.emp_id = emp_id;
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

    public RequestBody getTotal_charge() {
        return total_charge;
    }

    public void setTotal_charge(RequestBody total_charge) {
        this.total_charge = total_charge;
    }
}
