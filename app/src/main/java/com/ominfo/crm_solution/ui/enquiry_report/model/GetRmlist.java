
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetRmlist {

    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_username")
    private String mEmpUsername;

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

    public String getEmpUsername() {
        return mEmpUsername;
    }

    public void setEmpUsername(String empUsername) {
        mEmpUsername = empUsername;
    }

}
