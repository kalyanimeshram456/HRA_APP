package com.ominfo.app.interfaces;

import android.os.IBinder;

public interface ServiceCallBackInterface {

    void isServiceActiveCall(boolean is, IBinder service);
}
