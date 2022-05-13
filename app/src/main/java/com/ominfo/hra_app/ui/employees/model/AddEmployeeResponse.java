
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddEmployeeResponse {

    @SerializedName("result")
    private AddEmployeeResult mResult;

    public AddEmployeeResult getResult() {
        return mResult;
    }

    public void setResult(AddEmployeeResult result) {
        mResult = result;
    }

}
