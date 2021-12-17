package com.ominfo.crm_solution.network;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.util.IOUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ominfo.crm_solution.BuildConfig;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.internal.EverythingIsNonNull;


/* this class is responsible to manage com.app.teketeke.network call*/
@Module
public class NetworkModule {


    private static final String TAG = NetworkModule.class.getSimpleName();
    private static final String contentType = "application/json";
    private static final String contentTypeMultipart = "multipart/form-data";
    private static final String accept = "application/json";
    private String mUserAuthToken = null;
    private String mHostName = "";
    private static OkHttpClient.Builder httpClientBuilder = null;
    Context context;

    public NetworkModule(Activity baseActivity) {
        context = baseActivity;
    }


    @Provides
    @Singleton
    @Inject
    @Named("provideRetrofit2")
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }


    @Provides
    @Singleton
    @Inject
    @Named("provideRetrofit2")
    Retrofit provideRetrofit(@Named("provideRetrofit2") Gson gson, @Named("provideRetrofit2") OkHttpClient okHttpClient) {

        httpClientBuilder = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        initHttpLogging();
        initSSL(context);
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(NetworkURLs.BASE_URL);
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.client(httpClientBuilder.build());//okHttpClient);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build();
    }

    private static void initHttpLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG) httpClientBuilder.addInterceptor(logging);
    }

    private static void initSSL(Context context) {

        SSLContext sslContext = null;
        try {
            sslContext = createCertificate(context.getResources().openRawResource(R.raw.ominfo));
        } catch (CertificateException | IOException | KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(sslContext!=null){
            httpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), systemDefaultTrustManager());
        }

    }

    private static SSLContext createCertificate(InputStream trustedCertificateIS) throws CertificateException, IOException, KeyStoreException, KeyManagementException, NoSuchAlgorithmException{

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        try {
            ca = cf.generateCertificate(trustedCertificateIS);
        } finally {
            trustedCertificateIS.close();
        }

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;

    }

    private static X509TrustManager systemDefaultTrustManager() {

        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }

    }


    /**
     * set ssl certificate at dynamic from real time database (fire-base)
     * there is two way to set ssl pinning in dynamic
     * 1. when app is lunch at that time, call fire-base method for getting update ssl pin
     * 2. when getting error in any api at that time also update the ssl pin
     *
     * @return
     */




    @Provides
    @Singleton
    NetworkAPIServices getApiCallInterface(@Named("provideRetrofit2") Retrofit retrofit) {
        return retrofit.create(NetworkAPIServices.class);
    }

    @Provides
    @Singleton
    @Named("provideRetrofit2")
    OkHttpClient getRequestHeader() {

        try {
            URI uri = new URI(NetworkURLs.BASE_URL);
            mHostName = uri.getHost();
            LogUtil.printLog("Host name", mHostName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (LogUtil.isEnableLogs) { //dont show logs from here
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

        try {
            httpClient.addInterceptor(new Interceptor() {

                @NonNull
                @EverythingIsNonNull
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = null;
                    Response response = null;

                    String url = original.url().toString();
                    Request.Builder requestBuilder = original.newBuilder();
//                    if(SharedPref.getInstance(BaseApplication.getInstance().getApplicationContext()).read(SharedPrefKey.ACCESS_TOKEN,"true"))
                    requestBuilder.addHeader("Content-Type", contentType);
                    //requestBuilder.addHeader("app-id", "61151dc619e074b995f40062");
                    requestBuilder.method(original.method(), original.body());
                    request = requestBuilder.build();
                    response = chain.proceed(request);
                    LogUtil.printLog("Header " ,getRequestHeader().toString());
                    //trustAllCertificates();
                    return response;
                }
            })
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpClient.build();
    }

    private static OkHttpClient createTrustingOkHttpClient() {
        try {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    x509TrustManager
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

   /* @Provides
    @Singleton
    @Named("provideRetrofit2")
    OkHttpClient getRequestHeaderMultipart() {

        try {
            URI uri = new URI(NetworkURLs.BASE_URL);
            mHostName = uri.getHost();
            LogUtil.printLog("Host name", mHostName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (LogUtil.isEnableLogs) { //dont show logs from here
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

        try {
            httpClient.addInterceptor(new Interceptor() {

                @NonNull
                @EverythingIsNonNull
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = null;
                    Response response = null;

                    String url = original.url().toString();
                    Request.Builder requestBuilder = original.newBuilder();
//                    if(SharedPref.getInstance(BaseApplication.getInstance().getApplicationContext()).read(SharedPrefKey.ACCESS_TOKEN,"true"))
                    requestBuilder.addHeader("Content-Type", contentTypeMultipart);
                    requestBuilder.addHeader("Authorization", "Bearer "+ SharedPref.getInstance(BaseApplication.getInstance().getApplicationContext()).read(SharedPrefKey.ACCESS_TOKEN,"true"));
                    requestBuilder.method(original.method(), original.body());
                    request = requestBuilder.build();
                    response = chain.proceed(request);
                    LogUtil.printLog("Header " ,getRequestHeaderMultipart().toString());

                    return response;
                }
            })
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpClient.build();
    }
*/

    @Provides
    @Singleton
    Service getRepository(NetworkAPIServices networkAPIServices) {
        return new Service(networkAPIServices);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Service myService) {
        return new ViewModelFactory(myService);
    }

}
