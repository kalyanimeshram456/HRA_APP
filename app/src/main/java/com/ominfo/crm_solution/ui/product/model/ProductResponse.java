
package com.ominfo.crm_solution.ui.product.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProductResponse {

    @SerializedName("currentpage")
    private Long mCurrentpage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("products")
    private List<ProductResult> mProducts;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("totalpages")
    private Long mTotalpages;
    @SerializedName("totalproducts")
    private Long mTotalproducts;

    public Long getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(Long currentpage) {
        mCurrentpage = currentpage;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getNextpage() {
        return mNextpage;
    }

    public void setNextpage(Long nextpage) {
        mNextpage = nextpage;
    }

    public Long getPrevpage() {
        return mPrevpage;
    }

    public void setPrevpage(Long prevpage) {
        mPrevpage = prevpage;
    }

    public List<ProductResult> getProducts() {
        return mProducts;
    }

    public void setProducts(List<ProductResult> products) {
        mProducts = products;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

    public Long getTotalproducts() {
        return mTotalproducts;
    }

    public void setTotalproducts(Long totalproducts) {
        mTotalproducts = totalproducts;
    }

}
