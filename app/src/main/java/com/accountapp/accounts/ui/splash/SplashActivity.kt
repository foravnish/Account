package com.accountapp.accounts.ui.splash

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivitySplashBinding
import com.accountapp.accounts.ui.home.HomeActivity
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.utils.DirectoryCreate
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility

class SplashActivity :BaseActivity() , SplashHandler{

    lateinit var binding: ActivitySplashBinding
    internal var mSplashRepo = SplashRepository(this)
    val mContext by lazy { this@SplashActivity }

    override fun initUI() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash)
        mSplashRepo.onSplashInitiated()


    }

    override fun onSplashCompleted() {
        val isUserLoggedIn = Prefences.getIsLogin(mContext)

        if (isUserLoggedIn)
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, HomeActivity::class.java)
            )
        else
            Utility.startActivityWithLeftToRightAnimation(
                this,
                Intent(this, LoginActivity::class.java)
            )
        finish()
    }

}
