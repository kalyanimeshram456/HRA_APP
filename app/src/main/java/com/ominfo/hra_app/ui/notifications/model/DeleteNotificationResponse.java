
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DeleteNotificationResponse {

    @SerializedName("result")
    private DeleteNotiResult mResult;

    public DeleteNotiResult getResult() {
        return mResult;
    }

    public void setResult(DeleteNotiResult result) {
        mResult = result;
    }

}
