package com.accountapp.accounts.ui.changePassword

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityChangePasswordBinding
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.ui.home.HomeActivity
import com.accountapp.accounts.ui.login.ChangePasswordModel
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper

class ChangePasswordActivity : BaseActivity() {


    lateinit var binding: ActivityChangePasswordBinding

    val mViewModel by lazy { ViewModelProviders.of(mContext).get(ChangePasswordModel::class.java) }
    val mContext by lazy { this@ChangePasswordActivity }


    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)

        binding.btSubmit.setOnClickListener {


            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    callChangePasswordApi(binding.etOldPass.text.toString(),binding.etNewPass.text.toString())
                }
            }

        }
    }

    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.etOldPass,
                getString(R.string.err_old_pass),
                binding.root )) {
            if (ValidationHelper.isDataFilled(
                    binding.etNewPass,
                    getString(R.string.err_new_passs), binding.root)){
                if (ValidationHelper.isDataFilled(
                        binding.etConfirmPassword,
                        getString(R.string.err_confirm), binding.root)){

                    if (ValidationHelper.validatePasswordSameFields(
                            binding.etNewPass,
                            binding.etConfirmPassword,
                            getString(R.string.err_same_password),
                            binding.root
                        )
                    ) {
                        return  true
                    }
                }

            }
        }

        return false
    }


    private fun callChangePasswordApi(old_pass: String, new_passs: String) {


        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callChangePassword(Prefences.getUserMobile(mContext).toString(),old_pass, new_passs)
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
}
