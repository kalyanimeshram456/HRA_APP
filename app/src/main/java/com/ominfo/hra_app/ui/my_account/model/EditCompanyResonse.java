
package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EditCompanyResonse {

    @SerializedName("result")
    private EditCompanyResult mResult;

    public EditCompanyResult getResult() {
        return mResult;
    }

    public void setResult(EditCompanyResult result) {
        mResult = result;
    }

}
