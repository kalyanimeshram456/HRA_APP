
package com.ominfo.hra_app.ui.salary.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateSalaryRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("addition")
    private RequestBody addition;

    @SerializedName("total")
    private RequestBody total;

    @SerializedName("remark")
    private RequestBody remark;

    @SerializedName("emp_id")
    private RequestBody emp_id;

    @SerializedName("deduction")
    private RequestBody deduction;

    @SerializedName("year")
    private RequestBody year;

    @SerializedName("month")
    private RequestBody month;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getAddition() {
        return addition;
    }

    public void setAddition(RequestBody addition) {
        this.addition = addition;
    }

    public RequestBody getTotal() {
        return total;
    }

    public void setTotal(RequestBody total) {
        this.total = total;
    }

    public RequestBody getRemark() {
        return remark;
    }

    public void setRemark(RequestBody remark) {
        this.remark = remark;
    }

    public RequestBody getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(RequestBody emp_id) {
        this.emp_id = emp_id;
    }

    public RequestBody getDeduction() {
        return deduction;
    }

    public void setDeduction(RequestBody deduction) {
        this.deduction = deduction;
    }

    public RequestBody getYear() {
        return year;
    }

    public void setYear(RequestBody year) {
        this.year = year;
    }

    public RequestBody getMonth() {
        return month;
    }

    public void setMonth(RequestBody month) {
        this.month = month;
    }
}
