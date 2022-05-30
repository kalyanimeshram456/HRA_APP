
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalarySlipList {

    @SerializedName("addition")
    private String mAddition;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("deduction")
    private String mDeduction;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_position")
    private String mEmpPosition;
    @SerializedName("id")
    private String mId;
    @SerializedName("leaves")
    private String mLeaves;
    @SerializedName("logo_url")
    private String mLogoUrl;
    @SerializedName("month")
    private String mMonth;
    @SerializedName("name")
    private String mName;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("year")
    private String mYear;
    @SerializedName("salary")
    private String salary;

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAddition() {
        return mAddition;
    }

    public void setAddition(String addition) {
        mAddition = addition;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public String getDeduction() {
        return mDeduction;
    }

    public void setDeduction(String deduction) {
        mDeduction = deduction;
    }

    public String getEmpName() {
        return mEmpName;
    }

    public void setEmpName(String empName) {
        mEmpName = empName;
    }

    public String getEmpPosition() {
        return mEmpPosition;
    }

    public void setEmpPosition(String empPosition) {
        mEmpPosition = empPosition;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLeaves() {
        return mLeaves;
    }

    public void setLeaves(String leaves) {
        mLeaves = leaves;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        mLogoUrl = logoUrl;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

}
