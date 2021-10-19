package com.ominfo.staff.basecontrol;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.EditText;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.encryptionhandler.AESEncrypterUpdated;
import com.ominfo.staff.interfaces.Constants;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.util.AppUtils;
import com.ominfo.staff.util.LocaleManager;
import com.ominfo.staff.util.LogUtil;
import com.ominfo.staff.util.SharedPref;
import com.ominfo.staff.utility.ApplicationMode;

import java.security.Key;

/**
 * this base application class
 */
public class BaseApplication extends MultiDexApplication {
    private static final String TAG = "BaseApplication";
    private ApplicationMode mApplicationMode;
    private AppDatabase mDB;
    private final String DB_NAME = "sendbits";
    public boolean isApplicationBackgruond = false;
    private Context mCtx;

    // for the sake of simplicity. use DI in real apps instead
    public static LocaleManager localeManager;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static BaseApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        localeManager = new LocaleManager(getBaseContext());
        sInstance = this;
        //change application mode
        mApplicationMode = ApplicationMode.PRODUCTION;
    }

    public static synchronized BaseApplication getInstance(Context mCtx) {
        if (sInstance == null) {
            sInstance = new BaseApplication(mCtx);
        }
        return sInstance;
    }


    /**
     * this method return application mode i.e.development mode or production mode
     *
     * @return ApplicationMode
     */
    public ApplicationMode getApplicationMode() {
        return mApplicationMode;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }


    private EditText getEditText() {

        String tag = "";
        try {
            AESEncrypterUpdated encrypter = new AESEncrypterUpdated();
            Key key = encrypter.getEncryptionKey(Constants.SECRET_KEY);
            if (SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.RANDOM_STRING, null) == null) {
                tag = AppUtils.generateRandomString();
                String temp = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    temp = encrypter.encryptDBKey(tag, key);
                }
                SharedPref.getInstance(getApplicationContext()).write(SharedPrefKey.RANDOM_STRING, temp);
            } else {
                tag = encrypter.decryptDBKey(SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.RANDOM_STRING, ""), key);
            }
        } catch (Exception ex) {
            LogUtil.printLog(BaseApplication.class.getSimpleName(), "Exception Text" + ex.toString());
        }

        EditText editText = new EditText(this);
        editText.setText(tag);
        return editText;
    }

    private BaseApplication(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        mDB = Room.databaseBuilder(mCtx,
                AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                //.openHelperFactory(factory)
                //   .addMigrations(DBMigration.MIGRATION_2_3)
                .build();
    }


    public AppDatabase getAppDatabase() {
        return mDB;
    }

    @Override
    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
        localeManager = new LocaleManager(base);
        MultiDex.install(this);
        super.attachBaseContext(localeManager.setLocale(base));

    }

    /**
     * converting bytes to Hex string
     * @param bytes data
     * @return hex string
     */
    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }

}