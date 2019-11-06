package com.accountapp.accounts.ui.splash;

import android.os.Handler;

public class SplashRepository {

    long SplashTime = 3000;
    SplashHandler handler;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handler.onSplashCompleted();
        }
    };

    public SplashRepository(SplashHandler handler) {
        this.handler = handler;
    }

    public void onSplashInitiated() {
        new Handler().postDelayed(mRunnable, SplashTime);
    }

    public void removeHandler()
    {
    }

}
