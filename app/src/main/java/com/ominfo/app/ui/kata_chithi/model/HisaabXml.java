
package com.ominfo.app.ui.kata_chithi.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class HisaabXml {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("approved_amount")
    private String mApprovedAmount;
    @SerializedName("field_id")
    private Long mFieldId;
    @SerializedName("field_name")
    private String mFieldName;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getApprovedAmount() {
        return mApprovedAmount;
    }

    public void setApprovedAmount(String approvedAmount) {
        mApprovedAmount = approvedAmount;
    }

    public Long getFieldId() {
        return mFieldId;
    }

    public void setFieldId(Long fieldId) {
        mFieldId = fieldId;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public void setFieldName(String fieldName) {
        mFieldName = fieldName;
    }

}
