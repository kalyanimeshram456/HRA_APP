
package com.ominfo.hra_app.ui.employees.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddEmployeeRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("loc_disable")
    private RequestBody locDisable;

    @SerializedName("emp_name")
    private RequestBody empName;

    @SerializedName("emp_mob")
    private RequestBody empMob;

    @SerializedName("emp_email")
    private RequestBody empEmail;

    @SerializedName("emp_addr")
    private RequestBody empAddr;

    @SerializedName("emp_dob")
    private RequestBody empDob;

    @SerializedName("emp_gender")
    private RequestBody empGender;

    @SerializedName("emp_pincode")
    private RequestBody empPincode;

    @SerializedName("emp_position")
    private RequestBody empPosition;

    @SerializedName("created_by")
    private RequestBody createdBy;

    @SerializedName("company_ID")
    private RequestBody companyID;

    @SerializedName("token")
    private RequestBody token;

    @SerializedName("salary")
    private RequestBody salary;

    @SerializedName("other_leaves")
    private RequestBody otherLeaves;

    @SerializedName("casual_leaves")
    private RequestBody casualLeaves;

    @SerializedName("sick_leaves")
    private RequestBody sickLeaves;

    @SerializedName("joining")
    private RequestBody joiningDate;

    @SerializedName("office_address")
    private RequestBody mOfficeAddress;
    @SerializedName("office_latitude")
    private RequestBody mOfficeLatitude;
    @SerializedName("office_longitude")
    private RequestBody mOfficeLongitude;
    @SerializedName("fri_working")
    private RequestBody fri_working;
    @SerializedName("fri_start_time")
    private RequestBody fri_start_time;
    @SerializedName("fri_end_time")
    private RequestBody fri_end_time;
    @SerializedName("mon_end_time")
    private RequestBody mMonEndTime;
    @SerializedName("mon_start_time")
    private RequestBody mMonStartTime;
    @SerializedName("mon_working")
    private RequestBody mMonWorking;
    @SerializedName("sat_end_time")
    private RequestBody mSatEndTime;
    @SerializedName("sat_start_time")
    private RequestBody mSatStartTime;
    @SerializedName("sat_working")
    private RequestBody mSatWorking;
    @SerializedName("staff_strength")
    private RequestBody mStaffStrength;
    @SerializedName("sun_end_time")
    private RequestBody mSunEndTime;
    @SerializedName("sun_start_time")
    private RequestBody mSunStartTime;
    @SerializedName("sun_working")
    private RequestBody mSunWorking;
    @SerializedName("thurs_end_time")
    private RequestBody mThursEndTime;
    @SerializedName("thurs_start_time")
    private RequestBody mThursStartTime;
    @SerializedName("thurs_working")
    private RequestBody mThursWorking;
    @SerializedName("tue_end_time")
    private RequestBody mTueEndTime;
    @SerializedName("tue_start_time")
    private RequestBody mTueStartTime;
    @SerializedName("tue_working")
    private RequestBody mTueWorking;
    @SerializedName("updated_by")
    private RequestBody mUpdatedBy;
    @SerializedName("updated_on")
    private RequestBody mUpdatedOn;
    @SerializedName("user_prefix")
    private RequestBody mUserPrefix;
    @SerializedName("wed_end_time")
    private RequestBody mWedEndTime;
    @SerializedName("wed_start_time")
    private RequestBody mWedStartTime;
    @SerializedName("wed_working")
    private RequestBody mWedWorking;

    public RequestBody getLocDisable() {
        return locDisable;
    }

    public void setLocDisable(RequestBody locDisable) {
        this.locDisable = locDisable;
    }

    public RequestBody getFri_working() {
        return fri_working;
    }

    public void setFri_working(RequestBody fri_working) {
        this.fri_working = fri_working;
    }

    public RequestBody getFri_start_time() {
        return fri_start_time;
    }

    public void setFri_start_time(RequestBody fri_start_time) {
        this.fri_start_time = fri_start_time;
    }

    public RequestBody getFri_end_time() {
        return fri_end_time;
    }

    public void setFri_end_time(RequestBody fri_end_time) {
        this.fri_end_time = fri_end_time;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(RequestBody joiningDate) {
        this.joiningDate = joiningDate;
    }

    public RequestBody getEmpName() {
        return empName;
    }

    public void setEmpName(RequestBody empName) {
        this.empName = empName;
    }

    public RequestBody getEmpMob() {
        return empMob;
    }

    public void setEmpMob(RequestBody empMob) {
        this.empMob = empMob;
    }

    public RequestBody getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(RequestBody empEmail) {
        this.empEmail = empEmail;
    }

    public RequestBody getEmpAddr() {
        return empAddr;
    }

    public void setEmpAddr(RequestBody empAddr) {
        this.empAddr = empAddr;
    }

    public RequestBody getEmpDob() {
        return empDob;
    }

    public void setEmpDob(RequestBody empDob) {
        this.empDob = empDob;
    }

    public RequestBody getEmpGender() {
        return empGender;
    }

    public void setEmpGender(RequestBody empGender) {
        this.empGender = empGender;
    }

    public RequestBody getEmpPincode() {
        return empPincode;
    }

    public void setEmpPincode(RequestBody empPincode) {
        this.empPincode = empPincode;
    }

    public RequestBody getEmpPosition() {
        return empPosition;
    }

    public void setEmpPosition(RequestBody empPosition) {
        this.empPosition = empPosition;
    }

    public RequestBody getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(RequestBody createdBy) {
        this.createdBy = createdBy;
    }

    public RequestBody getCompanyID() {
        return companyID;
    }

    public void setCompanyID(RequestBody companyID) {
        this.companyID = companyID;
    }

    public RequestBody getToken() {
        return token;
    }

    public void setToken(RequestBody token) {
        this.token = token;
    }

    public RequestBody getSalary() {
        return salary;
    }

    public void setSalary(RequestBody salary) {
        this.salary = salary;
    }

    public RequestBody getOtherLeaves() {
        return otherLeaves;
    }

    public void setOtherLeaves(RequestBody otherLeaves) {
        this.otherLeaves = otherLeaves;
    }

    public RequestBody getCasualLeaves() {
        return casualLeaves;
    }

    public void setCasualLeaves(RequestBody casualLeaves) {
        this.casualLeaves = casualLeaves;
    }

    public RequestBody getSickLeaves() {
        return sickLeaves;
    }

    public void setSickLeaves(RequestBody sickLeaves) {
        this.sickLeaves = sickLeaves;
    }

    public RequestBody getMonEndTime() {
        return mMonEndTime;
    }

    public void setMonEndTime(RequestBody monEndTime) {
        mMonEndTime = monEndTime;
    }

    public RequestBody getMonStartTime() {
        return mMonStartTime;
    }

    public void setMonStartTime(RequestBody monStartTime) {
        mMonStartTime = monStartTime;
    }

    public RequestBody getMonWorking() {
        return mMonWorking;
    }

    public void setMonWorking(RequestBody monWorking) {
        mMonWorking = monWorking;
    }

    public RequestBody getOfficeAddress() {
        return mOfficeAddress;
    }

    public void setOfficeAddress(RequestBody officeAddress) {
        mOfficeAddress = officeAddress;
    }

    public RequestBody getOfficeLatitude() {
        return mOfficeLatitude;
    }

    public void setOfficeLatitude(RequestBody officeLatitude) {
        mOfficeLatitude = officeLatitude;
    }

    public RequestBody getOfficeLongitude() {
        return mOfficeLongitude;
    }

    public void setOfficeLongitude(RequestBody officeLongitude) {
        mOfficeLongitude = officeLongitude;
    }

    public RequestBody getSatEndTime() {
        return mSatEndTime;
    }

    public void setSatEndTime(RequestBody satEndTime) {
        mSatEndTime = satEndTime;
    }

    public RequestBody getSatStartTime() {
        return mSatStartTime;
    }

    public void setSatStartTime(RequestBody satStartTime) {
        mSatStartTime = satStartTime;
    }

    public RequestBody getSatWorking() {
        return mSatWorking;
    }

    public void setSatWorking(RequestBody satWorking) {
        mSatWorking = satWorking;
    }

    public RequestBody getStaffStrength() {
        return mStaffStrength;
    }

    public void setStaffStrength(RequestBody staffStrength) {
        mStaffStrength = staffStrength;
    }

    public RequestBody getSunEndTime() {
        return mSunEndTime;
    }

    public void setSunEndTime(RequestBody sunEndTime) {
        mSunEndTime = sunEndTime;
    }

    public RequestBody getSunStartTime() {
        return mSunStartTime;
    }

    public void setSunStartTime(RequestBody sunStartTime) {
        mSunStartTime = sunStartTime;
    }

    public RequestBody getSunWorking() {
        return mSunWorking;
    }

    public void setSunWorking(RequestBody sunWorking) {
        mSunWorking = sunWorking;
    }

    public RequestBody getThursEndTime() {
        return mThursEndTime;
    }

    public void setThursEndTime(RequestBody thursEndTime) {
        mThursEndTime = thursEndTime;
    }

    public RequestBody getThursStartTime() {
        return mThursStartTime;
    }

    public void setThursStartTime(RequestBody thursStartTime) {
        mThursStartTime = thursStartTime;
    }

    public RequestBody getThursWorking() {
        return mThursWorking;
    }

    public void setThursWorking(RequestBody thursWorking) {
        mThursWorking = thursWorking;
    }

    public RequestBody getTueEndTime() {
        return mTueEndTime;
    }

    public void setTueEndTime(RequestBody tueEndTime) {
        mTueEndTime = tueEndTime;
    }

    public RequestBody getTueStartTime() {
        return mTueStartTime;
    }

    public void setTueStartTime(RequestBody tueStartTime) {
        mTueStartTime = tueStartTime;
    }

    public RequestBody getTueWorking() {
        return mTueWorking;
    }

    public void setTueWorking(RequestBody tueWorking) {
        mTueWorking = tueWorking;
    }

    public RequestBody getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(RequestBody updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public RequestBody getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(RequestBody updatedOn) {
        mUpdatedOn = updatedOn;
    }

    public RequestBody getUserPrefix() {
        return mUserPrefix;
    }

    public void setUserPrefix(RequestBody userPrefix) {
        mUserPrefix = userPrefix;
    }

    public RequestBody getWedEndTime() {
        return mWedEndTime;
    }

    public void setWedEndTime(RequestBody wedEndTime) {
        mWedEndTime = wedEndTime;
    }

    public RequestBody getWedStartTime() {
        return mWedStartTime;
    }

    public void setWedStartTime(RequestBody wedStartTime) {
        mWedStartTime = wedStartTime;
    }

    public RequestBody getWedWorking() {
        return mWedWorking;
    }

    public void setWedWorking(RequestBody wedWorking) {
        mWedWorking = wedWorking;
    }
}
