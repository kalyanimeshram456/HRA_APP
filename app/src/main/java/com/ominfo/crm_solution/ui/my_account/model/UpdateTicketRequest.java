
package com.ominfo.crm_solution.ui.my_account.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateTicketRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("status")
    private RequestBody status;

    @SerializedName("reason")
    private RequestBody reason;

    @SerializedName("ticket_no")
    private RequestBody ticket_no;

    @SerializedName("subject")
    private RequestBody subject;

    @SerializedName("Description")
    private RequestBody Description;

    @SerializedName("priority")
    private RequestBody priority;

    @SerializedName("issue_type")
    private RequestBody issueType;

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

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getStatus() {
        return status;
    }

    public void setStatus(RequestBody status) {
        this.status = status;
    }

    public RequestBody getReason() {
        return reason;
    }

    public void setReason(RequestBody reason) {
        this.reason = reason;
    }

    public RequestBody getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(RequestBody ticket_no) {
        this.ticket_no = ticket_no;
    }
}
