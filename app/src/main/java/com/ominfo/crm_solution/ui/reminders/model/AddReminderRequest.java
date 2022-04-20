
package com.ominfo.crm_solution.ui.reminders.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddReminderRequest {

    @SerializedName("companyId")
    private String mCompanyId;
    @SerializedName("employee_id")
    private String mEmployeeId;
    @SerializedName("employeelist")
    private List<String> mEmployeelist;
    @SerializedName("remDate")
    private String mRemDate;
    @SerializedName("remStatus")
    private String mRemStatus;
    @SerializedName("remTime")
    private String mRemTime;
    @SerializedName("remark")
    private String mRemark;
    @SerializedName("remdescription")
    private String mRemdescription;

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getEmployeeId() {
        return mEmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        mEmployeeId = employeeId;
    }

    public List<String> getEmployeelist() {
        return mEmployeelist;
    }

    public void setEmployeelist(List<String> employeelist) {
        mEmployeelist = employeelist;
    }

    public String getRemDate() {
        return mRemDate;
    }

    public void setRemDate(String remDate) {
        mRemDate = remDate;
    }

    public String getRemStatus() {
        return mRemStatus;
    }

    public void setRemStatus(String remStatus) {
        mRemStatus = remStatus;
    }

    public String getRemTime() {
        return mRemTime;
    }

    public void setRemTime(String remTime) {
        mRemTime = remTime;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }

    public String getRemdescription() {
        return mRemdescription;
    }

    public void setRemdescription(String remdescription) {
        mRemdescription = remdescription;
    }

}
