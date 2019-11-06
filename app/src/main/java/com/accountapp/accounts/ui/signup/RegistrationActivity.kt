package com.accountapp.accounts.ui.signup

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.transition.Transition
import android.transition.TransitionInflater
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

class RegistrationActivity : BaseActivity() {

    lateinit var binding: ActivityRegistrationBinding

    val mViewModel by lazy { ViewModelProviders.of(mContext).get(SignupViewModel::class.java) }
    val mContext by lazy { this@RegistrationActivity }


    override fun initUI() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        ShowEnterAnimation()
        initView()
        binding.fab!!.setOnClickListener(View.OnClickListener { animateRevealClose() })

        binding.btGo.setOnClickListener {
            if (isInternetAvailable(binding.root, mContext)) {
                if (isValidate()) {
                    hitSignUpAPi()
                }
            }

        }
    }

    private fun hitSignUpAPi() {
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

//        var req=SignUpRequest(
//            ""+name,
//            ""+mobile,
//            ""+password,
//            ""+companyName,
//            ""+email,
//            ""+gst,
//            ""+address,
//            ""+city
//        )
        mViewModel.callSignUp(name, mobile, password, companyName, email, gst, address, city)
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

                            binding.root.postDelayed({
                                animateRevealClose()
                            }, 2000)


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
            }
        }
        return false
    }

}
