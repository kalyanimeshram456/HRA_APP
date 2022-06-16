
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalaryAllResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("list")
    private java.util.List<SalaryAllList> mList;
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
    @SerializedName("last_month_dis_sal")
    private String last_month_dis_sal;

    public String getLast_month_dis_sal() {
        return last_month_dis_sal;
    }

    public void setLast_month_dis_sal(String last_month_dis_sal) {
        this.last_month_dis_sal = last_month_dis_sal;
    }
    public String getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(String currentpage) {
        mCurrentpage = currentpage;
    }

    public java.util.List<SalaryAllList> getList() {
        return mList;
    }

    public void setList(java.util.List<SalaryAllList> list) {
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
