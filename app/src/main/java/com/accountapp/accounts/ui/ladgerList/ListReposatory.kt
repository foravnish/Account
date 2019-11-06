package com.accountapp.accounts.ui.ladgerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accountapp.accounts.model.response.LadgerListResponse
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.model.response.SearchCompanyList
import com.accountapp.accounts.model.response.SignUpResponse
import com.google.gson.Gson
import network.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListReposatory {

    fun callSearchData(gstNo: String?, search:String): LiveData<SearchCompanyList> {
        val data = MutableLiveData<SearchCompanyList>()
        AppRetrofit.instance.callSearchData(gstNo,search).enqueue(object : Callback<SearchCompanyList> {
            override fun onFailure(call: Call<SearchCompanyList>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<SearchCompanyList>, response: Response<SearchCompanyList>) {
                if (response.isSuccessful) {
                    data.value = if (response != null && response.body() != null) response.body() else null
                } else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(SearchCompanyList::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
                data.value = null

            }
        })

        return data
    }


//  Ledger Listing
    fun callLadgerList(gstNo: String?, acc_no:String,fromDate:String,endDate:String): LiveData<LadgerListResponse> {
        val data = MutableLiveData<LadgerListResponse>()
        AppRetrofit.instance.callLadgerList(gstNo,acc_no,fromDate,endDate).enqueue(object : Callback<LadgerListResponse> {
            override fun onFailure(call: Call<LadgerListResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<LadgerListResponse>, response: Response<LadgerListResponse>) {
                if (response.isSuccessful) {
                    data.value = if (response != null && response.body() != null) response.body() else null
                } else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(LadgerListResponse::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
                data.value = null

            }
        })

        return data
    }

    fun callPdffGenerateApi(gstNo: String,acc_no:String,fromDate: String,endDate: String): LiveData<PDFGeneratorReponse> {
        val data = MutableLiveData<PDFGeneratorReponse>()
        AppRetrofit.instance.callPdffGenerateApi(gstNo,acc_no,fromDate,endDate).enqueue(object :
            Callback<PDFGeneratorReponse> {
            override fun onFailure(call: Call<PDFGeneratorReponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<PDFGeneratorReponse>, response: Response<PDFGeneratorReponse>) {

                if (response.isSuccessful){
                    data.value = if (response != null && response.body() != null) response!!.body() else null
                }
                else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(PDFGeneratorReponse::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
            }
        })

        return data
    }



}