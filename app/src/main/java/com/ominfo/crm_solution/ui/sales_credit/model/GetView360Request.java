
package com.ominfo.crm_solution.ui.sales_credit.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetView360Request {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("company_id")
    private RequestBody companyId;

    @SerializedName("employee_id")
    private RequestBody employee;

    @SerializedName("cust_id")
    private RequestBody custId;

    @SerializedName("page_number")
    private RequestBody pageNumber;

    @SerializedName("page_size")
    private RequestBody pageSize;

    public RequestBody getCustId() {
        return custId;
    }

    public void setCustId(RequestBody custId) {
        this.custId = custId;
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

    public RequestBody getEmployee() {
        return employee;
    }

    public void setEmployee(RequestBody employee) {
        this.employee = employee;
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
