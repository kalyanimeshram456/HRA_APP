
package com.ominfo.crm_solution.ui.sales_credit.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesCreditReport {

    @SerializedName("balance")
    private Long mBalance;
    @SerializedName("credit_limit")
    private String mCreditLimit;
    @SerializedName("customer_id")
    private String mCustomerId;
    @SerializedName("customer_name")
    private String mCustomerName;
    @SerializedName("overdue")
    private Long mOverdue;

    public Long getBalance() {
        return mBalance;
    }

    public void setBalance(Long balance) {
        mBalance = balance;
    }

    public String getCreditLimit() {
        return mCreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        mCreditLimit = creditLimit;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public Long getOverdue() {
        return mOverdue;
    }

    public void setOverdue(Long overdue) {
        mOverdue = overdue;
    }

}
