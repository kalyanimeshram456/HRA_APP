
package com.ominfo.hra_app.ui.my_account.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RaiseTicketRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("cust_id")
    private RequestBody custId;

    @SerializedName("subject")
    private RequestBody subject;

    @SerializedName("Description")
    private RequestBody Description;

    @SerializedName("priority")
    private RequestBody priority;

    @SerializedName("issue_type")
    private RequestBody issueType;

    @SerializedName("ticket_no")
    private RequestBody ticketNo;

    public RequestBody getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(RequestBody ticketNo) {
        this.ticketNo = ticketNo;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getCustId() {
        return custId;
    }

    public void setCustId(RequestBody custId) {
        this.custId = custId;
    }

    public RequestBody getSubject() {
        return subject;
    }

    public void setSubject(RequestBody subject) {
        this.subject = subject;
    }

    public RequestBody getDescription() {
        return Description;
    }

    public void setDescription(RequestBody description) {
        Description = description;
    }

    public RequestBody getPriority() {
        return priority;
    }

    public void setPriority(RequestBody priority) {
        this.priority = priority;
    }

    public RequestBody getIssueType() {
        return issueType;
    }

    public void setIssueType(RequestBody issueType) {
        this.issueType = issueType;
    }
}
