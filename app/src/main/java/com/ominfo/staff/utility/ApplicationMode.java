package com.ominfo.staff.utility;

import androidx.annotation.NonNull;

public enum ApplicationMode {
    DEVELOPMENT("Development", 0),
    PRODUCTION("Production", 1);

    private String stringValue;
    private int intValue;

    private ApplicationMode(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @NonNull
    @Override
    public String toString() {
        return stringValue;
    }
}
