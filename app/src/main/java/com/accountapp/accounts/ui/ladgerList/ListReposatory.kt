package com.accountapp.accounts.ui.ladgerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accountapp.accounts.model.response.*
import com.google.gson.Gson
import network.AppRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListReposatory {

    fun callSearchData(gstNo: String?, search: String): LiveData<SearchCompanyList> {
        val data = MutableLiveData<SearchCompanyList>()
        AppRetrofit.instance.callSearchData(gstNo, search)
            .enqueue(object : Callback<SearchCompanyList> {
                override fun onFailure(call: Call<SearchCompanyList>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<SearchCompanyList>,
                    response: Response<SearchCompanyList>
                ) {
                    if (response.isSuccessful) {
                        data.value =
                            if (response != null && response.body() != null) response.body() else null
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
    fun callLadgerList(
        gstNo: String?,
        acc_no: String,
        fromDate: String,
        endDate: String,
        financial_year:String
    ): LiveData<LadgerListResponse> {
        val data = MutableLiveData<LadgerListResponse>()
        AppRetrofit.instance.callLadgerList(gstNo, acc_no, fromDate, endDate,financial_year)
            .enqueue(object : Callback<LadgerListResponse> {
                override fun onFailure(call: Call<LadgerListResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<LadgerListResponse>,
                    response: Response<LadgerListResponse>
                ) {
                    if (response.isSuccessful) {
                        data.value =
                            if (response != null && response.body() != null) response.body() else null
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

    fun callPdffGenerateApi(
        gstNo: String,
        acc_no: String,
        fromDate: String,
        endDate: String,
        financial_year:String
    ): LiveData<PDFGeneratorReponse> {
        val data = MutableLiveData<PDFGeneratorReponse>()
        AppRetrofit.instance.callPdffGenerateApi(gstNo, acc_no, fromDate, endDate,financial_year).enqueue(object :
            Callback<PDFGeneratorReponse> {
            override fun onFailure(call: Call<PDFGeneratorReponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<PDFGeneratorReponse>,
                response: Response<PDFGeneratorReponse>
            ) {

                if (response.isSuccessful) {
                    data.value =
                        if (response != null && response.body() != null) response!!.body() else null
                } else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(PDFGeneratorReponse::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
            }
        })

        return data
    }


    // My Company listing Repo
    fun callCompanyList(mobileNo: String?): LiveData<CompanyListingResponse> {
        val data = MutableLiveData<CompanyListingResponse>()
        AppRetrofit.instance.callCompanyList(mobileNo).enqueue(object :
            Callback<CompanyListingResponse> {
            override fun onFailure(call: Call<CompanyListingResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<CompanyListingResponse>,
                response: Response<CompanyListingResponse>
            ) {

                if (response.isSuccessful) {
                    data.value =
                        if (response != null && response.body() != null) response!!.body() else null
                } else {
                    val gson = Gson()
                    val adapter = gson.getAdapter(CompanyListingResponse::class.java)
                    if (response.errorBody() != null)
                        data.value = adapter.fromJson(response.errorBody()!!.string())
                }
            }
        })

        return data
    }


    // Trail balance
    fun callTrialBalanceList(
        gstNo: String,
        fromDate: String,
        endDate: String,
        financial_year:String
    ): LiveData<TrialBalanceRespone> {
        val data = MutableLiveData<TrialBalanceRespone>()
        AppRetrofit.instance.callTrialBalanceList(gstNo, fromDate, endDate,financial_year)
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


    /// Generate Tb PDF
    fun callPdffGenerateTrialBalance(
        gstNo: String,
        fromDate: String,
        endDate: String,
        financial_year:String
    ): LiveData<PDFGeneratorReponse> {
        val data = MutableLiveData<PDFGeneratorReponse>()
        AppRetrofit.instance.callPdffGenerateTrialBalance(gstNo, fromDate, endDate,financial_year).enqueue(object :
            Callback<PDFGeneratorReponse> {
            override fun onFailure(call: Call<PDFGeneratorReponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<PDFGeneratorReponse>,
                response: Response<PDFGeneratorReponse>
            ) {

                if (response.isSuccessful) {
                    data.value =
                        if (response != null && response.body() != null) response!!.body() else null
                } else {
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