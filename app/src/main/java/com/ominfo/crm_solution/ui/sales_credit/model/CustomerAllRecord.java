
package com.ominfo.crm_solution.ui.sales_credit.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CustomerAllRecord {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("docdate")
    private String mDocdate;
    @SerializedName("docnumber")
    private String mDocnumber;
    @SerializedName("docstatus")
    private String mDocstatus;
    @SerializedName("doctype")
    private String mDoctype;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getDocdate() {
        return mDocdate;
    }

    public void setDocdate(String docdate) {
        mDocdate = docdate;
    }

    public String getDocnumber() {
        return mDocnumber;
    }

    public void setDocnumber(String docnumber) {
        mDocnumber = docnumber;
    }

    public String getDocstatus() {
        return mDocstatus;
    }

    public void setDocstatus(String docstatus) {
        mDocstatus = docstatus;
    }

    public String getDoctype() {
        return mDoctype;
    }

    public void setDoctype(String doctype) {
        mDoctype = doctype;
    }

}
