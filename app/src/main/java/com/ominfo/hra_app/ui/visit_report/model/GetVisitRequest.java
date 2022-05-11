
package com.ominfo.hra_app.ui.visit_report.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetVisitRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("visit")
    private RequestBody visit;

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

    @SerializedName("filter_visit_no")
    private RequestBody filterVisitNo;

    @SerializedName("filter_customer_name")
    private RequestBody filterCustomerName;

    @SerializedName("filter_topic")
    private RequestBody filterTopic;

    @SerializedName("filter_visit_result")
    private RequestBody filterVisitResult;

    @SerializedName("filter_rm")
    private RequestBody filterRm;

    @SerializedName("filter_tour")
    private RequestBody filterTour;

    public RequestBody getFilterVisitNo() {
        return filterVisitNo;
    }

    public void setFilterVisitNo(RequestBody filterVisitNo) {
        this.filterVisitNo = filterVisitNo;
    }

    public RequestBody getFilterTopic() {
        return filterTopic;
    }

    public void setFilterTopic(RequestBody filterTopic) {
        this.filterTopic = filterTopic;
    }

    public RequestBody getFilterVisitResult() {
        return filterVisitResult;
    }

    public void setFilterVisitResult(RequestBody filterVisitResult) {
        this.filterVisitResult = filterVisitResult;
    }

    public RequestBody getFilterTour() {
        return filterTour;
    }

    public void setFilterTour(RequestBody filterTour) {
        this.filterTour = filterTour;
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

    public RequestBody getVisit() {
        return visit;
    }

    public void setVisit(RequestBody visit) {
        this.visit = visit;
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
