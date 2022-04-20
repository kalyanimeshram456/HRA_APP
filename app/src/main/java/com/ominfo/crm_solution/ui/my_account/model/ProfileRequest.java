
package com.ominfo.crm_solution.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProfileRequest {

    @SerializedName("empId")
    private String mEmpId;

    public String getEmpId() {
        return mEmpId;
    }

    public void setEmpId(String empId) {
        mEmpId = empId;
    }

}
