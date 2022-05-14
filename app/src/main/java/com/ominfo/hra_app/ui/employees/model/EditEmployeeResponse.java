
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EditEmployeeResponse {

    @SerializedName("result")
    private EditEmployeeResult mResult;

    public EditEmployeeResult getResult() {
        return mResult;
    }

    public void setResult(EditEmployeeResult result) {
        mResult = result;
    }

}
