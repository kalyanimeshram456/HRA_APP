
package com.ominfo.crm_solution.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EditVisitResult {

    @SerializedName("affectedRows")
    private Long mAffectedRows;
    @SerializedName("changedRows")
    private Long mChangedRows;
    @SerializedName("fieldCount")
    private Long mFieldCount;
    @SerializedName("insertId")
    private Long mInsertId;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("protocol41")
    private Boolean mProtocol41;
    @SerializedName("serverStatus")
    private Long mServerStatus;
    @SerializedName("warningCount")
    private Long mWarningCount;

    public Long getAffectedRows() {
        return mAffectedRows;
    }

    public void setAffectedRows(Long affectedRows) {
        mAffectedRows = affectedRows;
    }

    public Long getChangedRows() {
        return mChangedRows;
    }

    public void setChangedRows(Long changedRows) {
        mChangedRows = changedRows;
    }

    public Long getFieldCount() {
        return mFieldCount;
    }

    public void setFieldCount(Long fieldCount) {
        mFieldCount = fieldCount;
    }

    public Long getInsertId() {
        return mInsertId;
    }

    public void setInsertId(Long insertId) {
        mInsertId = insertId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getProtocol41() {
        return mProtocol41;
    }

    public void setProtocol41(Boolean protocol41) {
        mProtocol41 = protocol41;
    }

    public Long getServerStatus() {
        return mServerStatus;
    }

    public void setServerStatus(Long serverStatus) {
        mServerStatus = serverStatus;
    }

    public Long getWarningCount() {
        return mWarningCount;
    }

    public void setWarningCount(Long warningCount) {
        mWarningCount = warningCount;
    }

}
