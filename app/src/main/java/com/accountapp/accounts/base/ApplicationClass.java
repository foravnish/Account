package com.accountapp.accounts.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;



public class ApplicationClass extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    private static boolean IS_CONNECTED;
    private static int numRunningActivities = 0;
    private static String TAG = ApplicationClass.class.getName();

    public static String TOKEN="";
    private static ApplicationClass instance;
    private Activity mCurrentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(this);
        //register for connectivity change receiver

        // Network service for S3 upload

//        Fabric.with(this, new Crashlytics());
    }



    public static ApplicationClass getInstance() {
        return instance;
    }


    public static Context getContext(){
        return instance;
    }


    public static boolean isAppInForeground() {
        return numRunningActivities > 0;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static boolean isConnectedToInternet() {
        return IS_CONNECTED;
    }

    public static void setIsConnected(boolean isConnected) {
        IS_CONNECTED = isConnected;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "Running activities = " + numRunningActivities);

//        TOKEN= Prefences.getAuthToken(this);
//        Log.d("TokenAUTH","token:- "+TOKEN);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        numRunningActivities++;
        Log.d(TAG, "Running activities = " + numRunningActivities);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (numRunningActivities == 1) {
            Log.i(TAG, "No running activities left, app has likely entered the background.");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        numRunningActivities--;
        Log.d(TAG, "Running activities = " + numRunningActivities);
        if (numRunningActivities == 0) {
            Log.i(TAG, "No running activities left, app has likely entered the background.");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
