
package com.ominfo.crm_solution.ui.enquiry_report.model;

import com.google.gson.annotations.SerializedName;
import com.ominfo.crm_solution.network.DynamicAPIPath;

import javax.annotation.Generated;

import okhttp3.MediaType;
import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SaveEnquiryRequest {

    @SerializedName("RequestBodyTypeAction")
    private RequestBody RequestBodyTypeAction;

    @SerializedName("RequestBodyTypeEnquiry")
    private RequestBody RequestBodyTypeEnquiry;

    @SerializedName("RequestBodyTypeCustID")
    private RequestBody RequestBodyTypeCustID;

    @SerializedName("RequestBodyTypeCustName")
    private RequestBody RequestBodyTypeCustName;

    @SerializedName("RequestBodyTypeCustMobile")
    private RequestBody RequestBodyTypeCustMobile;

    @SerializedName("RequestBodyTypeProduct")
    private RequestBody RequestBodyTypeProduct;

    @SerializedName("RequestBodyTypeRm")
    private RequestBody RequestBodyTypeRm;

    @SerializedName("RequestBodyTypeDescription")
    private RequestBody RequestBodyTypeDescription;

    @SerializedName("RequestBodyTypeSource")
    private RequestBody RequestBodyTypeSource;

    @SerializedName("RequestBodyTypeCompID")
    private RequestBody RequestBodyTypeCompID;

    @SerializedName("RequestBodyTypeEmpID")
    private RequestBody RequestBodyTypeEmpID;

    public RequestBody getRequestBodyTypeAction() {
        return RequestBodyTypeAction;
    }

    public void setRequestBodyTypeAction(RequestBody requestBodyTypeAction) {
        RequestBodyTypeAction = requestBodyTypeAction;
    }

    public RequestBody getRequestBodyTypeEnquiry() {
        return RequestBodyTypeEnquiry;
    }

    public void setRequestBodyTypeEnquiry(RequestBody requestBodyTypeEnquiry) {
        RequestBodyTypeEnquiry = requestBodyTypeEnquiry;
    }

    public RequestBody getRequestBodyTypeCustID() {
        return RequestBodyTypeCustID;
    }

    public void setRequestBodyTypeCustID(RequestBody requestBodyTypeCustID) {
        RequestBodyTypeCustID = requestBodyTypeCustID;
    }

    public RequestBody getRequestBodyTypeCustName() {
        return RequestBodyTypeCustName;
    }

    public void setRequestBodyTypeCustName(RequestBody requestBodyTypeCustName) {
        RequestBodyTypeCustName = requestBodyTypeCustName;
    }

    public RequestBody getRequestBodyTypeCustMobile() {
        return RequestBodyTypeCustMobile;
    }

    public void setRequestBodyTypeCustMobile(RequestBody requestBodyTypeCustMobile) {
        RequestBodyTypeCustMobile = requestBodyTypeCustMobile;
    }

    public RequestBody getRequestBodyTypeProduct() {
        return RequestBodyTypeProduct;
    }

    public void setRequestBodyTypeProduct(RequestBody requestBodyTypeProduct) {
        RequestBodyTypeProduct = requestBodyTypeProduct;
    }

    public RequestBody getRequestBodyTypeRm() {
        return RequestBodyTypeRm;
    }

    public void setRequestBodyTypeRm(RequestBody requestBodyTypeRm) {
        RequestBodyTypeRm = requestBodyTypeRm;
    }

    public RequestBody getRequestBodyTypeDescription() {
        return RequestBodyTypeDescription;
    }

    public void setRequestBodyTypeDescription(RequestBody requestBodyTypeDescription) {
        RequestBodyTypeDescription = requestBodyTypeDescription;
    }

    public RequestBody getRequestBodyTypeSource() {
        return RequestBodyTypeSource;
    }

    public void setRequestBodyTypeSource(RequestBody requestBodyTypeSource) {
        RequestBodyTypeSource = requestBodyTypeSource;
    }

    public RequestBody getRequestBodyTypeCompID() {
        return RequestBodyTypeCompID;
    }

    public void setRequestBodyTypeCompID(RequestBody requestBodyTypeCompID) {
        RequestBodyTypeCompID = requestBodyTypeCompID;
    }

    public RequestBody getRequestBodyTypeEmpID() {
        return RequestBodyTypeEmpID;
    }

    public void setRequestBodyTypeEmpID(RequestBody requestBodyTypeEmpID) {
        RequestBodyTypeEmpID = requestBodyTypeEmpID;
    }
}
