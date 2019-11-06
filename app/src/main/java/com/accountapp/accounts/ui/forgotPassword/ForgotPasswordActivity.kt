package com.accountapp.accounts.ui.forgotPassword

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityForgotPasswordBinding
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.ui.login.ChangePasswordModel
import com.accountapp.accounts.ui.login.LoginViewModel
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper

class ForgotPasswordActivity : BaseActivity() {

    lateinit var binding: ActivityForgotPasswordBinding

    val mViewModel by lazy { ViewModelProviders.of(mContext).get(LoginViewModel::class.java) }
    val mContext by lazy { this@ForgotPasswordActivity }

    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

       // binding.backToLoginBtn.setOnClickListener { finish() }

        binding.forgotButton.setOnClickListener {

            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    callForgotPassswordAPi(binding.etMobileNo.text.toString())
                }
            }

        }
    }

    private fun callForgotPassswordAPi(mobileNO: String) {

        Utility.closeKeyboard(binding.root, mContext)
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callForgotPassword(mobileNO)
            .observe(mContext, object : Observer<LoginResponse> {
                override fun onChanged(resp: LoginResponse?) {
                    showLoadingView(false, binding.loadingView.loadingIndicator, binding.loadingView.container)
                    if (resp != null) {
                        if (resp.status.equals("success")) {

                            Utility.showSnackBar(binding.root, ""+resp.message)
                            binding.root.postDelayed({
//                                val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
//                                Utility.startActivityWithLeftToRightAnimation(this@ForgotPasswordActivity,intent)
                                finish()
                            },2000)

                        } else {
                            Utility.showSnackBar(binding.root, ""+resp.message)
                        }
                    } else {
                        showServerErrorSnackbar(binding.root)
                    }
                }

            })
    }


    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.etMobileNo,
                getString(R.string.err_user_name),
                binding.root )) {
            return true
        }

        return false
    }


}
