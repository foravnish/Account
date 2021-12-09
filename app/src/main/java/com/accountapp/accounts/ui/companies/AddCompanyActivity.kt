package com.accountapp.accounts.ui.companies

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityAddCompanyBinding
import com.accountapp.accounts.databinding.ActivityMyCompaniesBinding
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.ui.ladgerList.ListViewModel
import com.accountapp.accounts.ui.signup.SignupViewModel
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper
import com.chaos.view.PinView

class AddCompanyActivity : BaseActivity() {

    lateinit var binding: ActivityAddCompanyBinding
    val mContext by lazy { this@AddCompanyActivity }
    val mViewModel by lazy { ViewModelProviders.of(mContext).get(SignupViewModel::class.java) }
    var otpVal: String = ""
    var status: String = "";
    override fun initUI() {

        binding= DataBindingUtil.setContentView(this, R.layout.activity_add_company)

        binding.btAddCompany.setOnClickListener {
            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    hitAddCompanyAPi(true)
                }
            }
        }

    }


    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.etCompany,
                getString(R.string.err_com_name),
                binding.root )) {
            if (ValidationHelper.isDataFilled(
                    binding.etGst,
                    getString(R.string.err_gst), binding.root)){
                if (binding.etGst.text.toString().length<15){
                    Utility.showSnackBar(binding.root, getString(R.string.err_gst_alpha))
                }else{
                    return true
                }
            }
        }

        return false
    }



    private fun hitAddCompanyAPi(b: Boolean) {

        Utility.closeKeyboard(binding.root, mContext)
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        val comName = binding.etCompany.text.toString()
        val gstNo = binding.etGst.text.toString().toUpperCase()
        if (b) {
            status = "1"
        } else {
            status = "0"
        }


        mViewModel.callAddCompany(
            comName,
            gstNo,
            status,
            Prefences.getUserMobile(mContext).toString()
        )
            .observe(mContext, object : Observer<SignUpResponse> {
                override fun onChanged(resp: SignUpResponse?) {
                    showLoadingView(
                        false,
                        binding.loadingView.loadingIndicator,
                        binding.loadingView.container
                    )
                    if (resp != null) {
                        if (resp.status.equals("success")) {
                            Utility.showSnackBar(binding.root, "" + resp.msg)

                            if (b) {
                                binding.root.postDelayed({
                                    val intent = Intent(mContext, MyCompaniesActivity::class.java)
                                    Utility.startActivityWithLeftToRightAnimation(mContext,intent)
                                }, 1000)

                            } else {
                                openDialog(resp.otp)
                            }


                        } else {
                            Utility.showSnackBar(binding.root, "" + resp.msg)
                        }
                    } else {
                        showServerErrorSnackbar(binding.root)
                    }
                }

            })

    }


    private fun openDialog(otp: String) {

        val dialog = Dialog(this@AddCompanyActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_otp_dialog)
        dialog.setCancelable(false)

        val btnSubmit = dialog.findViewById<View>(R.id.bt_submit) as Button
        val getMobileNo = dialog.findViewById<View>(R.id.getMobileNo) as TextView
        val pinView = dialog.findViewById<View>(R.id.pinView) as PinView
        getMobileNo.text = "" + Prefences.getUserMobile(mContext)

        pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                otpVal = s.toString()

            }
        })


        btnSubmit.setOnClickListener {

            Log.d("OTPVALUE", otpVal)

            if (otpVal == "") {
                ValidationHelper.showSnackBar(binding.root, getString(R.string.err_otp))
            } else if (otpVal.length < 4) {
                ValidationHelper.showSnackBar(binding.root, getString(R.string.err_invalid_itp))
            } else if (otp.equals(otpVal)) {
                Utility.closeKeyboard(binding.root, mContext)
                dialog.dismiss()
                hitAddCompanyAPi(true)

            } else {
                ValidationHelper.showSnackBar(binding.root, getString(R.string.err_invalid_itp))
            }


        }
        dialog.show()
    }


}
