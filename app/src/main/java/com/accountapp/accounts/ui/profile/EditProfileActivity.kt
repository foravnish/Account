package com.accountapp.accounts.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityEditProfileBinding
import com.accountapp.accounts.databinding.ActivityHomeBinding
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.ui.signup.SignupViewModel
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper

class EditProfileActivity : BaseActivity() {
    val mContext by lazy { this@EditProfileActivity }
    lateinit var binding: ActivityEditProfileBinding
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(SignupViewModel::class.java) }

    override fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        setToolbarWithBackIcon(binding.includedToolbar.findViewById(R.id.toolbar), "Edit Profile")

        binding.txtName.setText("" + Prefences.getUserName(mContext!!))
        binding.txtEmail.setText("" + Prefences.getUserEmailId(mContext!!))
        binding.txtComName.setText("" + Prefences.getCompany(mContext!!))
        binding.txtAddress.setText("" + Prefences.getAddress(mContext!!))
        binding.txtCity.setText("" + Prefences.getCity(mContext!!))

        binding.btGo.setOnClickListener {
            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    callEditApi()
                }
            }
        }

    }

    private fun callEditApi() {
        Utility.closeKeyboard(binding.root, mContext)
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        mViewModel.callEditProfile(
                "" + Prefences.getUserId(mContext),
                "" + binding.txtName.text.toString(),
                "" + binding.txtComName.text.toString(),
                "" + binding.txtEmail.text.toString(),
                "" + binding.txtAddress.text.toString(),
                "" + binding.txtCity.text.toString()
            )
            .observe(mContext, object : Observer<LoginResponse> {
                override fun onChanged(resp: LoginResponse?) {
                    showLoadingView(
                        false,
                        binding.loadingView.loadingIndicator,
                        binding.loadingView.container
                    )
                    if (resp != null) {

                        Prefences.setUserName(mContext,binding.txtName.text.toString())
                        Prefences.setUserEmailId(mContext,binding.txtEmail.text.toString())
                        Prefences.setCompany(mContext,binding.txtComName.text.toString())
                        Prefences.setAddress(mContext,binding.txtAddress.text.toString())
                        Prefences.setCity(mContext,binding.txtCity.text.toString())

                        Utility.showSnackBar(binding.root, "" + resp.message)

                        binding.root.postDelayed({
                            val intent = Intent(mContext, ProfileFragemnt::class.java)
                            Utility.startActivityWithLeftToRightAnimation(mContext,intent)
                        }, 1000)

                    } else {
                        showServerErrorSnackbar(binding.root)
                    }
                }

            })


    }

    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.txtName,
                getString(R.string.err_name),
                binding.root
            )
        ) {

            if (ValidationHelper.isDataFilled(
                    binding.txtComName,
                    getString(R.string.err_com_name),
                    binding.root
                )
            ) {
                if (ValidationHelper.isDataFilled(
                        binding.txtEmail,
                        getString(R.string.err_email),
                        binding.root
                    )
                ) {
                    if (ValidationHelper.isDataFilled(
                            binding.txtAddress,
                            getString(R.string.err_address),
                            binding.root
                        )
                    ) {
                        if (ValidationHelper.isDataFilled(
                                binding.txtCity,
                                getString(R.string.err_city),
                                binding.root
                            )
                        ) {
                            return true

                        }
                    }

                }
            }


        }

        return false
    }

}
