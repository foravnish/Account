package com.accountapp.accounts.ui.login

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityMainBinding
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.ui.forgotPassword.ForgotPasswordActivity
import com.accountapp.accounts.ui.home.HomeActivity
import com.accountapp.accounts.ui.signup.RegistrationActivity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    val mViewModel by lazy { ViewModelProviders.of(mContext).get(LoginViewModel::class.java) }
    val mContext by lazy { this@LoginActivity }


    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListener()
        forgotPassword()
    }

    private fun forgotPassword() {

        binding.txtForgotPassword.setOnClickListener {

            Utility.startActivityWithLeftToRightAnimation(this@LoginActivity, Intent(this@LoginActivity, ForgotPasswordActivity::class.java))

        }

    }


    private fun callLoginAPi(userName: String, password: String) {
        Utility.closeKeyboard(binding.root, mContext)
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callLogin(userName, password)
            .observe(mContext, object : Observer<LoginResponse> {
                override fun onChanged(resp: LoginResponse?) {
                       showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            Prefences.setIsLogin(mContext,true)
                            Prefences.setUserId(mContext,resp.detail.id)
                            Prefences.setUserName(mContext,resp.detail.name)
                            Prefences.setUserMobile(mContext,resp.detail.mobile)
                            Prefences.setUserEmailId(mContext,resp.detail.email)
                            Prefences.setGST_No(mContext,resp.detail.gst)
                            Prefences.setAddress(mContext,resp.detail.address)
                            Prefences.setCompany(mContext,resp.detail.company)
                            Prefences.setCity(mContext,resp.detail.city)

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            Utility.startActivityWithLeftToRightAnimation(this@LoginActivity,intent)
                            finish()

                        } else {
                            Utility.showSnackBar(binding.root, ""+resp.message)
                        }
                    } else {
                        showServerErrorSnackbar(binding.root)
                    }
                }

            })

    }

    private fun setListener() {
        binding.btGo!!.setOnClickListener(View.OnClickListener {

            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    callLoginAPi(binding.etUsername.text.toString(), binding.etPassword.text.toString())
                }
            }

        })
        binding.fab!!.setOnClickListener(View.OnClickListener {
            window.exitTransition = null
            window.enterTransition = null
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@LoginActivity,
                binding.fab,
                binding.fab!!.getTransitionName()
            )
            startActivity(
                Intent(this@LoginActivity, RegistrationActivity::class.java),
                options.toBundle()
            )
        })
    }

    override fun onRestart() {
        super.onRestart()
        binding.fab!!.setVisibility(View.GONE)
    }

    override fun onResume() {
        super.onResume()
        binding.fab!!.setVisibility(View.VISIBLE)
    }

    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.etUsername,
                getString(R.string.err_user_name),
                binding.root )) {
            if (ValidationHelper.isDataFilled(
                    binding.etPassword,
                    getString(R.string.err_password), binding.root)){
                return true
            }
        }

        return false
    }


}
