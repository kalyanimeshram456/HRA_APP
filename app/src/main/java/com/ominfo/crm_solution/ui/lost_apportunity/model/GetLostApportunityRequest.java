
package com.ominfo.crm_solution.ui.lost_apportunity.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetLostApportunityRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("company_id")
    private RequestBody companyId;

   /* @SerializedName("employee")
    private RequestBody employee;
*/
    @SerializedName("from_date")
    private RequestBody fromDate;

    @SerializedName("to_date")
    private RequestBody toDate;

    @SerializedName("page_number")
    private RequestBody pageNumber;

    @SerializedName("page_size")
    private RequestBody pageSize;

    @SerializedName("filter_customer_name")
    private RequestBody filterCustomerName;

    @SerializedName("filter_doc_number")
    private RequestBody filterDocNumber;

    @SerializedName("filter_reason")
    private RequestBody filterReason;

    @SerializedName("filter_rm")
    private RequestBody filterRm;

    public RequestBody getFilterDocNumber() {
        return filterDocNumber;
    }

    public void setFilterDocNumber(RequestBody filterDocNumber) {
        this.filterDocNumber = filterDocNumber;
    }

    public RequestBody getFilterReason() {
        return filterReason;
    }

    public void setFilterReason(RequestBody filterReason) {
        this.filterReason = filterReason;
    }

    public RequestBody getFilterCustomerName() {
        return filterCustomerName;
    }

    public void setFilterCustomerName(RequestBody filterCustomerName) {
        this.filterCustomerName = filterCustomerName;
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

    public RequestBody getCompanyId() {
        return companyId;
    }

    public void setCompanyId(RequestBody companyId) {
        this.companyId = companyId;
    }

    /*public RequestBody getEmployee() {
        return employee;
    }

    public void setEmployee(RequestBody employee) {
        this.employee = employee;
    }*/

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
