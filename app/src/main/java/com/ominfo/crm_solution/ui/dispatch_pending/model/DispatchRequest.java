
package com.ominfo.crm_solution.ui.dispatch_pending.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DispatchRequest {

    @SerializedName("companyName")
    private List<String> mCompanyID;
    @SerializedName("pageno")
    private String mPageno;
    @SerializedName("pagesize")
    private String mPagesize;
    @SerializedName("po_number")
    private String mPoNumber;
    @SerializedName("rmID")
    private List<String> mRmID;
    @SerializedName("Startdate")
    private String startDate;
    @SerializedName("EndDate")
    private String endDate;


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(List<String> companyID) {
        mCompanyID = companyID;
    }

    public String getPageno() {
        return mPageno;
    }

    public void setPageno(String pageno) {
        mPageno = pageno;
    }

    public String getPagesize() {
        return mPagesize;
    }

    public void setPagesize(String pagesize) {
        mPagesize = pagesize;
    }

    public String getPoNumber() {
        return mPoNumber;
    }

    public void setPoNumber(String poNumber) {
        mPoNumber = poNumber;
    }

    public List<String> getRmID() {
        return mRmID;
    }

    public void setRmID(List<String> rmID) {
        mRmID = rmID;
    }

}
