package com.accountapp.accounts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.model.response.SignUpResponse
import com.google.gson.Gson
import network.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeReposatory {

    fun callReadCompany(gstNo: String,session:String): LiveData<SignUpResponse> {
        val data = MutableLiveData<SignUpResponse>()
        AppRetrofit.instance.callReadCompany(gstNo,session).enqueue(object :
            Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {

                if (response.isSuccessful){
                    data.value = if (response != null && response.body() != null) response!!.body() else null
                }
                else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(SignUpResponse::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
            }
        })

        return data
    }



}