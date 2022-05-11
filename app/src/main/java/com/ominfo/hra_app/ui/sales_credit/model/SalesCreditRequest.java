
package com.ominfo.hra_app.ui.sales_credit.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesCreditRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("isAdmin")
    private RequestBody isAdmin;

    @SerializedName("company_id")
    private RequestBody companyId;

    @SerializedName("employee_id")
    private RequestBody employeeId;

    @SerializedName("page_number")
    private RequestBody pageNumber;

    @SerializedName("page_size")
    private RequestBody pageSize;

    @SerializedName("filter_customer_name")
    private RequestBody filterCustomerName;

    @SerializedName("filter_rm")
    private RequestBody filterRm;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(RequestBody isAdmin) {
        this.isAdmin = isAdmin;
    }

    public RequestBody getCompanyId() {
        return companyId;
    }

    public void setCompanyId(RequestBody companyId) {
        this.companyId = companyId;
    }

    public RequestBody getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(RequestBody employeeId) {
        this.employeeId = employeeId;
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
}
