
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EmployeeListResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("list")
    private List<EmployeeList> mList;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalpages")
    private Long mTotalpages;
    @SerializedName("totalrows")
    private Long mTotalrows;
    @SerializedName("totalactiveemp")
    private String totalactiveemp;
    @SerializedName("total_prest_emp")
    private String total_prest_emp;

    public String getTotalactiveemp() {
        return totalactiveemp;
    }

    public void setTotalactiveemp(String totalactiveemp) {
        this.totalactiveemp = totalactiveemp;
    }

    public String getTotal_prest_emp() {
        return total_prest_emp;
    }

    public void setTotal_prest_emp(String total_prest_emp) {
        this.total_prest_emp = total_prest_emp;
    }

    public String getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(String currentpage) {
        mCurrentpage = currentpage;
    }

    public List<EmployeeList> getList() {
        return mList;
    }

    public void setList(List<EmployeeList> list) {
        mList = list;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getNextpage() {
        return mNextpage;
    }

    public void setNextpage(Long nextpage) {
        mNextpage = nextpage;
    }

    public Long getPrevpage() {
        return mPrevpage;
    }

    public void setPrevpage(Long prevpage) {
        mPrevpage = prevpage;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

    public Long getTotalrows() {
        return mTotalrows;
    }

    public void setTotalrows(Long totalrows) {
        mTotalrows = totalrows;
    }

}
