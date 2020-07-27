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

    fun callSignUp(name: String,mobile: String,login_pass: String,company_name: String,email: String,gst: String,address: String, city: String,status: String): LiveData<SignUpResponse> {
        val data = MutableLiveData<SignUpResponse>()
        AppRetrofit.instance.callSignUp(name,mobile,login_pass,company_name,email,gst,address,city,status).enqueue(object :
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



    fun callAddCompany(comName: String,gst: String,status: String,mobile: String): LiveData<SignUpResponse> {
        val data = MutableLiveData<SignUpResponse>()
        AppRetrofit.instance.callAddCompany(comName,gst,status,mobile).enqueue(object :
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
    fun callEditProfile(id: String,name: String,com_name: String,email: String,address: String,city:String): LiveData<LoginResponse> {
        val data = MutableLiveData<LoginResponse>()
        AppRetrofit.instance.callEditProfile(id,name,com_name,email,address,city).enqueue(object :
            Callback<LoginResponse> {
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