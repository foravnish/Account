package com.accountapp.accounts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.model.response.SignUpResponse

class HomeViewModel : ViewModel(){
    lateinit var mRepo: HomeReposatory

    init {
        mRepo = HomeReposatory()
    }

    fun callReadCompany(gstNo: String?): LiveData<SignUpResponse> {
        return mRepo.callReadCompany(gstNo!!)
    }



}