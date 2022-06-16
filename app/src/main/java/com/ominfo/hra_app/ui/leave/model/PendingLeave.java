
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PendingLeave {

    @SerializedName("credited_cl")
    private String mCreditedCl;
    @SerializedName("credited_ol")
    private String mCreditedOl;
    @SerializedName("credited_sl")
    private String mCreditedSl;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("id")
    private String mId;
    @SerializedName("left_cl")
    private Long mLeftCl;
    @SerializedName("left_ol")
    private Long mLeftOl;
    @SerializedName("left_sl")
    private Long mLeftSl;

    public String getCreditedCl() {
        return mCreditedCl;
    }

    public void setCreditedCl(String creditedCl) {
        mCreditedCl = creditedCl;
    }

    public String getCreditedOl() {
        return mCreditedOl;
    }

    public void setCreditedOl(String creditedOl) {
        mCreditedOl = creditedOl;
    }

    public String getCreditedSl() {
        return mCreditedSl;
    }

    public void setCreditedSl(String creditedSl) {
        mCreditedSl = creditedSl;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getLeftCl() {
        return mLeftCl;
    }

    public void setLeftCl(Long leftCl) {
        mLeftCl = leftCl;
    }

    public Long getLeftOl() {
        return mLeftOl;
    }

    public void setLeftOl(Long leftOl) {
        mLeftOl = leftOl;
    }

    public Long getLeftSl() {
        return mLeftSl;
    }

    public void setLeftSl(Long leftSl) {
        mLeftSl = leftSl;
    }

}
