package com.ominfo.hra_app.interfaces;

import android.os.IBinder;

public interface ServiceCallBackInterface {

    void isServiceActiveCall(boolean is, IBinder service);
}
