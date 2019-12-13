package com.accountapp.accounts.ui.ladgerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.*

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

    // My Company Listing
    fun callCompanyList(mobileNo: String?): LiveData<CompanyListingResponse> {
        return mRepo.callCompanyList(mobileNo)
    }

// Trailbalance Api
    fun callTrialBalanceList(gstNo: String?,fromDate: String,endDate: String): LiveData<TrialBalanceRespone> {
        return mRepo.callTrialBalanceList(gstNo!!,fromDate,endDate)
    }

// Generate Tb PDF
    fun callPdffGenerateTrialBalance(gstNo: String?,fromDate: String,endDate: String): LiveData<PDFGeneratorReponse> {
        return mRepo.callPdffGenerateTrialBalance(gstNo!!,fromDate,endDate)
    }
}