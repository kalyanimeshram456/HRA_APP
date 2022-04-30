
package com.ominfo.crm_solution.ui.my_account.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RaiseTicketRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("pageno")
    private RequestBody pageNo;

    @SerializedName("pagesize")
    private RequestBody pageSize;

    @SerializedName("start_time")
    private RequestBody startTime;


}
