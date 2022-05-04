
package com.ominfo.crm_solution.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetDashboardResponse {

    @SerializedName("dashboard")
    private Dashboard mDashboard;
    @SerializedName("result")
    private Result mResult;

    public Dashboard getDashboard() {
        return mDashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        mDashboard = dashboard;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

}
