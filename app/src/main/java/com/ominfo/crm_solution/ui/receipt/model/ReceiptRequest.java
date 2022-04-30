
package com.ominfo.crm_solution.ui.receipt.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReceiptRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("pageno")
    private RequestBody pageno;

    @SerializedName("pagesize")
    private RequestBody pagesize;

    @SerializedName("Startdate")
    private RequestBody startDate;

    @SerializedName("endDate")
    private RequestBody endDate;

    @SerializedName("MinAmount")
    private RequestBody minAmount;

    @SerializedName("MaxAmount")
    private RequestBody maxAmount;

    @SerializedName("ticket_no")
    private RequestBody ticketNo;

    @SerializedName("cust_name")
    private RequestBody custName;

    public RequestBody getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(RequestBody ticketNo) {
        this.ticketNo = ticketNo;
    }

    public RequestBody getCustName() {
        return custName;
    }

    public void setCustName(RequestBody custName) {
        this.custName = custName;
    }

    public RequestBody getStartDate() {
        return startDate;
    }

    public void setStartDate(RequestBody startDate) {
        this.startDate = startDate;
    }

    public RequestBody getEndDate() {
        return endDate;
    }

    public void setEndDate(RequestBody endDate) {
        this.endDate = endDate;
    }

    public RequestBody getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(RequestBody minAmount) {
        this.minAmount = minAmount;
    }

    public RequestBody getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(RequestBody maxAmount) {
        this.maxAmount = maxAmount;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getPageno() {
        return pageno;
    }

    public void setPageno(RequestBody pageno) {
        this.pageno = pageno;
    }

    public RequestBody getPagesize() {
        return pagesize;
    }

    public void setPagesize(RequestBody pagesize) {
        this.pagesize = pagesize;
    }
}
