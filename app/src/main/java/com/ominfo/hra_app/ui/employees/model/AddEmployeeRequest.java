
package com.ominfo.hra_app.ui.employees.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddEmployeeRequest {

    @SerializedName("action")
    private RequestBody action;

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
}
