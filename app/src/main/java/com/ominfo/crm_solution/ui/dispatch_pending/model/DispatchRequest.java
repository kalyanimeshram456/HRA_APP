
package com.ominfo.crm_solution.ui.dispatch_pending.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DispatchRequest {

    @SerializedName("action")
    private RequestBody action;
    @SerializedName("company_id")
    private RequestBody companyId;
    @SerializedName("employee_id")
    private RequestBody employeeId;
    @SerializedName("pageno")
    private RequestBody pageno;
    @SerializedName("pagesize")
    private RequestBody pagesize;
    @SerializedName("po_number")
    private RequestBody poNumber;
    @SerializedName("pending_qty")
    private RequestBody quantity;
    @SerializedName("companyName")
    private RequestBody companyName;
    @SerializedName("rmID")
    private RequestBody rmId;
    @SerializedName("Startdate")
    private RequestBody startDate;
    @SerializedName("EndDate")
    private RequestBody endDate;

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

    public RequestBody getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(RequestBody employeeId) {
        this.employeeId = employeeId;
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

    public RequestBody getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(RequestBody poNumber) {
        this.poNumber = poNumber;
    }

    public RequestBody getQuantity() {
        return quantity;
    }

    public void setQuantity(RequestBody quantity) {
        this.quantity = quantity;
    }

    public RequestBody getCompanyName() {
        return companyName;
    }

    public void setCompanyName(RequestBody companyName) {
        this.companyName = companyName;
    }

    public RequestBody getRmId() {
        return rmId;
    }

    public void setRmId(RequestBody rmId) {
        this.rmId = rmId;
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
}
