
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CustomerList {

    @SerializedName("customer_mobile")
    private String mCustomerMobile;
    @SerializedName("customer_name")
    private String mCustomerName;
    @SerializedName("rm")
    private String mRm;

    public String getCustomerMobile() {
        return mCustomerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        mCustomerMobile = customerMobile;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public String getRm() {
        return mRm;
    }

    public void setRm(String rm) {
        mRm = rm;
    }

}
