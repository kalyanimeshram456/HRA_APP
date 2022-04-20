
package com.ominfo.crm_solution.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetDashboardResponse {

    @SerializedName("dashboard")
    private Dashboard mDashboard;
    @SerializedName("result")
    private GetDashboardResult mResult;

    public Dashboard getDashboard() {
        return mDashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        mDashboard = dashboard;
    }

    public GetDashboardResult getResult() {
        return mResult;
    }

    public void setResult(GetDashboardResult result) {
        mResult = result;
    }

}
