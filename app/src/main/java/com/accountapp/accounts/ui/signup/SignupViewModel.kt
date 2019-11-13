package com.accountapp.accounts.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.SignUpResponse

class SignupViewModel: ViewModel() {

    lateinit var mRepo: SignupReposatory

    init {
        mRepo = SignupReposatory()
    }

    fun callSignUp(name: String,mobile: String,login_pass: String,company_name: String,email: String,gst: String,address: String, city: String,status: String): LiveData<SignUpResponse> {
        return mRepo.callSignUp(name,mobile,login_pass,company_name,email,gst,address,city,status)
    }
}