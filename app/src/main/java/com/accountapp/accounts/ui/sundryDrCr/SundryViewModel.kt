package com.accountapp.accounts.ui.sundryDrCr

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accountapp.accounts.model.response.PDFGeneratorReponse
import com.accountapp.accounts.model.response.TrialBalanceRespone
import com.accountapp.accounts.ui.ladgerList.ListReposatory

class SundryViewModel: ViewModel() {

    lateinit var mRepo: SundryReposatory

    init {
        mRepo = SundryReposatory()
    }


    //Sundry cradator Api
    fun callSundryCredator(gstNo: String?,fromDate: String,endDate: String): LiveData<TrialBalanceRespone> {
        return mRepo.callSundryCredator(gstNo!!,fromDate,endDate)
    }

    //Sundry cradator Api PDF
    fun callSundryCredatorPDF(gstNo: String?,fromDate: String,endDate: String): LiveData<PDFGeneratorReponse> {
        return mRepo.callSundryCredatorPDF(gstNo!!,fromDate,endDate)
    }

    //Sundry Debator Api
    fun callSundryDebator(gstNo: String?,fromDate: String,endDate: String): LiveData<TrialBalanceRespone> {
        return mRepo.callSundryDebator(gstNo!!,fromDate,endDate)
    }
    //Sundry Debator Api PDF
    fun callSundryDebatorPDF(gstNo: String?,fromDate: String,endDate: String): LiveData<PDFGeneratorReponse> {
        return mRepo.callSundryDebatorPDF(gstNo!!,fromDate,endDate)
    }
}