package com.accountapp.accounts.ui.signup

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityRegistrationBinding
import com.accountapp.accounts.model.response.SignUpResponse
import com.accountapp.accounts.utils.Utility
import com.accountapp.accounts.utils.ValidationHelper
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.chaos.view.PinView


class RegistrationActivity : BaseActivity() {

    lateinit var binding: ActivityRegistrationBinding

    val mViewModel by lazy { ViewModelProviders.of(mContext).get(SignupViewModel::class.java) }
    val mContext by lazy { this@RegistrationActivity }
    var otpVal: String = ""
    var status: String = "";
    override fun initUI() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        ShowEnterAnimation()
        initView()
        binding.fab!!.setOnClickListener({ animateRevealClose() })

        binding.btGo.setOnClickListener {
            //            openDialog()
            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    hitSignUpAPi(false)
                }
            }

        }
    }

    private fun openDialog(otp: String) {

        val dialog = Dialog(this@RegistrationActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_otp_dialog)
        dialog.setCancelable(false)

        val btnSubmit = dialog.findViewById<View>(R.id.bt_submit) as Button
        val getMobileNo = dialog.findViewById<View>(R.id.getMobileNo) as TextView
        val pinView = dialog.findViewById<View>(R.id.pinView) as PinView
        getMobileNo.text = "" + binding.txtMobile.text.toString()

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
                hitSignUpAPi(true)

            } else {
                ValidationHelper.showSnackBar(binding.root, getString(R.string.err_invalid_itp))
            }


        }
        dialog.show()
    }


    private fun hitSignUpAPi(b: Boolean) {

        Utility.closeKeyboard(binding.root, mContext)
        showLoadingView(true, binding.loadingView.loadingIndicator, binding.loadingView.container)
        val name = binding.txtName.text.toString()
        val mobile = binding.txtMobile.text.toString()
        val password = binding.txtPasword.text.toString()
        val companyName = binding.txtComName.text.toString()
        val email = binding.txtEmail.text.toString()
        val gst = binding.txtGst.text.toString()
        val address = binding.txtAddress.text.toString()
        val city = binding.txtCity.text.toString()
        if (b) {
            status = "1"
        } else {
            status = "0"
        }


        mViewModel.callSignUp(
            name,
            mobile,
            password,
            companyName,
            email,
            gst,
            address,
            city,
            status
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
                                    animateRevealClose()
                                }, 2000)

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


    private fun initView() {

    }

    private fun ShowEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition)
        window.sharedElementEnterTransition = transition

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                binding.cvAdd!!.setVisibility(View.GONE)
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }


        })
    }

    fun animateRevealShow() {
        val mAnimator = ViewAnimationUtils.createCircularReveal(
            binding.cvAdd,
            binding.cvAdd!!.getWidth() / 2,
            0,
            (binding.fab!!.getWidth() / 2).toFloat(),
            binding.cvAdd!!.getHeight().toFloat()
        )
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                binding.cvAdd!!.setVisibility(View.VISIBLE)
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    fun animateRevealClose() {
        val mAnimator = ViewAnimationUtils.createCircularReveal(
            binding.cvAdd,
            binding.cvAdd!!.getWidth() / 2,
            0,
            binding.cvAdd!!.getHeight().toFloat(),
            (binding.fab!!.getWidth() / 2).toFloat()
        )
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.cvAdd!!.setVisibility(View.INVISIBLE)
                super.onAnimationEnd(animation)
                binding.fab!!.setImageResource(R.drawable.plus)
                super@RegistrationActivity.onBackPressed()
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }


    override fun onBackPressed() {
        animateRevealClose()
    }


    private fun isValidate(): Boolean {
        if (ValidationHelper.isDataFilled(
                binding.txtName,
                getString(R.string.err_name),
                binding.root
            )
        ) {
            if (ValidationHelper.isDataFilled(
                    binding.txtMobile,
                    getString(R.string.err_user_name),
                    binding.root
                )
            ) {

                if (ValidationHelper.isValidPassword(binding.txtPasword)) {
                    if (ValidationHelper.isDataFilled(
                            binding.txtPasword,
                            getString(R.string.err_password),
                            binding.root
                        )
                    ) {
                        if (ValidationHelper.isDataFilled(
                                binding.txtRepeatPass,
                                getString(R.string.err_re_password),
                                binding.root
                            )
                        ) {
                            if (ValidationHelper.isValidPassword(binding.txtRepeatPass)) {
                                if (ValidationHelper.validatePasswordSameFields(
                                        binding.txtPasword,
                                        binding.txtRepeatPass,
                                        getString(R.string.err_same_password),
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
                                                    binding.txtGst,
                                                    getString(R.string.err_gst),
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

                                }
                            }

                        }
                    }else{
                        Utility.showSnackBar(binding.root, getString(R.string.err_invalid_password))
                    }
                } else {
                    Utility.showSnackBar(binding.root, getString(R.string.err_invalid_password))
                }
            }
        }
        return false
    }

}
