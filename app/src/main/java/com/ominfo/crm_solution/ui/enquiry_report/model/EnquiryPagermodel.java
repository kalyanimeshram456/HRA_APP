
package com.ominfo.crm_solution.ui.enquiry_report.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EnquiryPagermodel {

    @SerializedName("page_no")
    private String pageNo;
    @SerializedName("status")
    private int status;

    public EnquiryPagermodel(String pageNo, int status) {
        this.pageNo = pageNo;
        this.status = status;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
