
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalarySheetList {

    @SerializedName("id")
    private String mId;
    @SerializedName("leaves")
    private String mLeaves;
    @SerializedName("month")
    private String mMonth;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("year")
    private String mYear;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLeaves() {
        return mLeaves;
    }

    public void setLeaves(String leaves) {
        mLeaves = leaves;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

}
