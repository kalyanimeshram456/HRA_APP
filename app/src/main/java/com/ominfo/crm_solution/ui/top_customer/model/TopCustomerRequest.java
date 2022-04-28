
package com.ominfo.crm_solution.ui.top_customer.model;

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
