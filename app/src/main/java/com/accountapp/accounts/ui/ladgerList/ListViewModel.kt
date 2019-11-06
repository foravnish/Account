package com.accountapp.accounts.ui.ladgerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.LadgerListResponse
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.model.response.SearchCompanyList
import com.accountapp.accounts.model.response.SignUpResponse

class ListViewModel: ViewModel() {
    lateinit var mRepo: ListReposatory

    init {
        mRepo = ListReposatory()
    }

    fun callSearchData(gstNo: String?, search: String): LiveData<SearchCompanyList> {
        return mRepo.callSearchData(gstNo,search)
    }

    fun callLadgerList(gstNo: String?, acc_no: String,fromDate:String,endDate:String): LiveData<LadgerListResponse> {
        return mRepo.callLadgerList(gstNo,acc_no,fromDate,endDate)
    }

    fun callPdffGenerateApi(gstNo: String?,acc_no:String,fromDate: String,endDate: String): LiveData<PDFGeneratorReponse> {
        return mRepo.callPdffGenerateApi(gstNo!!,acc_no,fromDate,endDate)
    }
}