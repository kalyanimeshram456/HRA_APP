
package com.ominfo.hra_app.ui.top_customer.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TopCustomerRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("rmid")
    private RequestBody rmId;

    @SerializedName("cust_name")
    private RequestBody custName;

    @SerializedName("pageno")
    private RequestBody pageNo;

    @SerializedName("pagesize")
    private RequestBody pageSize;

    @SerializedName("inv_max")
    private RequestBody invMax;

    @SerializedName("inv_min")
    private RequestBody invMin;

    @SerializedName("sale_min_amt")
    private RequestBody saleMinAmt;

    @SerializedName("sale_max_amt")
    private RequestBody saleMaxAmt;

    @SerializedName("from_date")
    private RequestBody fromDate;

    @SerializedName("end_date")
    private RequestBody endDate;

    public RequestBody getInvMax() {
        return invMax;
    }

    public void setInvMax(RequestBody invMax) {
        this.invMax = invMax;
    }

    public RequestBody getInvMin() {
        return invMin;
    }

    public void setInvMin(RequestBody invMin) {
        this.invMin = invMin;
    }

    public RequestBody getSaleMinAmt() {
        return saleMinAmt;
    }

    public void setSaleMinAmt(RequestBody saleMinAmt) {
        this.saleMinAmt = saleMinAmt;
    }

    public RequestBody getSaleMaxAmt() {
        return saleMaxAmt;
    }

    public void setSaleMaxAmt(RequestBody saleMaxAmt) {
        this.saleMaxAmt = saleMaxAmt;
    }

    public RequestBody getFromDate() {
        return fromDate;
    }

    public void setFromDate(RequestBody fromDate) {
        this.fromDate = fromDate;
    }

    public RequestBody getEndDate() {
        return endDate;
    }

    public void setEndDate(RequestBody endDate) {
        this.endDate = endDate;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getRmId() {
        return rmId;
    }

    public void setRmId(RequestBody rmId) {
        this.rmId = rmId;
    }

    public RequestBody getCustName() {
        return custName;
    }

    public void setCustName(RequestBody custName) {
        this.custName = custName;
    }

    public RequestBody getPageNo() {
        return pageNo;
    }

    public void setPageNo(RequestBody pageNo) {
        this.pageNo = pageNo;
    }

    public RequestBody getPageSize() {
        return pageSize;
    }

    public void setPageSize(RequestBody pageSize) {
        this.pageSize = pageSize;
    }
}
