
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BirthDayDobdatum {

    @SerializedName("dob")
    private String mDob;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_position")
    private String mEmpPosition;
    @SerializedName("emp_profile_pic")
    private String mEmpProfilePic;
    @SerializedName("id")
    private String mId;

    public String getDob() {
        return mDob;
    }

    public void setDob(String dob) {
        mDob = dob;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
