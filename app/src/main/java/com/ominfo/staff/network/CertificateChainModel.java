package com.ominfo.staff.network;

import com.google.gson.annotations.SerializedName;

public class CertificateChainModel {
    @SerializedName("shaPINHash")
    private String shaPINHash;

    @SerializedName("serialNumber")
    private String serialNumber;

    public String getShaPINHash() {
        return shaPINHash;
    }

    public void setShaPINHash(String shaPINHash) {
        this.shaPINHash = shaPINHash;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
