
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalaryList {

    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_position")
    private String mEmpPosition;
    @SerializedName("emp_profile_pic")
    private String mEmpProfilePic;
    @SerializedName("logo_url")
    private String mLogoUrl;
    @SerializedName("name")
    private String mName;

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

}
