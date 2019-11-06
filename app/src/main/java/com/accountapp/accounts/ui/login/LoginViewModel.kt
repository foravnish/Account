package com.accountapp.accounts.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.LoginResponse

class LoginViewModel: ViewModel() {
    lateinit var mRepo: LoginResposatory

    init {
        mRepo = LoginResposatory()
    }

    fun callLogin(phone_no: String,pass:String): LiveData<LoginResponse> {
        return mRepo.callLogin(phone_no,pass)
    }

    fun callForgotPassword(phone_no: String): LiveData<LoginResponse> {
        return mRepo.callForgotPassword(phone_no)
    }

}