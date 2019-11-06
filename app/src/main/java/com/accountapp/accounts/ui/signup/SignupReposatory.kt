package com.accountapp.accounts.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accountapp.accounts.model.request.SignUpRequest
import com.accountapp.accounts.model.response.LoginResponse
import com.accountapp.accounts.model.response.SignUpResponse
import com.google.gson.Gson
import network.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupReposatory {

    fun callSignUp(name: String,mobile: String,login_pass: String,company_name: String,email: String,gst: String,address: String, city: String): LiveData<SignUpResponse> {
        val data = MutableLiveData<SignUpResponse>()
        AppRetrofit.instance.callSignUp(name,mobile,login_pass,company_name,email,gst,address,city).enqueue(object :
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