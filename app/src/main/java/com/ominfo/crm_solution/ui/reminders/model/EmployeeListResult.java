
package com.ominfo.crm_solution.ui.reminders.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EmployeeListResult {

    @SerializedName("emp_id")
    private Long mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;

    public Long getEmpId() {
        return mEmpId;
    }

    public void setEmpId(Long empId) {
        mEmpId = empId;
    }

    public String getEmpName() {
        return mEmpName;
    }

    public void setEmpName(String empName) {
        mEmpName = empName;
    }

}
