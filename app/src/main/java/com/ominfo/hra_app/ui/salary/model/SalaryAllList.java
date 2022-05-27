
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalaryAllList {

    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("status")
    private String status;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_position")
    private String mEmpPosition;
    @SerializedName("emp_profile_pic")
    private String mEmpProfilePic;
    @SerializedName("last_salpaid_date")
    private String mLastSalpaidDate;
    @SerializedName("leave_count_cur_mon")
    private String mLeaveCountCurMon;
    @SerializedName("logo_url")
    private String mLogoUrl;
    @SerializedName("name")
    private String mName;
    @SerializedName("salary")
    private String mSalary;
    @SerializedName("salary_this_month")
    private double mSalaryThisMonth;
    @SerializedName("month")
    private String month;
    @SerializedName("year")
    private String year;


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(String companyID) {
        mCompanyID = companyID;
    }

    public String getEmpId() {
        return mEmpId;
    }

    public void setEmpId(String empId) {
        mEmpId = empId;
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

    public String getEmpProfilePic() {
        return mEmpProfilePic;
    }

    public void setEmpProfilePic(String empProfilePic) {
        mEmpProfilePic = empProfilePic;
    }

    public String getLastSalpaidDate() {
        return mLastSalpaidDate;
    }

    public void setLastSalpaidDate(String lastSalpaidDate) {
        mLastSalpaidDate = lastSalpaidDate;
    }

    public String getLeaveCountCurMon() {
        return mLeaveCountCurMon;
    }

    public void setLeaveCountCurMon(String leaveCountCurMon) {
        mLeaveCountCurMon = leaveCountCurMon;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        mLogoUrl = logoUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSalary() {
        return mSalary;
    }

    public void setSalary(String salary) {
        mSalary = salary;
    }

    public double getSalaryThisMonth() {
        return mSalaryThisMonth;
    }

    public void setSalaryThisMonth(double salaryThisMonth) {
        mSalaryThisMonth = salaryThisMonth;
    }

}
