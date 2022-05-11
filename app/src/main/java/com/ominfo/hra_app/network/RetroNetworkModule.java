package com.ominfo.hra_app.network;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.utility.ApplicationMode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroNetworkModule {

    private static RetroNetworkModule sInstance = null;
    private final NetworkAPIServices mAPI;

    private RetroNetworkModule() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (LogUtil.isEnableLogs) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseApplication.getInstance().
                        getApplicationMode() == ApplicationMode.DEVELOPMENT ?
                        NetworkURLs.BASE_URL :
                        NetworkURLs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        mAPI = retrofit.create(NetworkAPIServices.class);
    }

    public static synchronized RetroNetworkModule getInstance() {
        if (sInstance == null) {
            sInstance = new RetroNetworkModule();
        }
        return sInstance;
    }

    public NetworkAPIServices getAPI() {
        return mAPI;
    }
}
