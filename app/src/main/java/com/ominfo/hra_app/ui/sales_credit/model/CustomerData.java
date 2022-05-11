
package com.ominfo.hra_app.ui.sales_credit.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CustomerData {

    @SerializedName("All Records")
    private List<CustomerAllRecord> mAllRecords;
    @SerializedName("Customer Credit Balance")
    private String mCustomerCreditBalance;
    @SerializedName("Customer Details")
    private CustomerDetails mCustomerDetails;
    @SerializedName("result")
    private CustomerResult mResult;
    @SerializedName("Yearly Inv Record")
    private List<YearlyInvRecord> mYearlyInvRecord;
    @SerializedName("Yearly Record")
    private List<Object> mYearlyRecord;
    @SerializedName("Yearly SO Record")
    private List<Object> mYearlySORecord;

    public List<CustomerAllRecord> getAllRecords() {
        return mAllRecords;
    }

    public void setAllRecords(List<CustomerAllRecord> allRecords) {
        mAllRecords = allRecords;
    }

    public String getCustomerCreditBalance() {
        return mCustomerCreditBalance;
    }

    public void setCustomerCreditBalance(String customerCreditBalance) {
        mCustomerCreditBalance = customerCreditBalance;
    }

    public CustomerDetails getCustomerDetails() {
        return mCustomerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        mCustomerDetails = customerDetails;
    }

    public CustomerResult getResult() {
        return mResult;
    }

    public void setResult(CustomerResult result) {
        mResult = result;
    }

    public List<YearlyInvRecord> getYearlyInvRecord() {
        return mYearlyInvRecord;
    }

    public void setYearlyInvRecord(List<YearlyInvRecord> yearlyInvRecord) {
        mYearlyInvRecord = yearlyInvRecord;
    }

    public List<Object> getYearlyRecord() {
        return mYearlyRecord;
    }

    public void setYearlyRecord(List<Object> yearlyRecord) {
        mYearlyRecord = yearlyRecord;
    }

    public List<Object> getYearlySORecord() {
        return mYearlySORecord;
    }

    public void setYearlySORecord(List<Object> yearlySORecord) {
        mYearlySORecord = yearlySORecord;
    }

}
