
package com.ominfo.hra_app.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddHolidayRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("date")
    private RequestBody date;

    @SerializedName("name")
    private RequestBody name;

    @SerializedName("description")
    private RequestBody description;

    @SerializedName("company_id")
    private RequestBody company_id;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getDate() {
        return date;
    }

    public void setDate(RequestBody date) {
        this.date = date;
    }

    public RequestBody getName() {
        return name;
    }

    public void setName(RequestBody name) {
        this.name = name;
    }

    public RequestBody getDescription() {
        return description;
    }

    public void setDescription(RequestBody description) {
        this.description = description;
    }

    public RequestBody getCompany_id() {
        return company_id;
    }

    public void setCompany_id(RequestBody company_id) {
        this.company_id = company_id;
    }
}
