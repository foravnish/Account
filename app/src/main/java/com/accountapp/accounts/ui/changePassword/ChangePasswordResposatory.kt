package com.accountapp.accounts.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accountapp.accounts.model.response.LoginResponse
import com.google.gson.Gson
import network.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordResposatory {



    fun callChangePassword(mobile: String,old_password: String,new_password: String): LiveData<LoginResponse> {
        val data = MutableLiveData<LoginResponse>()
        AppRetrofit.instance.callChangePassword(mobile,old_password,new_password).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful){
                    data.value = if (response != null && response.body() != null) response!!.body() else null
                }
                else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(LoginResponse::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
            }
        })

        return data
    }

}