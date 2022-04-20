
package com.ominfo.crm_solution.ui.product.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProductRequest {

    @SerializedName("pageno")
    private String mPageno;
    @SerializedName("pagesize")
    private String mPagesize;

    public String getPageno() {
        return mPageno;
    }

    public void setPageno(String pageno) {
        mPageno = pageno;
    }

    public String getPagesize() {
        return mPagesize;
    }

    public void setPagesize(String pagesize) {
        mPagesize = pagesize;
    }

}
