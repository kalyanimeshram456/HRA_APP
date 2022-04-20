
package com.ominfo.crm_solution.ui.dashboard.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class HighlightModel {

    @SerializedName("highlight")
    private String highlight;

    @SerializedName("dateFrom")
    private String dateFrom;

    @SerializedName("dateTo")
    private String dateTo;

    public HighlightModel(String highlight, String dateFrom, String dateTo) {
        this.highlight = highlight;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
