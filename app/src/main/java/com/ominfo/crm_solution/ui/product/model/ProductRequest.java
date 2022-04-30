
package com.ominfo.crm_solution.ui.product.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProductRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("pageno")
    private RequestBody pageno;

    @SerializedName("pagesize")
    private RequestBody pagesize;

    @SerializedName("prod_code")
    private RequestBody prodCode;

    @SerializedName("prod_name")
    private RequestBody prodName;

    public RequestBody getProdName() {
        return prodName;
    }

    public void setProdName(RequestBody prodName) {
        this.prodName = prodName;
    }

    public RequestBody getProdCode() {
        return prodCode;
    }

    public void setProdCode(RequestBody prodCode) {
        this.prodCode = prodCode;
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
