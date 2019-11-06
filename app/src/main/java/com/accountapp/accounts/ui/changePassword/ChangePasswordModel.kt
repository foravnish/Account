package com.accountapp.accounts.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.LoginResponse

class ChangePasswordModel: ViewModel() {
    lateinit var mRepo: ChangePasswordResposatory

    init {
        mRepo = ChangePasswordResposatory()
    }



    fun callChangePassword(mobile: String,old_password: String,new_password: String): LiveData<LoginResponse> {
        return mRepo.callChangePassword(mobile,old_password,new_password)
    }

}