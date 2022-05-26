
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ActiveEmployeeListEmpDatum {

    @SerializedName("company_id")
    private String mCompanyId;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("isAdmin")
    private String mIsAdmin;

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
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

    public String getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        mIsAdmin = isAdmin;
    }

}
