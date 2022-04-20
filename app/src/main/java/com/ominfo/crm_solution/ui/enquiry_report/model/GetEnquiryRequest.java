
package com.ominfo.crm_solution.ui.enquiry_report.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;
import retrofit2.http.Part;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetEnquiryRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("enquiry")
    private RequestBody enquiry;

    @SerializedName("company_id")
    private RequestBody companyId;

    @SerializedName("employee")
    private RequestBody employee;

    @SerializedName("from_date")
    private RequestBody fromDate;

    @SerializedName("to_date")
    private RequestBody toDate;

    @SerializedName("page_number")
    private RequestBody pageNumber;

    @SerializedName("page_size")
    private RequestBody pageSize;

    @SerializedName("filter_enquiry_no")
    private RequestBody filterEnquiryNo;

    @SerializedName("filter_customer_name")
    private RequestBody filterCustomerName;

    @SerializedName("filter_enquiry_status")
    private RequestBody filterEnquiryStatus;

    @SerializedName("filter_close_reason")
    private RequestBody filterCloseReason;

    @SerializedName("filter_rm")
    private RequestBody filterRm;

    public RequestBody getFilterEnquiryNo() {
        return filterEnquiryNo;
    }

    public void setFilterEnquiryNo(RequestBody filterEnquiryNo) {
        this.filterEnquiryNo = filterEnquiryNo;
    }

    public RequestBody getFilterCustomerName() {
        return filterCustomerName;
    }

    public void setFilterCustomerName(RequestBody filterCustomerName) {
        this.filterCustomerName = filterCustomerName;
    }

    public RequestBody getFilterEnquiryStatus() {
        return filterEnquiryStatus;
    }

    public void setFilterEnquiryStatus(RequestBody filterEnquiryStatus) {
        this.filterEnquiryStatus = filterEnquiryStatus;
    }

    public RequestBody getFilterCloseReason() {
        return filterCloseReason;
    }

    public void setFilterCloseReason(RequestBody filterCloseReason) {
        this.filterCloseReason = filterCloseReason;
    }

    public RequestBody getFilterRm() {
        return filterRm;
    }

    public void setFilterRm(RequestBody filterRm) {
        this.filterRm = filterRm;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(RequestBody enquiry) {
        this.enquiry = enquiry;
    }

    public RequestBody getCompanyId() {
        return companyId;
    }

    public void setCompanyId(RequestBody companyId) {
        this.companyId = companyId;
    }

    public RequestBody getEmployee() {
        return employee;
    }

    public void setEmployee(RequestBody employee) {
        this.employee = employee;
    }

    public RequestBody getFromDate() {
        return fromDate;
    }

    public void setFromDate(RequestBody fromDate) {
        this.fromDate = fromDate;
    }

    public RequestBody getToDate() {
        return toDate;
    }

    public void setToDate(RequestBody toDate) {
        this.toDate = toDate;
    }

    public RequestBody getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(RequestBody pageNumber) {
        this.pageNumber = pageNumber;
    }

    public RequestBody getPageSize() {
        return pageSize;
    }

    public void setPageSize(RequestBody pageSize) {
        this.pageSize = pageSize;
    }
}
