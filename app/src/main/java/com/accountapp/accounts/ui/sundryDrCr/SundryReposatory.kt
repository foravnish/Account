package com.accountapp.accounts.ui.sundryDrCr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.model.response.TrialBalanceRespone
import com.google.gson.Gson
import network.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SundryReposatory {


    // Api Calling for Sundry Creditor
    fun callSundryCredator(
        gstNo: String,
        fromDate: String,
        endDate: String
    ): LiveData<TrialBalanceRespone> {
        val data = MutableLiveData<TrialBalanceRespone>()
        AppRetrofit.instance.callSundryCradator(gstNo, fromDate, endDate)
            .enqueue(object : Callback<TrialBalanceRespone> {
                override fun onFailure(call: Call<TrialBalanceRespone>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<TrialBalanceRespone>,
                    response: Response<TrialBalanceRespone>
                ) {
                    if (response.isSuccessful) {
                        data.value =
                            if (response != null && response.body() != null) response.body() else null
                    } else {
                        val gson = Gson()
                        val adapter = gson.getAdapter(TrialBalanceRespone::class.java)
                        if (response.errorBody() != null)
                            data.value = adapter.fromJson(response.errorBody()!!.string())
                    }
                    data.value = null

                }
            })

        return data
    }


    // Api Calling for Sundry Creditor PDF
    fun callSundryCredatorPDF(
        gstNo: String,
        fromDate: String,
        endDate: String
    ): LiveData<PDFGeneratorReponse> {
        val data = MutableLiveData<PDFGeneratorReponse>()
        AppRetrofit.instance.callSundryCradatorPDF(gstNo, fromDate, endDate)
            .enqueue(object : Callback<PDFGeneratorReponse> {
                override fun onFailure(call: Call<PDFGeneratorReponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<PDFGeneratorReponse>,
                    response: Response<PDFGeneratorReponse>
                ) {
                    if (response.isSuccessful) {
                        data.value =
                            if (response != null && response.body() != null) response.body() else null
                    } else {
                        val gson = Gson()
                        val adapter = gson.getAdapter(PDFGeneratorReponse::class.java)
                        if (response.errorBody() != null)
                            data.value = adapter.fromJson(response.errorBody()!!.string())
                    }
                    data.value = null

                }
            })

        return data
    }




    // Sundry Debator
    fun callSundryDebator(
        gstNo: String,
        fromDate: String,
        endDate: String
    ): LiveData<TrialBalanceRespone> {
        val data = MutableLiveData<TrialBalanceRespone>()
        AppRetrofit.instance.callSundryDebator(gstNo, fromDate, endDate)
            .enqueue(object : Callback<TrialBalanceRespone> {
                override fun onFailure(call: Call<TrialBalanceRespone>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<TrialBalanceRespone>,
                    response: Response<TrialBalanceRespone>
                ) {
                    if (response.isSuccessful) {
                        data.value =
                            if (response != null && response.body() != null) response.body() else null
                    } else {
                        val gson = Gson()
                        val adapter = gson.getAdapter(TrialBalanceRespone::class.java)
                        if (response.errorBody() != null)
                            data.value = adapter.fromJson(response.errorBody()!!.string())
                    }
                    data.value = null

                }
            })

        return data
    }


    // Sundry Debator PDF
    fun callSundryDebatorPDF(
        gstNo: String,
        fromDate: String,
        endDate: String
    ): LiveData<PDFGeneratorReponse> {
        val data = MutableLiveData<PDFGeneratorReponse>()
        AppRetrofit.instance.callSundryDebatorPDF(gstNo, fromDate, endDate)
            .enqueue(object : Callback<PDFGeneratorReponse> {
                override fun onFailure(call: Call<PDFGeneratorReponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<PDFGeneratorReponse>,
                    response: Response<PDFGeneratorReponse>
                ) {
                    if (response.isSuccessful) {
                        data.value =
                            if (response != null && response.body() != null) response.body() else null
                    } else {
                        val gson = Gson()
                        val adapter = gson.getAdapter(PDFGeneratorReponse::class.java)
                        if (response.errorBody() != null)
                            data.value = adapter.fromJson(response.errorBody()!!.string())
                    }
                    data.value = null

                }
            })

        return data
    }

}